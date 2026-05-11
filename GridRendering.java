import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridRendering {

    public static void loadGrid(Grid grid, JFrame frame) {

        defineGridDimensions(grid, frame);

        int xPosit = grid.initialXPosit;
        int yPosit = grid.initialYPosit;

        yPosit = yPosit + grid.panelHeight;

        for (int i = 0; i < grid.gridHeight; i++) {
            for (int j = 0; j < grid.gridWidth; j++) {
                grid.panelArray[i][j] = new JPanel();
                grid.panelArray[i][j].setLayout(null);
                grid.panelArray[i][j].setBackground(grid.theme.unfocusedPanelBackground);
                grid.panelArray[i][j].setBounds(xPosit, yPosit, grid.panelWidth, grid.panelHeight);
                // grid.panelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
                grid.labelArray[i][j] = new JLabel();
                grid.labelArray[i][j].setBounds(1, 0, grid.panelWidth, grid.panelHeight);
                grid.labelArray[i][j].setFont(grid.defaultTextAreaFont);
                grid.labelArray[i][j].setForeground(grid.theme.unfocusedLabelForeground);
                grid.panelArray[i][j].add(grid.labelArray[i][j]);
                frame.add(grid.panelArray[i][j]);
                xPosit = xPosit + grid.panelWidth;
            }
            xPosit = grid.initialXPosit;
            yPosit = yPosit + grid.panelHeight;
        }

        int tempYPosit = yPosit;

        yPosit = grid.initialYPosit;

        for (int i = 0, size = grid.hudPanelArray.length; i < size;i++) {
            for (int j = 0, size2 = grid.hudPanelArray[i].length; j < size2;j++) {
                grid.hudPanelArray[i][j] = new JPanel();
                grid.hudPanelArray[i][j].setLayout(null);
                grid.hudPanelArray[i][j].setBackground(grid.theme.unfocusedPanelBackground);
                grid.hudPanelArray[i][j].setBounds(xPosit, yPosit, grid.panelWidth, grid.panelHeight);
                // grid.hudPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
                grid.hudLabelArray[i][j] = new JLabel();
                grid.hudLabelArray[i][j].setBounds(1, 0, grid.panelWidth, grid.panelHeight);
                grid.hudLabelArray[i][j].setFont(grid.defaultTextAreaFont);
                grid.hudLabelArray[i][j].setForeground(grid.theme.unfocusedLabelForeground);
                grid.hudPanelArray[i][j].add(grid.hudLabelArray[i][j]);
                frame.add(grid.hudPanelArray[i][j]);
                xPosit = xPosit + grid.panelWidth;
            }
            xPosit = grid.initialXPosit;

            if (i == 0) {
                yPosit = tempYPosit;
            } else {
                yPosit = yPosit + grid.panelHeight;
            }
        }

        String msg = String.format("To display the commands, type: commands%s\n%s", Constants.delim, Constants.delim);
        GridRendering.terminalOut(String.format("%s%s%s%s", msg, grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid);

        grid.gridLoaded = 1; // Allows input.
    }

    private static void defineGridDimensions(Grid grid, JFrame frame) {

        int width = 0;
        int height = 0;

        if (Constants.currentOS.contains("win") == true) {
            width = frame.getWidth() - 14;
            height = frame.getHeight() - 37;
        } else if (Constants.currentOS.contains("nux") == true) {
            width = frame.getWidth();
            height = frame.getHeight() - 40;
        }

        grid.gridHeight = 0;
        grid.gridWidth = 0;

        int i = 0;

        while (true) {
            grid.gridWidth++;
            i += grid.panelWidth;

            if (i >= width) {
                grid.gridWidth--;
                i -= grid.panelWidth;
                grid.initialXPosit = (width - i) / 2;
                break;
            }
        }

        i = 0;

        while (true) {
            grid.gridHeight++;
            i += grid.panelHeight;

            if (i >= height) {
                grid.gridHeight -= 4;
                i -= grid.panelHeight;
                grid.initialYPosit = (height - i) / 2;
                break;
            }
        }

        grid.panelArray = new JPanel[grid.gridHeight][grid.gridWidth];
        grid.labelArray = new JLabel[grid.gridHeight][grid.gridWidth];

        grid.hudPanelArray = new JPanel[3][grid.gridWidth];
        grid.hudLabelArray = new JLabel[3][grid.gridWidth];

        grid.maxCaretY = grid.gridHeight - 1;
        grid.maxCaretX = grid.gridWidth - 1;
    }

    public static void saveFileSession(Grid grid) {

        int lastLineIndex = GridRendering.getLastLineIndex(grid);

        if (lastLineIndex > grid.maxCaretY) {
            lastLineIndex = grid.maxCaretY;
        }

        grid.fileSessions.addFirst(new FileSession());

        grid.fileSessions.getFirst().caretY = grid.caretY;
        grid.fileSessions.getFirst().caretX = grid.caretX;

        grid.fileSessions.getFirst().fileName = grid.currentOpenFile;
        grid.fileSessions.getFirst().fileAbsolutePath = String.format("%s%s%s",
            grid.terminalDirectory, Constants.fs, grid.currentOpenFile
        );
        grid.fileSessions.getFirst().isModified = grid.isCurrentOpenFileModified;

        for (int i = 0; i <= lastLineIndex; i++) {
            String gridLine = GridRendering.labelArrayToString(grid.labelArray[i]);

            if (gridLine.length() == 0) {
                grid.fileSessions.getFirst().gridBuffer.add("\n");
            } else {
                grid.fileSessions.getFirst().gridBuffer.add(gridLine);
            }
        }

        for (int i = 0, size = grid.upperBuffer.size(); i < size; i++) {
            grid.fileSessions.getFirst().upperBuffer.add(grid.upperBuffer.get(i));
        }

        for (int i = 0, size = grid.lowerBuffer.size(); i < size; i++) {
            grid.fileSessions.getFirst().lowerBuffer.add(grid.lowerBuffer.get(i));
        }
    }

    public static void clearGrid(Grid grid) {
        unfocusCell(grid);
        for (int i = 0; i < grid.gridHeight; i++) {
            for (int j = 0; j < grid.gridWidth; j++) {
                grid.labelArray[i][j].setText("");
                grid.panelArray[i][j].setBackground(grid.theme.unfocusedPanelBackground);
                grid.labelArray[i][j].setForeground(grid.theme.unfocusedLabelForeground);
            }
        }

        grid.upperBuffer.clear();
        grid.lowerBuffer.clear();
    }

    public static void focusCell(Grid grid) {

        // Inverts background and foreground colors.
        if (grid.mode == Constants.EDIT_MODE) {
            Color labelForeground = grid.labelArray[grid.caretY][grid.caretX].getForeground();
            grid.labelArray[grid.caretY][grid.caretX].setForeground(grid.panelArray[grid.caretY][grid.caretX].getBackground());
            grid.panelArray[grid.caretY][grid.caretX].setBackground(labelForeground);
        }

        if (grid.mode == Constants.COMMAND_MODE) {
            grid.labelArray[grid.caretY][grid.caretX].setForeground(grid.theme.focusedLabelForeground);
            grid.panelArray[grid.caretY][grid.caretX].setBackground(grid.theme.focusedPanelBackground);
        }
    }

    public static void unfocusCell(Grid grid) {

        // Inverts background and foreground colors.
        if (grid.mode == Constants.EDIT_MODE) {
            Color labelForeground = grid.labelArray[grid.caretY][grid.caretX].getForeground();
            grid.labelArray[grid.caretY][grid.caretX].setForeground(grid.panelArray[grid.caretY][grid.caretX].getBackground());
            grid.panelArray[grid.caretY][grid.caretX].setBackground(labelForeground);
        }

        if (grid.mode == Constants.COMMAND_MODE) {
            grid.labelArray[grid.caretY][grid.caretX].setForeground(grid.theme.unfocusedLabelForeground);
            grid.panelArray[grid.caretY][grid.caretX].setBackground(grid.theme.unfocusedPanelBackground);
        }
    }

    public static void highlightLine(JLabel[] labelArray, JPanel[] panelArray, Theme theme) {
        for (int i = 0; i < labelArray.length; i++) {
            labelArray[i].setForeground(theme.focusedLabelForeground);
            panelArray[i].setBackground(theme.focusedPanelBackground);
        }
    }

    public static void unhighlightLine(JLabel[] labelArray, JPanel[] panelArray, Theme theme) {
        for (int i = 0; i < labelArray.length; i++) {
            labelArray[i].setForeground(theme.unfocusedLabelForeground);
            panelArray[i].setBackground(theme.unfocusedPanelBackground);
        }
    }

    public static void displayCurrentOpenFile(Grid grid, JFrame frame) {

        String fileName = String.format("%s%s%s", grid.terminalDirectory, Constants.fs, grid.currentOpenFile);

        if (grid.isCurrentOpenFileModified == 1) {
            fileName += "*";
        }

        frame.setTitle(fileName);
    }

    public static void shiftTerminalUp(Grid grid) {
        for (int i = 0, size = grid.lowerBuffer.size(); i < size; i++) {
            GridRendering.addFirstLineToUpperBuffer(grid);
            GridRendering.shiftUp(grid.labelArray);
            if (grid.lowerBuffer.getFirst().equals("\n") == false) {
                GridRendering.stringToLabelArray(grid.lowerBuffer.getFirst(), grid.labelArray[grid.maxCaretY]);
            }
            grid.lowerBuffer.removeFirst();
        }
    }

    public static void resetSyntaxHighlighting(Grid grid) {
        for (int i = 0, size = grid.labelArray.length; i < size; i++) {
            for (int j = 0, size2 = grid.labelArray[i].length; j < size2; j++) {
                grid.labelArray[i][j].setForeground(grid.theme.unfocusedLabelForeground);
                grid.panelArray[i][j].setBackground(grid.theme.unfocusedPanelBackground);
            }
        }
    }

    public static int getLineCount(Grid grid) {
        return GridRendering.getLastLineIndex(grid) + 1 + grid.upperBuffer.size();
    }

    // A function that returns the index after the last non-empty character on a JLabel[].
    public static int getArrayNextAvailableIndex(JLabel[] array) {
        for (int i = 0, size = array.length; i < size; i++) {
            if (array[i].getText().isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    // Returns the index before the last non-empty line on a JLabel[][].
    // If last line index is 0, it returns -1
    public static int getLastLineIndex(Grid grid) {

        int size = grid.labelArray.length - 1;
        int i = size;
        while (true) {
            if (isLineEmpty(grid.labelArray[i]) == false) {
                break;
            }

            if (i == 0) { // Checked line 0 and it's empty.
                break;
            }

            i--;
        }

        if (grid.lowerBuffer.size() > 0) {
            i = size + grid.lowerBuffer.size();
        }

        return i;
    }

    // Converts labelArray row into a String.
    public static String labelArrayToString(JLabel[] array) {

        if (isLineEmpty(array) == true) {
            return "";
        }

        int end = getArrayNextAvailableIndex(array);

        if (end == -1) {
            end = array.length;
        }

        String str = "";
        for (int i = 0; i < end; i++) {
            str += array[i].getText();
        }

        return str;
    }

    // Converts String into a JLabel array.
    public static int stringToLabelArray(String str, JLabel[] array) {

        for (int i = 0, size = array.length; i < size; i++) {
            array[i].setText("");
        }

        for (int i = 0, size = str.length(); i < size; i++) {
            if (i == array.length) {
                break;
            }
            array[i].setText(String.valueOf(str.charAt(i)));
        }

        return 0;
    }

    // Returns true if labelArray is empty.
    public static boolean isLineEmpty(JLabel[] array) {
        for (int i = 0, size = array.length; i < size; i++) {
            if (array[i].getText().isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    // Appends a String object to a line.
    // Should only be used if you are certain that the line to be appended to,
    // has enough space for the appended line.
    public static int appendToLine(String str, JLabel[] array) {

        int start = getArrayNextAvailableIndex(array);

        for (int i = 0, i2 = start, size = str.length(); i < size; i++, i2++) {
            array[i2].setText(String.valueOf(str.charAt(i)));
        }

        return 0;
    }

    // Returns a substring (String object) of a labelArray starting at current x index.
    public static String lineSubstring(JLabel[] array, int x) {
        int end = getArrayNextAvailableIndex(array);

        if (end == -1) {
            end = array.length;
        }

        String sub = "";

        for (int i = x; i < end; i++) {
            sub += array[i].getText();
        }

        return sub;
    }

    // Returns a substring (String object) of a labelArray starting at startX and ending at endX.
    public static String lineSubstring(JLabel[] array, int startX, int endX) {

        String sub = "";

        for (int i = startX; i < endX; i++) {
            sub += array[i].getText();
        }

        return sub;
    }

    // Adds first line to upperBuffer's first index.
    public static int addFirstLineToUpperBuffer(Grid grid) {
        if (isLineEmpty(grid.labelArray[0]) == false) { // First line contains text.
            grid.upperBuffer.addFirst(labelArrayToString(grid.labelArray[0])); // Adds first line to upperBuffer.
        } else { // First line doesn't contain text.
            grid.upperBuffer.addFirst("\n"); // Adds blank line to upperBuffer.
        }

        return 0;
    }

    // Adds last line to lowerBuffer's first index.
    public static int addLastLineToLowerBuffer(Grid grid) {
        if (isLineEmpty(grid.labelArray[grid.labelArray.length - 1]) == false) { // Last line contains text.
            grid.lowerBuffer.addFirst(labelArrayToString(grid.labelArray[grid.labelArray.length - 1])); // Adds last line to upperBuffer.
        } else { // Last line doesn't contain text.
            if (grid.lowerBuffer.size() > 0) { // lowerBuffer contains text.
                grid.lowerBuffer.addFirst("\n"); // Adds blank line to lowerBuffer.
            }
        }

        return 0;
    }

    // Returns the length of a line (JLabel[] array).
    public static int lineLength(JLabel[] array) {

        int length = getArrayNextAvailableIndex(array);

        if (length == -1) {
            length = array.length;
        }

        return length;
    }

    // Removes line chunk, starting at x.
    public static int removeLineChunk(JLabel[] array, int x) {

        int end = getArrayNextAvailableIndex(array);

        if (end == -1) {
            end = array.length;
        }

        for (int i = x; i < end; i++) {
            array[i].setText("");
        }

        return 0;
    }

    // Returns the size of the biggest String.
    public static int getBiggestStringSize(Grid grid) {
        int biggestStringSize = 0;

        if (grid.upperBuffer.size() > 0) {
            for (int i = 0, size = grid.upperBuffer.size(); i < size; i++) {
                if (grid.upperBuffer.get(i).length() > biggestStringSize) {
                    biggestStringSize = grid.upperBuffer.get(i).length();
                }
            }
        }

        if (grid.lowerBuffer.size() > 0) {
            for (int i = 0, size = grid.lowerBuffer.size(); i < size; i++) {
                if (grid.lowerBuffer.get(i).length() > biggestStringSize) {
                    biggestStringSize = grid.lowerBuffer.get(i).length();
                }
            }
        }

        for (int i = 0, size = grid.labelArray.length; i < size; i++) {
            if (lineLength(grid.labelArray[i]) > biggestStringSize) {
                biggestStringSize = lineLength(grid.labelArray[i]);
            }
        }

        return biggestStringSize;
    }

    public static int clearLine(JLabel[] array) {

        for (int i = 0, size = array.length; i < size; i++) {
            array[i].setText("");
        }

        return 0;
    }

    // Shifts every character in the array one cell to the left.
    public static int shiftLeft(JLabel[] array, int x) {

        int end = getArrayNextAvailableIndex(array);

        if (end == -1) {
            end = array.length;
        }

        for (int i = x; i < end; i++) {
            array[i - 1].setText(array[i].getText());
            if (i == end - 1) {
                array[i].setText("");
            }
        }

        return 0;
    }

    // Shifts every character in the array one cell to the right.
    public static int shiftRight(JLabel[] array, int x) {

        int end = getArrayNextAvailableIndex(array);

        for (int i = end - 1; i >= x; i--) {
            array[i + 1].setText(array[i].getText());
        }

        return 0;
    }

    // Shifts every character up, starting at row y (y-axis). If the lowerBuffer has text,
    // its first index gets printed to the last line and removed from the lowerBuffer.
    public static int shiftUp(ArrayList<String> lowerBuffer, JLabel[][] array, int y) {

        for (int i = y, size = array.length - 1; i < size; i++) {
            String lowerLine = labelArrayToString(array[i + 1]); // Gets lower line.
            stringToLabelArray(lowerLine, array[i]); // Prints lower line to current line i.
        }

        clearLine(array[array.length - 1]); // Clears last line, so if lowerBuffer contains text, it can push it to the last line.

        if (lowerBuffer.size() > 0) { // LowerBuffer contains text.
            if (lowerBuffer.get(0).equals("\n") == true) { // First lowerBuffer index is a blank line.
                lowerBuffer.removeFirst();
            } else { // First lowerBuffer index isn't a blank line.
                stringToLabelArray(lowerBuffer.get(0), array[array.length - 1]);
                lowerBuffer.removeFirst();
            }
        }

        return 0;
    }

    // Shifts every character down, starting at a certain row (y-axis). If the last line has text,
    // it gets added to lowerBuffer.
    public static int shiftDown(Grid grid, int y) {

        addLastLineToLowerBuffer(grid); // If last line contains text, it gets added to lowerBuffer.

        for (int i = grid.labelArray.length - 1; i > y; i--) {
            String upperLine = labelArrayToString(grid.labelArray[i - 1]); // Gets upper line.
            stringToLabelArray(upperLine, grid.labelArray[i]);
        }

        clearLine(grid.labelArray[y]); // Clears line at index y.

        return 0;
    }

    // Shifts every character up, starting at row 0 and clears last line.
    public static int shiftUp(JLabel[][] array) {

        for (int i = 0, size = array.length - 1; i < size; i++) {
            String lowerLine = labelArrayToString(array[i + 1]); // Gets lower line.
            stringToLabelArray(lowerLine, array[i]); // Prints lower line to current line i.
        }

        clearLine(array[array.length - 1]); // Clears last line.

        return 0;
    }

    // Shifts every character down, starting at last row and clears first line.
    public static int shiftDown(JLabel[][] array) {

        for (int i = array.length - 1; i > 0; i--) {
            String upperLine = labelArrayToString(array[i - 1]); // Gets upper line.
            stringToLabelArray(upperLine, array[i]); // Prints upper line to line at index i.
        }

        clearLine(array[0]); // Clears first line.

        return 0;
    }

    // If 1 is passed as the flag, the function shall add the current directory to the outputArray.
    // If 0 is passed as the flag, the function shall not add the current directory to the outputArray.
    public static void terminalOut(String str, int flag, Grid grid) {

        ArrayList<String> outputArray = new ArrayList<>();

        int i = 0;
        while (true) {
            String token = Helper.getTokenAt(str, i);
            i++;

            if (token == null) {
                break;
            }

            if (token.equals("\n") == true) {
                outputArray.add(" ");
            } else {
                outputArray.add(token);
            }
        }

        if (flag == 1) {
            outputArray.add(String.format(String.format("%s%s", grid.terminalDirectory, Constants.afterDirString)));
        }

        int lineSize = grid.labelArray[0].length;
        int index = 0;

        while (index < outputArray.size()) {
            if (outputArray.get(index).length() > lineSize) {
                String entireString = outputArray.get(index);
                outputArray.set(index, entireString.substring(0, lineSize));
                if (index == outputArray.size() - 1) {
                    outputArray.add(entireString.substring(lineSize));
                } else {
                    outputArray.add(index + 1, entireString.substring(lineSize));
                }
                index = 0;
                continue;
            }
            index++;
        }

        int nextAvailableLine = GridRendering.getLastLineIndex(grid);

        if (nextAvailableLine == 0) {
            if (GridRendering.isLineEmpty(grid.labelArray[0]) == false) {
                nextAvailableLine++;
            }
        } else {
            nextAvailableLine++;
        }

        for (int ind = 0, size = outputArray.size(); ind < size; ind++, nextAvailableLine++) {
            if (nextAvailableLine <= grid.maxCaretY) {
                GridRendering.stringToLabelArray(outputArray.get(ind), grid.labelArray[nextAvailableLine]);
            } else {
                GridRendering.addFirstLineToUpperBuffer(grid);
                GridRendering.shiftUp(grid.labelArray);
                GridRendering.stringToLabelArray(outputArray.get(ind), grid.labelArray[grid.maxCaretY]);
            }
        }

        int lastLineIndex = GridRendering.getLastLineIndex(grid);

        grid.commandLineStartX = GridRendering.getArrayNextAvailableIndex(grid.labelArray[lastLineIndex]);

        if (grid.commandLineStartX == -1) {
            if (lastLineIndex == grid.maxCaretY) {
                GridRendering.addFirstLineToUpperBuffer(grid);
                GridRendering.shiftUp(grid.labelArray);

                grid.caretX = 0;
                grid.commandLineStartX = grid.caretX;
                grid.caretY = grid.maxCaretY;
                grid.commandLineStartY = grid.caretY;
            } else {
                grid.caretX = 0;
                grid.commandLineStartX = grid.caretX;
                grid.caretY = lastLineIndex + 1;
                grid.commandLineStartY = grid.caretY;
            }
        } else {
            grid.caretX = grid.commandLineStartX;
            grid.caretY = lastLineIndex;
            grid.commandLineStartY = grid.caretY;
        }

        focusCell(grid);
    }
}
