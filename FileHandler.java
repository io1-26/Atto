import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// Useful functions to work with files.

public class FileHandler {

    private static String[] fileExtensions = {
        ".txt", ".java", ".c", ".h", ".cpp", ".py", ".html", ".css", ".js",
        ".asm", ".inb"
    };;

    public static ArrayList<String> getFileText(String filePath) {
        ArrayList<String> linesArray = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

            String fileLine = "";

            while ((fileLine = in.readLine()) != null) {
                if (fileLine.isBlank() == false && fileLine.isEmpty() == false) {
                    linesArray.add(fileLine.trim());
                }
            }

            in.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        return linesArray;
    }

    public static String getValue(String filePath, String key) {
        // Returns null if there is an IO exception.
        ArrayList<String> fileLines = getFileText(filePath);

        if (fileLines == null) {
            return null;
        }

        for (int i = 0, size = fileLines.size(); i < size; i++) {
            String fileLine = fileLines.get(i).trim();
            int separator = fileLine.indexOf('=');

            if (separator == -1 || separator == 0) {
                continue;
            }

            if (fileLine.substring(0, separator).trim().equals(key) == true) {
                return fileLine.substring(separator + 1).trim();
            }
        }

        return null;
    }
	
    // TODO: Make this function return 1 if it doesn't find the key.
    public static int updateValue(String filePath, String key, String newValue) {

        ArrayList<String> fileLines = getFileText(filePath);

        if (fileLines == null) {
            return -1;
        }

        String newFileLines = "";

        for (int i = 0, size = fileLines.size(); i < size; i++) {
            String fileLine = fileLines.get(i).trim();
            int separator = fileLine.indexOf('=');

            if (separator == -1 || separator == 0) {
                newFileLines += String.format("%s\n", fileLines.get(i));
                continue;
            }

            if (fileLine.substring(0, separator).trim().equals(key) == true) {
                newFileLines += String.format("%s = %s\n", key, newValue);
            } else {
                newFileLines += String.format("%s\n", fileLines.get(i));
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            if (fileLines.size() == 0) {
                fileWriter.write(String.format("%s = %s", key, newValue));
            } else {
                fileWriter.write(newFileLines);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            return -1;
        }

        return 0;
    }

    public static int loadFileIntoGrid(String filePath, Grid grid) {

        File file = new File(filePath);
        if (file.length() == 0) {
            return 0;
        }

        ArrayList<String> fileLines = new ArrayList<>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            while (true) {
                String fileLine = in.readLine();

                if (fileLine == null) {
                    break;
                }

                if (fileLine.isEmpty() == true) {
                    fileLines.add("\n");
                } else {
                    fileLines.add(fileLine);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.printf("[Error while opening input file: \"%s\"]\n", filePath);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.printf("[Error while reading line from input file: \"%s\"]\n", filePath);
            System.out.println(e.getMessage());
        }

        int index = 0;
        int maxLineSize = grid.labelArray[0].length;

        while (index < fileLines.size()) {
            if (fileLines.get(index).length() > maxLineSize) {
                String entireString = fileLines.get(index);
                fileLines.set(index, entireString.substring(0, maxLineSize));
                if (index == fileLines.size() - 1) {
                    fileLines.add(entireString.substring(maxLineSize));
                } else {
                    fileLines.add(index + 1, entireString.substring(maxLineSize));
                }
                index = 0;
                continue;
            }
            index++;
        }

        int lastLineIndex = grid.labelArray.length - 1;

        for (int i = 0, size = fileLines.size(); i < size; i++) {
            if (i <= lastLineIndex) {
                if (fileLines.get(i).equals("\n")) {
                    GridRendering.stringToLabelArray("", grid.labelArray[i]);
                } else {
                    GridRendering.stringToLabelArray(fileLines.get(i), grid.labelArray[i]);
                }
            } else {
                grid.lowerBuffer.add(fileLines.get(i));
            }
        }

        return 0;
    }

    public static int saveFile(String filePath, Grid grid) {

        String upperText = "";

        if (grid.upperBuffer.size() > 0) {
            for (int i = grid.upperBuffer.size() - 1; i >= 0; i--) {
                if (grid.upperBuffer.get(i).equals("\n")) {
                    upperText += "\n";
                } else {
                    upperText += String.format("%s\n", grid.upperBuffer.get(i));
                }
            }
        }

        String lowerText = "";

        if (grid.lowerBuffer.size() > 0) {
            for (int i = 0, size = grid.lowerBuffer.size(); i < size; i++) {
                if (grid.lowerBuffer.get(i).equals("\n")) {
                    lowerText += "\n";
                } else {
                    lowerText += String.format("%s\n", grid.lowerBuffer.get(i));
                }
            }
        }

        String gridText = "";

        int lli = GridRendering.getLastLineIndex(grid);
        if (lli > grid.labelArray.length - 1) {
            lli = grid.labelArray.length - 1;
        }

        if ((lli != 0) || (lli == 0 && GridRendering.isLineEmpty(grid.labelArray[0]) == false)) {
            for (int i = 0; i <= lli; i++) {
                if (GridRendering.isLineEmpty(grid.labelArray[i]) == true) {
                    gridText += "\n";
                } else {
                    gridText += String.format("%s\n", GridRendering.labelArrayToString(grid.labelArray[i]));
                }
            }
        }

        String fileText = upperText + gridText + lowerText;

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(fileText);
            fileWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return -1;
        }

        File file = new File(filePath);

        if (file.length() != fileText.getBytes().length) {
            return -1;
        }

        return 0;
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);

        return file.exists();
    }

    // Returns true if file name is valid (contains valid characters).
    public static boolean isFileNameValid(String fileName) {

        String[] invalidCharacters = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};

        for (int i = 0, size = invalidCharacters.length; i < size; i++) {
            if (fileName.contains(invalidCharacters[i]) == true) {
                return false;
            }
        }

        if (Helper.containsOnlyDots(fileName) == true) {
            return false;
        }

        return true;
    }

    public static boolean containsFile(File[] files, String fileName) {
        for (int i = 0, size = files.length; i < size; i++) {
            if (files[i].getName().equals(fileName) == true) {
                return true;
            }
        }
        return false;
    }

    // Given a file name, it checks if the extension is allowed or not.
    public static boolean isFileAllowed(String fileName) {
        for (int i = 0, size = fileExtensions.length; i < size; i++) {
            if (fileName.endsWith(fileExtensions[i]) == true) {
                return true;
            }
        }
        return false;
    }

    public static int createLocalFiles(String editorHomeDirectory, String editorConfigFile) {

        File file = new File(editorHomeDirectory);

        if (file.exists() == false) {
            boolean homeFolderCreation = file.mkdir();

            if (homeFolderCreation == false) {
                System.out.println("Could not create editorHomeDirectory");
                return -1;
            }
        }

        file = new File(String.format("%s%sTemp", editorHomeDirectory, Constants.fs));

        if (file.exists() == false) {
            boolean tempFolderCreation = file.mkdir();

            if (tempFolderCreation == false) {
                System.out.println("Could not create TempFolder");
                return -1;
            }
        }

        file = new File(editorConfigFile);

        if (file.exists() == false) {
            boolean configFileCreation;

            try {
                configFileCreation = file.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create editorConfigFile");
                return -1;
            }

            if (configFileCreation == false) {
                System.out.println("Could not create editorConfigFile");
                return -1;
            }

            try {
                FileWriter fileWriter = new FileWriter(editorConfigFile);
                String defaultConfig = String.format("Theme = light\nIsSyntaxHighlightingActive = false\nDefaultStartingDirectory = %s",
                    Constants.userHomeDirectory
                );
                fileWriter.write(defaultConfig);
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Failed using FileWriter in function: createLocalFiles");
                return -1;
            }
        }

        return 0;
    }
}
