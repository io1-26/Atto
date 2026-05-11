import javax.swing.*;
import java.awt.*;

public class SyntaxHighlighter {

    public static void highlightSyntax(Grid grid) {

        if (grid.isSyntaxHighlightingActive == false) {
            highlightOnlySpaces(grid);
            return;
        }

        if (grid.currentOpenFile.endsWith(".java") == true) {
            highlightJava(grid);
        } else if (grid.currentOpenFile.endsWith(".py") == true) {
            highlightPython(grid);
        } else if (grid.currentOpenFile.endsWith(".c") == true) {
            highlightC(grid);
        } else if (grid.currentOpenFile.endsWith(".cpp") == true) {
            highlightC(grid);
        } else if (grid.currentOpenFile.endsWith(".h") == true) {
            highlightC(grid);
        } else {
            highlightOnlySpaces(grid);
        }
    }

    private static void highlightOnlySpaces(Grid grid) {
        for (int i = 0, size = grid.labelArray.length; i < size; i++) {
            String line = GridRendering.labelArrayToString(grid.labelArray[i]);

            int lineLength = line.length();

            if (lineLength == 0) {
                continue;
            }

            if (containsOnlySpaces(line) == true) {
                for (int j = 0; j < lineLength; j++) {
                    grid.panelArray[i][j].setBackground(grid.theme.spaceOnlyLineColor);
                }
            }
        }
    }

    private static void highlightJava(Grid grid) {

        String[] keywords = Keywords.javaKeywords;

        for (int i = 0, size = grid.labelArray.length; i < size; i++) {

            String line = GridRendering.labelArrayToString(grid.labelArray[i]);

            int lineLength = line.length();

            if (lineLength == 0) {
                continue;
            }

            if (containsOnlySpaces(line) == true) {
                for (int j = 0; j < lineLength; j++) {
                    grid.panelArray[i][j].setBackground(grid.theme.spaceOnlyLineColor);
                }
                continue;
            }

            String[] operators = Operators.javaOperators;

            // Operators
            for (int j = 0, size2 = operators.length; j < size2; j++) {
                String operator = operators[j];

                if (line.contains(operator) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(operator, startFrom)) != -1) {

                        end = start + operator.length();
                        startFrom = end;

                        highlightChunk(grid.labelArray[i], start, end, grid.theme.operatorColor);

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Keywords
            for (int j = 0, size2 = keywords.length; j < size2; j++) {
                String keyword = keywords[j];

                if (line.contains(keyword) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(keyword, startFrom)) != -1) {

                        end = start + keyword.length();
                        startFrom = end;
                        if (lineLength == keyword.length()) {
                            highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                        } else if (start != 0 && end < lineLength) {
                            if (Helper.isAlphaNum(line.charAt(start - 1)) == false && Helper.isAlphaNum(line.charAt(end)) == false) {
                                highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                            }
                        } else {

                            if (start == 0) {
                                if (Helper.isAlphaNum(line.charAt(end)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }

                            if (end == lineLength) {
                                if (Helper.isAlphaNum(line.charAt(start - 1)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }
                        }

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Numbers
            for (int j = 0; j < lineLength; j++) {
                if (Helper.isNum(line.charAt(j)) == true) {
                    if (j == 0) {
                        highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                    } else if (j == lineLength - 1) {
                        if (hasLettersToTheLeft(line) == false) {
                            highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                        }
                    } else {
                        String leftString = line.substring(0, j);
                        String rightString = line.substring(j + 1);

                        if (hasLettersToTheLeft(line.substring(0, j + 1)) == false) {
                            if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) == 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            } else if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) != 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            }
                        }
                    }
                }
            } // For loop end.

            // Single quotes
            int start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\'') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.charColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.charColor);
            }

            // Double quotes
            start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\"') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.stringColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.stringColor);
            }

            int startFrom = 0;
            start = 0;

            // Line comments
            while ((start = line.indexOf("//", startFrom)) != -1) {
                if ((getCharCount(line.substring(0, start), '\"') % 2) == 0 &&
                    (getCharCount(line.substring(0, start), '\'') % 2) == 0) {
                    highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.commentColor);
                    break;
                } else {
                    startFrom = start + 2;
                }
                if (startFrom >= lineLength) {
                    break;
                }
            }

        } // For loop end.
    }

    private static void highlightC(Grid grid) {

        String[] keywords = Keywords.cKeywords;

        for (int i = 0, size = grid.labelArray.length; i < size; i++) {

            String line = GridRendering.labelArrayToString(grid.labelArray[i]);

            int lineLength = line.length();

            if (lineLength == 0) {
                continue;
            }

            if (containsOnlySpaces(line) == true) {
                for (int j = 0; j < lineLength; j++) {
                    grid.panelArray[i][j].setBackground(grid.theme.spaceOnlyLineColor);
                }
                continue;
            }

            String[] operators = Operators.cOperators;

            // Operators
            for (int j = 0, size2 = operators.length; j < size2; j++) {
                String operator = operators[j];

                if (line.contains(operator) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(operator, startFrom)) != -1) {

                        end = start + operator.length();
                        startFrom = end;

                        highlightChunk(grid.labelArray[i], start, end, grid.theme.operatorColor);

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Keywords
            for (int j = 0, size2 = keywords.length; j < size2; j++) {
                String keyword = keywords[j];

                if (line.contains(keyword) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(keyword, startFrom)) != -1) {

                        end = start + keyword.length();
                        startFrom = end;
                        if (lineLength == keyword.length()) {
                            highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                        } else if (start != 0 && end < lineLength) {
                            if (Helper.isAlphaNum(line.charAt(start - 1)) == false && Helper.isAlphaNum(line.charAt(end)) == false) {
                                highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                            }
                        } else {

                            if (start == 0) {
                                if (Helper.isAlphaNum(line.charAt(end)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }

                            if (end == lineLength) {
                                if (Helper.isAlphaNum(line.charAt(start - 1)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }
                        }

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Numbers
            for (int j = 0; j < lineLength; j++) {
                if (Helper.isNum(line.charAt(j)) == true) {
                    if (j == 0) {
                        highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                    } else if (j == lineLength - 1) {
                        if (hasLettersToTheLeft(line) == false) {
                            highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                        }
                    } else {
                        String leftString = line.substring(0, j);
                        String rightString = line.substring(j + 1);

                        if (hasLettersToTheLeft(line.substring(0, j + 1)) == false) {
                            if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) == 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            } else if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) != 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            }
                        }
                    }
                }
            } // For loop end.

            // Single quotes
            int start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\'') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.charColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.charColor);
            }

            // Double quotes
            start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\"') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.stringColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.stringColor);
            }

            int startFrom = 0;
            start = 0;

            // Line comments
            while ((start = line.indexOf("//", startFrom)) != -1) {
                if ((getCharCount(line.substring(0, start), '\"') % 2) == 0 &&
                    (getCharCount(line.substring(0, start), '\'') % 2) == 0) {
                    highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.commentColor);
                    break;
                } else {
                    startFrom = start + 2;
                }
                if (startFrom >= lineLength) {
                    break;
                }
            }

            // Artificial block comment
            while ((start = line.indexOf("/*", startFrom)) != -1) {
                if ((getCharCount(line.substring(0, start), '\"') % 2) == 0 &&
                    (getCharCount(line.substring(0, start), '\'') % 2) == 0) {
                    highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.commentColor);
                    break;
                } else {
                    startFrom = start + 2;
                }
                if (startFrom >= lineLength) {
                    break;
                }
            }

            // Includes and defines
            while ((start = line.indexOf("#", startFrom)) != -1) {
                if ((getCharCount(line.substring(0, start), '\"') % 2) == 0 &&
                    (getCharCount(line.substring(0, start), '\'') % 2) == 0) {
                    highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.commentColor);
                    break;
                } else {
                    startFrom = start + 2;
                }
                if (startFrom >= lineLength) {
                    break;
                }
            }

        } // For loop end.
    }

    private static void highlightPython(Grid grid) {

        String[] keywords = Keywords.pythonKeywords;

        for (int i = 0, size = grid.labelArray.length; i < size; i++) {

            String line = GridRendering.labelArrayToString(grid.labelArray[i]);

            int lineLength = line.length();

            if (lineLength == 0) {
                continue;
            }

            if (containsOnlySpaces(line) == true) {
                for (int j = 0; j < lineLength; j++) {
                    grid.panelArray[i][j].setBackground(grid.theme.spaceOnlyLineColor);
                }
                continue;
            }

            String[] operators = Operators.pythonOperators;

            for (int j = 0, size2 = operators.length; j < size2; j++) {
                String operator = operators[j];

                if (line.contains(operator) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(operator, startFrom)) != -1) {

                        end = start + operator.length();
                        startFrom = end;

                        highlightChunk(grid.labelArray[i], start, end, grid.theme.operatorColor);

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Keywords
            for (int j = 0, size2 = keywords.length; j < size2; j++) {
                String keyword = keywords[j];

                if (line.contains(keyword) == true) {

                    int start = 0;
                    int end = 0;
                    int startFrom = 0;

                    while ((start = line.indexOf(keyword, startFrom)) != -1) {

                        end = start + keyword.length();
                        startFrom = end;
                        if (lineLength == keyword.length()) {
                            highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                        } else if (start != 0 && end < lineLength) {
                            if (Helper.isAlphaNum(line.charAt(start - 1)) == false && Helper.isAlphaNum(line.charAt(end)) == false) {
                                highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                            }
                        } else {

                            if (start == 0) {
                                if (Helper.isAlphaNum(line.charAt(end)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }

                            if (end == lineLength) {
                                if (Helper.isAlphaNum(line.charAt(start - 1)) == false) {
                                    highlightChunk(grid.labelArray[i], start, end, grid.theme.keywordColor);
                                }
                            }
                        }

                        if (startFrom == lineLength) {
                            break;
                        }
                    }
                }
            } // For loop end.

            // Numbers
            for (int j = 0; j < lineLength; j++) {
                if (Helper.isNum(line.charAt(j)) == true) {
                    if (j == 0) {
                        highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                    } else if (j == lineLength - 1) {
                        if (hasLettersToTheLeft(line) == false) {
                            highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                        }
                    } else {
                        String leftString = line.substring(0, j);
                        String rightString = line.substring(j + 1);

                        if (hasLettersToTheLeft(line.substring(0, j + 1)) == false) {
                            if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) == 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            } else if ((getCharCount(leftString, '\"') % 2) == 0 && (getCharCount(rightString, '\"') % 2) != 0) {
                                highlightChunk(grid.labelArray[i], j, j + 1, grid.theme.numberColor);
                            }
                        }
                    }
                }
            } // For loop end.

            // Single quotes
            int start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\'') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.stringColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.stringColor);
            }

            // Double quotes
            start = -1;
            for (int j = 0; j < lineLength; j++) {
                if (line.charAt(j) == '\"') {
                    if (j != 0) {
                        if (line.charAt(j - 1) == '\\') {
                            if (j >= 3) {
                                if (line.charAt(j - 2) != '\\') {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (start == -1) {
                        start = j;
                    } else if (start != -1) {
                        highlightChunk(grid.labelArray[i], start, j + 1, grid.theme.stringColor);
                        start = -1;
                    }
                }
            } // For loop end.

            if (start != -1) {
                highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.stringColor);
            }

            int startFrom = 0;
            start = 0;

            // Python line comment
            while ((start = line.indexOf("#", startFrom)) != -1) {
                if ((getCharCount(line.substring(0, start), '\"') % 2) == 0 &&
                    (getCharCount(line.substring(0, start), '\'') % 2) == 0) {
                    highlightChunk(grid.labelArray[i], start, lineLength, grid.theme.commentColor);
                    break;
                } else {
                    startFrom = start + 2;
                }
                if (startFrom >= lineLength) {
                    break;
                }
            }

        } // For loop end.
    }

    // Helper function for highlightSyntax(), it highlights specified chunks of the array.
    private static void highlightChunk(JLabel[] array, int start, int end, Color color) {
        for (int i = start; i < end; i++) {
            array[i].setForeground(color);
        }
    }

    private static boolean containsOnlySpaces(String line) {
        for (int i = 0, size = line.length(); i < size; i++) {
            if (line.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    private static boolean hasLettersToTheLeft(String text) {
        for (int i = text.length() - 1; i >= 0; i--) {
            if (Helper.isNum(text.charAt(i)) == false) {
                if (Helper.isAlpha(text.charAt(i)) == true) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public static int getCharCount(String text, char chr) {
        int occ = 0;
        for (int i = 0, size = text.length(); i < size; i++) {
            if (text.charAt(i) == chr) {
                if (i != 0) {
                    if (text.charAt(i - 1) == '\\') {
                        continue;
                    }
                }
                occ++;
            }
        }
        return occ;
    }
}
