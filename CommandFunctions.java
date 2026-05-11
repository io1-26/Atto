import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;

public class CommandFunctions {

    public static void showGridPattern(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 1) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        for (int i = 0; i < grid.panelArray.length; i++) {
            for (int j = 0; j < grid.panelArray[0].length; j++) {
                grid.panelArray[i][j].setBorder(BorderFactory.createLineBorder(grid.theme.spaceOnlyLineColor, 1));
            }
        }

        for (int i = 0; i < grid.hudPanelArray.length; i++) {
            for (int j = 0; j < grid.hudPanelArray[0].length; j++) {
                grid.hudPanelArray[i][j].setBorder(BorderFactory.createLineBorder(grid.theme.spaceOnlyLineColor, 1));
            }
        }

        GridRendering.terminalOut(String.format("%s%s%s",
                grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void hideGridPattern(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 2) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        for (int i = 0; i < grid.panelArray.length; i++) {
            for (int j = 0; j < grid.panelArray[0].length; j++) {
                grid.panelArray[i][j].setBorder(BorderFactory.createLineBorder(grid.theme.spaceOnlyLineColor, 0));
            }
        }

        for (int i = 0; i < grid.hudPanelArray.length; i++) {
            for (int j = 0; j < grid.hudPanelArray[0].length; j++) {
                grid.hudPanelArray[i][j].setBorder(BorderFactory.createLineBorder(grid.theme.spaceOnlyLineColor, 0));
            }
        }

        GridRendering.terminalOut(String.format("%s%s%s",
                grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void displayCommands(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 1) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String strOut = "";
        for (int i = 0, size = Commands.commandsArray.length; i < size; i++) {
            strOut += String.format("%s%s", Commands.commandsArray[i], Constants.delim);
        }

        GridRendering.terminalOut(String.format("\n%s%s\n%s",
            Constants.delim, strOut, Constants.delim), 1, grid
        );
    }

    public static void changeDefaultStartingDirectory(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires at least one argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }
        if (commandTokens.size() > 2) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file = new File(commandTokens.get(1));
        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sNew starting directory does not exist%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        file = new File(Constants.editorConfigFile);
        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sEditor config file was not found%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        int status = FileHandler.updateValue(Constants.editorConfigFile, "DefaultStartingDirectory", commandTokens.get(1));

        if (status == -1) {
            GridRendering.terminalOut(String.format(
                "\n%sThere was an error while updating the default starting directory in local files%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.terminalOut(String.format("\n%sDefault starting directory updated to [%s]%s\n%s",
            Constants.delim, commandTokens.get(1), Constants.delim, Constants.delim), 1, grid
        );
    }

    public static void changeDirectory(ArrayList<String> commandTokens, String commandString, Grid grid) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires at least one argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String directory = "";
        if (commandTokens.size() > 2) {
            // Gets what should be a quoted string.
            directory = commandString.trim();
            directory = directory.substring(directory.indexOf(' ') + 1).trim();
            if (directory.startsWith("\"") == false || directory.endsWith("\"") == false) {
                GridRendering.terminalOut(String.format("\n%sDirectory name must be between double quotes if it contains spaces%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            // Gets Directory name out of the double quotes and trims it.
            directory = directory.substring(1);
            directory = directory.substring(0, directory.length() - 1).trim();
        } else {
            directory = commandTokens.get(1);
        }

        if (directory.equals("..")) {
            if (grid.terminalDirectory.equals(Constants.userHomeDirectory) == true) {
                GridRendering.terminalOut(String.format("\n%sYou can not go back past the user home directory%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            for (int i = grid.terminalDirectory.length() - 1; i >= 0; i--) {
                if (grid.terminalDirectory.charAt(i) == '\\' || grid.terminalDirectory.charAt(i) == '/') {
                    grid.terminalDirectory = grid.terminalDirectory.substring(0, i);
                    break;
                }
            }

            GridRendering.terminalOut(String.format("%s%s%s",
                grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
            );

            return;
        }

        if (Helper.containsOnlyDots(directory) == true) {
            GridRendering.terminalOut(String.format("\n%sDirectory not found [%s]%s\n%s",
                Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String newCurrentDirectory = String.format("%s%s%s", grid.terminalDirectory, Constants.fs, directory);

        File file = new File(newCurrentDirectory);

        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sDirectory not found [%s]%s\n%s",
                Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (file.isDirectory() == false) {
            GridRendering.terminalOut(String.format("\n%sNot a directory [%s]%s\n%s",
                Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        grid.terminalDirectory = newCurrentDirectory;

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void listDirectory(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 1) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file = new File(grid.terminalDirectory);

        File[] files = file.listFiles();

        if (files == null) {
            GridRendering.terminalOut(String.format("\n%sCould not read the files from current directory%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (files.length == 0) {
            GridRendering.terminalOut(String.format("\n%sCurrent directory is empty%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        ArrayList<String> tempArray = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory() == true) {
                tempArray.add(String.format("[D] %s", files[i].getName()));
            } else {
                tempArray.add(String.format("[F] %s", files[i].getName()));
            }
        }

        int longestString = Helper.getLongestStringSize(tempArray);

        String filesString = "";

        for (int i = 0, size = tempArray.size(); i < size; i++) {
            if (files[i].isDirectory() == true) {
                filesString += String.format("%s%s", tempArray.get(i), Constants.delim);
                continue;
            }
            while (true) {
                if (tempArray.get(i).length() < longestString) {
                    tempArray.set(i, tempArray.get(i) + " ");
                    continue;
                } else {
                    filesString += String.format("%s [%d Bytes]%s", tempArray.get(i), files[i].length(), Constants.delim);
                    break;
                }
            }
        }

        GridRendering.terminalOut(String.format("\n%s%s\n%s",
            Constants.delim, filesString, Constants.delim), 1, grid
        );
    }

    public static void openFile(ArrayList<String> commandTokens, String commandString, Grid grid, JFrame frame) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires a file name as argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String fileName = "";

        if (commandTokens.size() > 2) {
            // Gets what should be a quoted string.
            fileName = commandString.trim();
            fileName = fileName.substring(fileName.indexOf(' ') + 1).trim();
            if (fileName.startsWith("\"") == false || fileName.endsWith("\"") == false) {
                GridRendering.terminalOut(String.format("\n%sFile name must be between double quotes if it contains spaces%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            // Gets fileName out of the quotes and trims it.
            fileName = fileName.substring(1);
            fileName = fileName.substring(0, fileName.length() - 1).trim();
        } else {
            fileName = commandTokens.get(1);
        }

        String command = commandTokens.get(0);

        String filePath = String.format("%s%s%s", grid.terminalDirectory, Constants.fs, fileName);

        File file = new File(filePath);

        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sFile not found [%s]%s\n%s",
                Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (file.isDirectory() == true) {
            GridRendering.terminalOut(String.format("\n%sInvalid argument type for command [%s]%s\n%s",
                Constants.delim, command, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (FileHandler.isFileAllowed(fileName) == false) {
            GridRendering.terminalOut(String.format("\n%sFile extension not supported [%s]%s\n%s",
                Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        grid.mode = Constants.EDIT_MODE;

        saveTerminalScreenshot(grid);

        GridRendering.clearGrid(grid);

        grid.currentOpenFile = fileName;

        int index = Helper.isFileInFileSessions(filePath, grid.fileSessions);

        if (index != -1) {
            grid.caretY = grid.fileSessions.get(index).caretY;
            grid.caretX = grid.fileSessions.get(index).caretX;
            grid.isCurrentOpenFileModified = grid.fileSessions.get(index).isModified;
            loadFileSession(index, grid);
        } else {
            grid.caretY = 0;
            grid.caretX = 0;
            FileHandler.loadFileIntoGrid(filePath, grid);
        }

        GridRendering.displayCurrentOpenFile(grid, frame);
    }

    // It already removes the FileSession object from fileSessions ArrayList inside the Grid object.
    // TODO: Move this function to GridRendering.java because it's not a direct command call.
    public static void loadFileSession(int index, Grid grid) {
        for (int i = 0, size = grid.fileSessions.get(index).upperBuffer.size(); i < size; i++) {
            grid.upperBuffer.add(grid.fileSessions.get(index).upperBuffer.get(i));
        }

        for (int i = 0, size = grid.fileSessions.get(index).lowerBuffer.size(); i < size; i++) {
            grid.lowerBuffer.add(grid.fileSessions.get(index).lowerBuffer.get(i));
        }

        for (int i = 0, size = grid.fileSessions.get(index).gridBuffer.size(); i < size; i++) {
            String gridLine = grid.fileSessions.get(index).gridBuffer.get(i);

            if (gridLine.equals("\n") == true) {
                GridRendering.stringToLabelArray("", grid.labelArray[i]);
            } else {
                GridRendering.stringToLabelArray(gridLine, grid.labelArray[i]);
            }
        }

        grid.fileSessions.remove(index);
    }

    public static void clearTerminal(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 1) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.clearGrid(grid);
        grid.terminalSession.sessionBigString = "";

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void mkdir(ArrayList<String> commandTokens, String commandString, Grid grid) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires at least one argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String directory = "";

        if (commandTokens.size() > 2) {
            // Gets what should be a quoted string.
            directory = commandString.substring(commandString.indexOf(' ') + 1).trim();
            if (directory.startsWith("\"") == false || directory.endsWith("\"") == false) {
                GridRendering.terminalOut(String.format("\n%sDirectory name must be between double quotes if it contains spaces%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            // Gets Directory name out of the double quotes and trims it.
            directory = directory.substring(1);
            directory = directory.substring(0, directory.length() - 1).trim();
        } else {
            directory = commandTokens.get(1);
        }

        if (directory.isBlank() == true) {
            GridRendering.terminalOut(String.format("\n%sNew directory name is missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (FileHandler.isFileNameValid(directory) == false) {
            GridRendering.terminalOut(String.format("\n%sNew directory name [%s] is invalid%s\n%s",
                Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file = new File(String.format("%s%s%s", grid.terminalDirectory, Constants.fs, directory));

        if (file.exists() == true) {
            if (file.isDirectory() == true) {
                GridRendering.terminalOut(String.format("\n%sA directory named [%s] already exists%s\n%s",
                    Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
                );
            } else {
                GridRendering.terminalOut(String.format("\n%sA file named [%s] already exists%s\n%s",
                    Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
                );
            }
            return;
        }

        boolean dirCreation = file.mkdir();

        if (dirCreation == false) {
            GridRendering.terminalOut(String.format("\n%sFiled creating directory [%s]%s\n%s",
                Constants.delim, directory, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void mkfile(ArrayList<String> commandTokens, String commandString, Grid grid) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires 1 argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String fileName = "";

        if (commandTokens.size() > 2) {
            // Gets what should be the quoted string.
            fileName = commandString.substring(commandString.indexOf(' ') + 1).trim();
            if (fileName.startsWith("\"") == false || fileName.endsWith("\"") == false) {
                GridRendering.terminalOut(String.format("\n%sFile name must be between double quotes if it contains spaces%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            // Gets file name out of the double quotes and trims it.
            fileName = fileName.substring(1);
            fileName = fileName.substring(0, fileName.length() - 1).trim();
        } else {
            fileName = commandTokens.get(1);
        }

        if (fileName.isBlank() == true) {
            GridRendering.terminalOut(String.format("\n%sNew file name is missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (FileHandler.isFileNameValid(fileName) == false) {
            GridRendering.terminalOut(String.format("\n%sNew file name [%s] is invalid%s\n%s",
                Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file = new File(String.format("%s%s%s", grid.terminalDirectory, Constants.fs, fileName));

        if (file.exists() == true) {
            if (file.isDirectory() == true) {
                GridRendering.terminalOut(String.format("\n%sA directory named [%s] already exists%s\n%s",
                    Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                );
            } else {
                GridRendering.terminalOut(String.format("\n%sA file named [%s] already exists%s\n%s",
                    Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                );
            }
            return;
        }

        boolean fileCreation = false;

        try {
            fileCreation = file.createNewFile();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            fileCreation = false;
        }

        if (fileCreation == false) {
            GridRendering.terminalOut(String.format("\n%sFiled creating file [%s]%s\n%s",
                Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void deleteFile(ArrayList<String> commandTokens, String commandString, Grid grid) {

        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires at least 1 argument%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String fileName = "";

        if (commandTokens.size() > 2) {
            // Gets what should be the quoted string.
            fileName = commandString.substring(commandString.indexOf(' ') + 1).trim();
            if (fileName.startsWith("\"") == false || fileName.endsWith("\"") == false) {
                GridRendering.terminalOut(String.format("\n%sFile name must be between double quotes if it contains spaces%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }

            // Gets file name out of the double quotes and trims it.
            fileName = fileName.substring(1);
            fileName = fileName.substring(0, fileName.length() - 1).trim();
        } else {
            fileName = commandTokens.get(1);
        }

        if (fileName.isBlank() == true) {
            GridRendering.terminalOut(String.format("\n%sFile to be deleted is missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file = new File(String.format("%s%s%s", grid.terminalDirectory, Constants.fs, fileName));

        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sFile [%s] does not exist%s\n%s", 
                Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (file.isDirectory() == true) {
            File[] directoryEntries = file.listFiles();
            if (directoryEntries.length == 0) {
                boolean directoryDeletion = file.delete();
                if (directoryDeletion == true) {
                    GridRendering.terminalOut(String.format("\n%sDirectory [%s] was successfully deleted%s\n%s", 
                        Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                    );
                    return;
                } else {
                    GridRendering.terminalOut(String.format("\n%sDirectory [%s] could not be deleted%s\n%s", 
                        Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                    );
                    return;
                }
            } else {
                GridRendering.terminalOut(String.format("\n%sDirectory [%s] must be empty in order to be deleted%s\n%s", 
                    Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }
        } else if (file.isFile() == true) {
            boolean fileDeletion = file.delete();
            if (fileDeletion == true) {
                GridRendering.terminalOut(String.format("\n%sFile [%s] was successfully deleted%s\n%s", 
                    Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                );
                return;
            } else {
                GridRendering.terminalOut(String.format("\n%sFile [%s] could not be deleted%s\n%s", 
                    Constants.delim, fileName, Constants.delim, Constants.delim), 1, grid
                );
                return;
            }
        } else {
             GridRendering.terminalOut(String.format("\n%sSomething went wrong%s\n%s", 
                 Constants.delim, Constants.delim, Constants.delim), 1, grid
             );
             return;
        }
    }

    public static void renameFile(ArrayList<String> commandTokens, String commandString, Grid grid) {
        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCommand [%s] requires 2 arguments: \"OldFileName\" / \"NewFileName\"%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String arguments = commandString.substring(commandString.indexOf(' ')).trim();

        int barPosition = arguments.indexOf('/');

        if (barPosition == -1) {
            GridRendering.terminalOut(String.format("\n%sInvalid argument(s) for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (arguments.length() == 1 && arguments.equals("/") == true) {
            GridRendering.terminalOut(String.format("\n%sFile to be renamed and its new name are missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        String argument1 = arguments.substring(0, barPosition).trim();

        String argument2 = arguments.substring(barPosition + 1).trim();

        if (argument1.length() == 0) {
            GridRendering.terminalOut(String.format("\n%sFile to be renamed is missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (argument2.length() == 0) {
            GridRendering.terminalOut(String.format("\n%sNew file name is missing%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file1 = new File(String.format("%s%s%s", grid.terminalDirectory, Constants.fs, argument1));

        if (file1.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sFile to be renamed [%s] doesn't exist%s\n%s",
                Constants.delim, argument1, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        File file2 = new File(String.format("%s%s%s", grid.terminalDirectory, Constants.fs, argument2));

        if (file2.exists() == true) {
            GridRendering.terminalOut(String.format("\n%sA file named [%s] already exists%s\n%s",
                Constants.delim, argument2, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (FileHandler.isFileNameValid(argument2) == false) {
            GridRendering.terminalOut(String.format("\n%sNew file name [%s] is invalid%s\n%s",
                Constants.delim, argument2, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        boolean wasFileNameChanged = file1.renameTo(file2);

        if (wasFileNameChanged == false) {
            GridRendering.terminalOut(String.format("\n%sError while renaming file [%s] to [%s]%s\n%s",
                Constants.delim, argument1, argument2, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.terminalOut(String.format("\n%sFile [%s] was renamed to [%s]%s\n%s",
            Constants.delim, argument1, argument2, Constants.delim, Constants.delim), 1, grid
        );
    }

    public static void opencmd(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 1) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (Constants.currentOS.contains("win") == true) {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/d", grid.terminalDirectory);
            try {
                pb.start();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                GridRendering.terminalOut(String.format("\n%sFailed to open Windows terminal%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
            }
        } else if (Constants.currentOS.contains("nux") == true) {
            ProcessBuilder pb = new ProcessBuilder("gnome-terminal");
            pb.directory(new File(grid.terminalDirectory));
            try {
                pb.start();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                GridRendering.terminalOut(String.format("\n%sFailed to open Linux terminal%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
            }
        }

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void openNewEditorInstance(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 2) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        ArrayList<String> pbArgs = new ArrayList<>();

        String jarPath = "";

        if (Constants.currentOS.contains("win") == true) {
            jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
        } else if (Constants.currentOS.contains("nux") == true) {
            jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(5);
        }

        File file = new File(jarPath);

        if (file.exists() == false) {
            GridRendering.terminalOut(String.format("\n%sMain .jar executable file was not found%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (jarPath.endsWith(".jar") == false) {
            GridRendering.terminalOut(String.format(
                "\n%sCould not open a new editor instance, because the program was executed from a .java file%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        pbArgs.add("java");
        pbArgs.add("-jar");
        pbArgs.add(jarPath);

        if (commandTokens.size() == 2) {
            String arg = commandTokens.get(1);
            if (arg.equals(".") == true) {
                pbArgs.add(grid.terminalDirectory);
            } else {
                GridRendering.terminalOut(String.format("\n%sInvalid argument for command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
                );
                return;
            }
        }

        ProcessBuilder pb = new ProcessBuilder(pbArgs);
        try {
            pb.start();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            GridRendering.terminalOut(String.format("\n%sError: Could not create new editor instance%s\n%s",
                Constants.delim, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        GridRendering.terminalOut(String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, grid
        );
    }

    public static void displayThemes(ArrayList<String> commandTokens, Grid grid) {
        String themesListString = "";

        switch (commandTokens.size()) {
            case 1:
                for (int i = 0, size = Theme.standardThemeNamesList.length; i < size; i++) {
                    themesListString += String.format("%s%s", Theme.standardThemeNamesList[i], Constants.delim);
                }

                GridRendering.terminalOut(String.format("\n%s[Available Themes]%s\n%s%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim, themesListString, Constants.delim), 1, grid
                );
                break;
            case 2:
                if (commandTokens.get(1).equalsIgnoreCase("more") == true) {
                    for (int i = 0, size = Theme.specialThemeNamesList.length; i < size; i++) {
                        themesListString += String.format("%s%s", Theme.specialThemeNamesList[i], Constants.delim);
                    }

                    GridRendering.terminalOut(String.format("\n%s[Available Themes]%s\n%s%s\n%s",
                        Constants.delim, Constants.delim, Constants.delim, themesListString, Constants.delim), 1, grid
                    );
                } else {
                    GridRendering.terminalOut(String.format("\n%sInvalid argument for command [%s]%s\n%s",
                        Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
                    );
                }
                break;
            default:
                GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
                );
                break;
        }
    }

    public static void changeTheme(ArrayList<String> commandTokens, Grid grid, JFrame frame) {
        if (commandTokens.size() > 2) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (commandTokens.size() < 2) {
            GridRendering.terminalOut(String.format("\n%sCurrent theme [%s]%s\n%s",
                Constants.delim, grid.theme.name, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (Theme.themeExists(commandTokens.get(1)) == false) {
            GridRendering.terminalOut(String.format("\n%sNot a valid theme [%s]%s\n%s",
                Constants.delim, commandTokens.get(1), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        grid.theme = new Theme(commandTokens.get(1));
        FileHandler.updateValue(Constants.editorConfigFile, "Theme", commandTokens.get(1));
        GridRendering.terminalOut(String.format("\n%sTheme changed to [%s]%s\n%s",
            Constants.delim, commandTokens.get(1), Constants.delim, Constants.delim), 1, grid
        );

        changeHudTheme(grid);

        frame.getContentPane().setBackground(grid.theme.frameBackground);
        GridRendering.resetSyntaxHighlighting(grid);
        frame.revalidate();
        frame.repaint();
    }

    private static void changeHudTheme(Grid grid) {
        for (int i = 0; i < grid.hudPanelArray.length; i++) {
            for (int j = 0; j < grid.hudPanelArray[i].length; j++) {
                grid.hudPanelArray[i][j].setBackground(grid.theme.unfocusedPanelBackground);
                grid.hudLabelArray[i][j].setForeground(grid.theme.unfocusedLabelForeground);
            }
        }
    }

    public static void activateOrDeactivateSyntaxHighlighting(ArrayList<String> commandTokens, Grid grid) {
        if (commandTokens.size() > 2) {
            GridRendering.terminalOut(String.format("\n%sToo many arguments for command [%s]%s\n%s",
                Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        if (commandTokens.size() < 2) {
            String onOrOff = "";
            if (grid.isSyntaxHighlightingActive == true) {
                onOrOff = "ON";
            } else {
                onOrOff = "OFF";
            }
            GridRendering.terminalOut(String.format("\n%s%s%s\n%s",
                Constants.delim, onOrOff, Constants.delim, Constants.delim), 1, grid
            );
            return;
        }

        switch (commandTokens.get(1)) {
            case "on":
                FileHandler.updateValue(Constants.editorConfigFile, "IsSyntaxHighlightingActive", "true");
                grid.isSyntaxHighlightingActive = true;
                GridRendering.terminalOut(String.format("\n%sSyntax highlighting switched to ON%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                break;
            case "off":
                FileHandler.updateValue(Constants.editorConfigFile, "IsSyntaxHighlightingActive", "false");
                grid.isSyntaxHighlightingActive = false;
                GridRendering.terminalOut(String.format("\n%sSyntax highlighting switched to OFF%s\n%s",
                    Constants.delim, Constants.delim, Constants.delim), 1, grid
                );
                break;
            default:
                GridRendering.terminalOut(String.format("\n%sInvalid argument for command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, grid
                );
                break;
        }
    }

    public static void saveTerminalScreenshot(Grid grid) {

        grid.terminalSession.sessionBigString = "";

        for (int i = grid.upperBuffer.size() - 1; i >= 0; i--) {
            String lineString = grid.upperBuffer.get(i);
            grid.terminalSession.sessionBigString += String.format("%s%s", lineString, Constants.delim);
        }

        int lastLineIndex = GridRendering.getLastLineIndex(grid);

        for (int i = 0; i <= lastLineIndex; i++) {
            String lineString = GridRendering.labelArrayToString(grid.labelArray[i]);

            if (lineString.length() == 0) {
                grid.terminalSession.sessionBigString += String.format("\n%s", Constants.delim);
            } else {
                grid.terminalSession.sessionBigString += String.format("%s%s", lineString, Constants.delim);
            }
        }

        grid.terminalSession.sessionBigString += String.format("%s%s%s",
            grid.terminalDirectory, Constants.afterDirString, Constants.delim
        );
    }

    public static String getCommandString(Grid grid) {

        String commandString = "";

        GridRendering.shiftTerminalUp(grid);

        int lastLineIndex = GridRendering.getLastLineIndex(grid);

        for (int i = grid.commandLineStartY; i <= lastLineIndex; i++) {
            commandString += GridRendering.labelArrayToString(grid.labelArray[i]);
        }

        return commandString.substring(grid.commandLineStartX); // So the directory isn't included.
    }
}
