import javax.swing.*;
import java.util.ArrayList;

public class InputHandler {

    public static void moveCaretToLeft(Grid grid) {

        if (grid.caretX == 0) { // Caret is the first index.

            if (grid.caretY != 0) { // Caret isn't at the first line.
                grid.caretY--;
                grid.caretX = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);
                if (grid.caretX == -1) {
                    grid.caretX = grid.maxCaretX;
                }
            } else { // Caret is at the first line.
                if (grid.upperBuffer.size() > 0) { // UpperBuffer isn't empty (the caret can only go up, if upperBuffer contains text).
                    String upperLine = grid.upperBuffer.get(0); // Gets upper line.
                    if (upperLine.equals("\n") == true) { // UpperLine is a blank line.
                        upperLine = "";
                    }
                    grid.upperBuffer.removeFirst(); // Removes upperLine from upperBuffer.

                    GridRendering.addLastLineToLowerBuffer(grid); // If last line contains text, it gets added to lowerBuffer.

                    GridRendering.shiftDown(grid.labelArray); // Shifts all lines down and clears the first line.
                    GridRendering.stringToLabelArray(upperLine, grid.labelArray[0]); // Writes upperLine String to first line.

                    // Gets upperLine's last available index.
                    grid.caretX = GridRendering.getArrayNextAvailableIndex(grid.labelArray[0]);
                    if (grid.caretX == -1) {
                        grid.caretX = grid.labelArray[0].length - 1; // CaretX equals to the last index.
                    }
                }
            }
        } else { // Caret isn't the first index.
            grid.caretX--;
        }
    }

    public static void moveCaretToLeftWithCtrl(Grid grid) {
        int started = 0;

        String currentCaretXString = grid.labelArray[grid.caretY][grid.caretX].getText();

        if (grid.caretX == 0) {
            return;
        }

        if (currentCaretXString.isEmpty() == true) {
            grid.caretX--;
            currentCaretXString = grid.labelArray[grid.caretY][grid.caretX].getText();
        }

        if (grid.caretX == 0) {
            return;
        }

        if (Helper.isAlphaNum(currentCaretXString.charAt(0)) == false) {
            if (Helper.isAlphaNum(grid.labelArray[grid.caretY][grid.caretX - 1].getText().charAt(0)) == true) {
                grid.caretX--;
            }
        }

        for (int i = grid.caretX; i >= 0; i--) {

            String labelText = grid.labelArray[grid.caretY][i].getText();

            if (i == 0) {
                grid.caretX = 0;
                break;
            }

            char labelChar = labelText.charAt(0);

            if (Helper.isAlphaNum(labelChar) == false) {
                if (i == 0) {
                    grid.caretX = 0;
                    break;
                }

                if (started == 0) {
                    started = 1;
                }

                continue;
            }

            if (started == 1) {
                grid.caretX = i + 1;
                break;
            }
        }
    }

    public static void moveCaretToRight(Grid grid) {
        int lastAvailableIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);
        if (lastAvailableIndex == -1) {
            lastAvailableIndex = grid.maxCaretX;
        }

        if (grid.caretX == lastAvailableIndex) { // CaretX is the last index (last available index or at the end of the line).

            if (grid.caretY == grid.maxCaretY) { // Caret is at the last line.
                if (grid.lowerBuffer.size() > 0) { // LowerBuffer contains text (caret can only go down if lowerBuffer contains text).
                    GridRendering.addFirstLineToUpperBuffer(grid);

                    GridRendering.shiftUp(grid.labelArray); // Shifts every line up and clears the last line.

                    String lowerLine = grid.lowerBuffer.get(0); // Gets lowerBuffer's first index (lower line).
                    grid.lowerBuffer.removeFirst(); // Removes lowerLine from lowerBuffer.

                    if (lowerLine.equals("\n") == true) {
                        lowerLine = ""; // LowerLine turns into an empty line.
                    }
                    // Prints lowerLine to last line.
                    GridRendering.stringToLabelArray(lowerLine, grid.labelArray[grid.maxCaretY]);

                    grid.caretX = 0;
                }
            } else { // Caret is the last available index or at the end of the line, but isn't at the last line.
                if (grid.caretY < GridRendering.getLastLineIndex(grid)) {
                    grid.caretY++;
                    grid.caretX = 0;
                }
            }
        } else { // Caret is at the beginning or in the middle of the line.
            grid.caretX++;
        }
    }

    public static void moveCaretToRightWithCtrl(Grid grid) {

        int started = 0;

        for (int i = grid.caretX; i <= grid.maxCaretX; i++) {

            String labelText = grid.labelArray[grid.caretY][i].getText();

            if (labelText.isEmpty() == true) {
                grid.caretX = i;
                break;
            }

            char labelChar = labelText.charAt(0);

            if (Helper.isAlphaNum(labelChar) == true) {
                if (i == grid.maxCaretX) {
                    grid.caretX = i;
                    break;
                }

                if (started == 0) {
                    started = 1;
                }

                continue;
            }

            if (started == 1) {
                grid.caretX = i;
                break;
            }
        }
    }

    public static void moveCaretUp(Grid grid) {
        if (grid.caretY != 0) { // Caret isn't at the first line.
            if (GridRendering.isLineEmpty(grid.labelArray[grid.caretY]) == false) { // Line isn't empty.

                int upperLineLastIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY - 1]);

                if (upperLineLastIndex == -1) {
                    upperLineLastIndex = grid.maxCaretX;
                }

                grid.caretY--;

                if (grid.caretX > upperLineLastIndex) {
                    grid.caretX = upperLineLastIndex;
                }
            } else { // Line is empty.
                grid.caretY--;
                grid.caretX = 0;
            }

            if (grid.mode == Constants.EDIT_MODE) {
                return;
            }

            if (grid.caretY == grid.maxCaretY - 1) {
                if (grid.lowerBuffer.size() == 0 && GridRendering.isLineEmpty(grid.labelArray[grid.maxCaretY]) == true) {
                    grid.commandLineStartY++;
                    grid.caretY++;
                    GridRendering.shiftDown(grid.labelArray);
                    if (grid.upperBuffer.size() > 0) {
                        if (grid.upperBuffer.getFirst().equals("\n") == false) {
                            GridRendering.stringToLabelArray(grid.upperBuffer.getFirst(), grid.labelArray[0]);
                        }
                        grid.upperBuffer.removeFirst();
                    }
                }
            }
        } else { // Caret is at the first line.
            if (grid.upperBuffer.size() > 0) { // UpperBuffer isn't empty (the caret can only go up, if upperBuffer contains text).

                String upperLine = grid.upperBuffer.get(0); // Gets upper line.
                if (upperLine.equals("\n") == true) { // UpperLine is a blank line.
                    upperLine = "";
                }
                grid.upperBuffer.removeFirst(); // Removes upper line from upperBuffer.

                GridRendering.addLastLineToLowerBuffer(grid); // If last line contains text, it gets added to lowerBuffer.

                GridRendering.shiftDown(grid.labelArray); // Shifts all lines down and clears the first line.
                GridRendering.stringToLabelArray(upperLine, grid.labelArray[0]); // Writes upperLine String to first line.

                // Gets upperLine's last available index.
                int upperLineLastAvailableIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[0]);
                if (upperLineLastAvailableIndex == -1) {
                    upperLineLastAvailableIndex = grid.labelArray[0].length - 1; // It equals to the last index.
                }
                if (grid.caretX > upperLineLastAvailableIndex) { // Caret is greater than upperLine's last available index.
                    grid.caretX = upperLineLastAvailableIndex; // CaretX equals to upperLine's last available index.
                }
            }
        }
    }

    public static void moveCaretUpWithCtrl(Grid grid) {
        int upperBufferSize = grid.upperBuffer.size();

        if (upperBufferSize == 0) {
            return;
        }

        int upMoves = 10;

        if (upperBufferSize < upMoves) {
            upMoves = upperBufferSize;
        }

        for (int i = 0; i < upMoves; i++) {
            moveCaretUp(grid);
        }
    }

    public static void moveCaretDown(Grid grid) {
        if (grid.caretY != grid.maxCaretY) { // Caret isn't at the last line.
            if (grid.caretY < GridRendering.getLastLineIndex(grid)) {
                grid.caretY++;
                int lowerLineLastIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);

                if (lowerLineLastIndex == -1) {
                    lowerLineLastIndex = grid.maxCaretX;
                }

                if (grid.caretX > lowerLineLastIndex) {
                    grid.caretX = lowerLineLastIndex;
                }
            }
        } else { // Caret is at the last line.
            if (grid.lowerBuffer.size() > 0) { // LowerBuffer contains text (caret can only go down, if lowerBuffer contains text).

                GridRendering.addFirstLineToUpperBuffer(grid);

                GridRendering.shiftUp(grid.labelArray); // Shifts every line up and clears the last line.

                String lowerLine = grid.lowerBuffer.get(0); // Gets lowerBuffer's first index (lower line).
                grid.lowerBuffer.removeFirst(); // Removes lowerLine from lowerBuffer.

                if (lowerLine.equals("\n") == true) {
                    lowerLine = ""; // LowerLine turns into an empty line.
                }

                // Prints lowerLine to last line.
                GridRendering.stringToLabelArray(lowerLine, grid.labelArray[grid.maxCaretY]);

                int lowerLineLastIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);
                if (lowerLineLastIndex == -1) {
                    lowerLineLastIndex = grid.maxCaretX;
                }

                if (grid.caretX > lowerLineLastIndex) {
                    grid.caretX = lowerLineLastIndex;
                }
            }
        }
    }

    public static void moveCaretDownWithCtrl(Grid grid) {
        int lowerBufferSize = grid.lowerBuffer.size();

        if (lowerBufferSize == 0) {
            return;
        }

        int downMoves = 10;

        if (lowerBufferSize < downMoves) {
            downMoves = lowerBufferSize;
        }

        for (int i = 0; i < downMoves; i++) {
            moveCaretDown(grid);
        }
    }

    public static int printCharacter(Grid grid, char chr) {

        if (isCharacterAllowed(chr) == false) {
            return -1;
        }

        int lastAvailableIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);

        if (lastAvailableIndex != -1) { // line has at least one free cell at the end.

            if (grid.caretX == lastAvailableIndex) { // Caret is the last available index.

                if (grid.caretX == grid.maxCaretX) { // Caret is the last available index and at the end of the line.

                    if (grid.caretY == grid.maxCaretY) { // Caret is at the last line.
                        if (grid.mode == Constants.COMMAND_MODE && grid.commandLineStartY == 0) {
                            return -1;
                        }
                        grid.labelArray[grid.caretY][grid.caretX].setText(String.valueOf(chr));
                        grid.caretX = 0;

                        // If the first line contains text, it gets added to upperBuffer.
                        GridRendering.addFirstLineToUpperBuffer(grid);

                        GridRendering.shiftUp(grid.labelArray); // Shifts every line up and clears last line.

                        grid.commandLineStartY--; // So the program can keep track of where the command starts.
                    } else { // Caret isn't at the last line.
                        grid.labelArray[grid.caretY][grid.caretX].setText(String.valueOf(chr));
                        grid.caretY++;
                        grid.caretX = 0;

                        // Shifts every line down, starting with caretY's line. If last line contains text, it gets added to lowerBuffer.
                        GridRendering.shiftDown(grid, grid.caretY);
                    }
                } else {// Caret is the last available index, but not at the end of the line.
                    grid.labelArray[grid.caretY][grid.caretX].setText(String.valueOf(chr));
                    grid.caretX++;
                }
            } else if (grid.caretX < lastAvailableIndex) { // Caret has text to both sides.
                GridRendering.shiftRight(grid.labelArray[grid.caretY], grid.caretX);
                grid.labelArray[grid.caretY][grid.caretX].setText(String.valueOf(chr));
                grid.caretX++;
            }
        }

        return 0;
    }

    public static boolean isCharacterAllowed(char chr) {

        final char[] allowedCharacters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '\'', '\"', 'º', '!', '@', '#', '$', '%', '&', '*', '(', ')', '_', '-', '+', '=', '§', '¢', '£', '{', '}', '[', ' ',']', 'ç',
            'Ç', '^', 'ª', ',', '.', ';', ':', '/', '\\', '?', '<', '>', '´', '`', '|', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        for (int i = 0, size = allowedCharacters.length; i < size; i++) {
            if (allowedCharacters[i] == chr) {
                return true;
            }
        }
        return false;
    }

    public static void backSpace(Grid grid) {

        if (grid.caretX != 0) { // Caret isn't the first index of the line.

            int lastAvailableIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);

            if (grid.caretX == lastAvailableIndex) { // Caret is the last index.
                grid.caretX--;
                grid.labelArray[grid.caretY][grid.caretX].setText(""); // Removes character at grid(caretY, caretX).
            } else { // Caret has text in both sides, or is equal to maxCaretX.
                GridRendering.shiftLeft(grid.labelArray[grid.caretY], grid.caretX);
                grid.caretX--;
            }
        } else { // Caret is the first index of the line.
            if (grid.caretY != 0) { // Caret isn't at the first line.

                int backspacedLineSize = GridRendering.lineLength(grid.labelArray[grid.caretY]);
                int freeSpace = grid.labelArray[grid.caretY - 1].length - GridRendering.lineLength(grid.labelArray[grid.caretY - 1]);

                if (backspacedLineSize <= freeSpace) { // Backspaced line fits on the upper line's free space.

                    if (backspacedLineSize > 0) { // Backspaced line is not empty.
                        String backspacedLine = GridRendering.labelArrayToString(grid.labelArray[grid.caretY]);
                        GridRendering.clearLine(grid.labelArray[grid.caretY]);
                        GridRendering.shiftUp(grid.lowerBuffer, grid.labelArray, grid.caretY);
                        grid.caretY--;
                        grid.caretX = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);
                        GridRendering.appendToLine(backspacedLine, grid.labelArray[grid.caretY]);
                    } else { // Backspaced line is empty.
                        GridRendering.shiftUp(grid.lowerBuffer, grid.labelArray, grid.caretY);
                        grid.caretY--;
                        grid.caretX = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);
                        if (grid.caretX == -1) {
                            grid.caretX = grid.maxCaretX;
                        }
                    }

                    if (grid.mode == Constants.COMMAND_MODE) {
                        if (grid.caretY == grid.maxCaretY - 1) {
                            grid.commandLineStartY++;
                            grid.caretY++;
                            GridRendering.shiftDown(grid.labelArray);
                            if (grid.upperBuffer.size() > 0) {
                                if (grid.upperBuffer.getFirst().equals("\n") == false) {
                                    GridRendering.stringToLabelArray(grid.upperBuffer.getFirst(), grid.labelArray[0]);
                                }
                                grid.upperBuffer.removeFirst();
                            }
                        }
                    }
                }
            } else { // Caret is the first index of the line and at the first line.

                if (grid.upperBuffer.size() > 0) { // UpperBuffer has text on the first index.

                    int backspacedLineSize = GridRendering.lineLength(grid.labelArray[grid.caretY]);
                    int freeSpace = grid.labelArray[grid.caretY].length - grid.upperBuffer.get(0).length();

                    if (backspacedLineSize <= freeSpace) { // Backspaced line fits on the upper line.

                        if (backspacedLineSize > 0) { // Backspaced line is not empty.
                            String upperLine = grid.upperBuffer.get(0); // Gets upper line.
                            grid.upperBuffer.removeFirst();
                            String backspacedLine = GridRendering.labelArrayToString(grid.labelArray[grid.caretY]); // Gets backspaced line.
                            if (upperLine.equals("\n") == true) { // Upper line is actually just a blank line.
                                upperLine = ""; // makes upperLine equal to a blank String.
                            }
                            grid.caretX = upperLine.length();
                            if (grid.caretX > grid.maxCaretX) {
                                grid.caretX = grid.maxCaretX;
                            }
                            upperLine += backspacedLine;
                            GridRendering.stringToLabelArray(upperLine, grid.labelArray[0]);
                        } else { // Backspaced line is empty.
                            String upperLine = grid.upperBuffer.get(0); // Gets upper line.
                            grid.upperBuffer.removeFirst();
                            if (upperLine.equals("\n") == true) { // Upper line is actually just a blank line.
                                upperLine = ""; // makes upperLine equal to a blank String.
                            }
                            grid.caretX = upperLine.length();
                            if (grid.caretX > grid.maxCaretX) {
                                grid.caretX = grid.maxCaretX;
                            }
                            GridRendering.stringToLabelArray(upperLine, grid.labelArray[0]);
                        }
                    }
                }
            }
        }
    }

    public static void lineBreak(Grid grid) {

        int lastAvailableIndex = GridRendering.getArrayNextAvailableIndex(grid.labelArray[grid.caretY]);

        if (grid.caretX == lastAvailableIndex) { // Caret is the last available index, but not at the end of the line.

            if (grid.caretY == grid.maxCaretY) { // Caret is the last available index and at the last line.
                grid.caretX = 0;

                // If the first line contains text, it gets added to upperBuffer.
                GridRendering.addFirstLineToUpperBuffer(grid);

                // Shifts every line up and clears last line.
                GridRendering.shiftUp(grid.labelArray);
            } else { // Caret is the last available index, but not at the last line.
                grid.caretY++;
                grid.caretX = 0;
                GridRendering.shiftDown(grid, grid.caretY);
            }
        } else { // LastAvailableIndex is equal to -1 or lastAvailableIndex is greater than caretX, meaning caret has text to both sides.
            if (grid.caretY == grid.maxCaretY) { // Carat is at the last line.
                String substring = GridRendering.lineSubstring(grid.labelArray[grid.caretY], grid.caretX);
                GridRendering.removeLineChunk(grid.labelArray[grid.caretY], grid.caretX);
                grid.caretX = 0;

                // If the first contains text, it gets added to upperBuffer.
                GridRendering.addFirstLineToUpperBuffer(grid);

                GridRendering.shiftUp(grid.labelArray); // Shifts every line up and clears the last line.
                GridRendering.stringToLabelArray(substring, grid.labelArray[grid.maxCaretY]);
            } else {
                String substring = GridRendering.lineSubstring(grid.labelArray[grid.caretY], grid.caretX);
                GridRendering.removeLineChunk(grid.labelArray[grid.caretY], grid.caretX);
                grid.caretY++;
                grid.caretX = 0;
                GridRendering.shiftDown(grid, grid.caretY);
                GridRendering.stringToLabelArray(substring, grid.labelArray[grid.caretY]);
            }
        }
    }
}
