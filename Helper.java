import javax.swing.JLabel;
import java.util.ArrayList;

public class Helper {

    public static int copyLabelArray(JLabel[] srcArray, JLabel[] destArray) {
        for (int i = 0; i < srcArray.length; i++) {
            destArray[i].setText(srcArray[i].getText());
        }
        return 0;
    }

    public static int isFileInFileSessions(String filePath, ArrayList<FileSession> fileSessions) {
        for (int i = 0, size = fileSessions.size(); i < size; i++) {
            if (fileSessions.get(i).fileAbsolutePath.equals(filePath) == true) {
                return i;
            }
        }

        return -1;
    }

    // Returns true if file name contains only dots.
    public static boolean containsOnlyDots(String str) {
       	for (int i = 0, size = str.length(); i < size; i++) {
       	    if (str.charAt(i) != '.') {
	               return false;
	           }
        }
        return true;
    }

    public static int getLongestStringSize(ArrayList<String> lines) {

        if (lines == null) {
            System.out.println("\n>>\nIn class Helper, in function getLongestStringSize():");
            System.out.println("ArrayList<String> Lines is equal to null.\n>>\n");
            return -1;
        }

        if (lines.size() == 0) {
            System.out.println("\n>>\nIn class Helper, in function getLongestStringSize():");
            System.out.println("ArrayList<String> Lines has a size of 0.\n>>\n");
            return -1;
        }

        int longestString = 0;
        for (int i = 0, size = lines.size(); i < size; i++) {
            if (lines.get(i).length() > longestString) {
                longestString = lines.get(i).length();
            }
        }
        return longestString;
    }

    // A function that, given a string which has values separated by some delimitator,
    // returns the value at the given index, as a string.
    public static String getTokenAt(String str, int index) {
        final String delim = "%$delim$%";

        if (str == null) {
            return null;
        }
        if (str.length() == 0 || str.contains(delim) == false) {
            return null;
        }

        final int delimLength = delim.length();
        final char delimBegin = delim.charAt(0);

        int begin = 0;
        int iter = 0;

        for (int i = 0, size = str.length(); i < size; i++) {
            if (str.charAt(i) == delimBegin) {
                if (str.substring(i, i + delimLength).equals(delim)) {
                    if (iter == index) {
                        return str.substring(begin, i);
                    } else {
                        iter++;
                        begin = i + delimLength;
                        i = begin - 1;
                    }
                }
            }
        }

        return null;
    }


    // A function that tokenizes a string and returns an ArrayList<String> object containing those tokens.
    // It tokenizes the string based on spaces.
    public static ArrayList<String> tokenizeString(String str) {

        ArrayList<String> array = new ArrayList<>();

        if (str == null) {
            return null;
        }

        if (str.length() == 0 || str.isBlank()) {
            return array;
        }

        String temp = str.trim();

        while (true) {
            int space = temp.indexOf(' ');
            if (space == -1) {
                array.add(temp);
                break;
            } else {
                array.add(temp.substring(0, space));
                temp = temp.substring(space).trim();
            }
        }

        return array;
    }

    private void printBuffers(ArrayList<String> upperBuffer, ArrayList<String> lowerBuffer) {

        for (int i = 0; i < 50; i++) {
            System.out.print("\n");
        }

        System.out.println("=========================================================");
        for (int i = upperBuffer.size() - 1; i >= 0; i--) {
            if (upperBuffer.get(i).equals("\n") == true) {
                System.out.println("BLANK_LINE");
            } else {
                System.out.println(upperBuffer.get(i));
            }
        }
        System.out.println("=========================================================");
        for (int i = 0, size = lowerBuffer.size(); i < size; i++) {
            if (lowerBuffer.get(i).equals("\n") == true) {
                System.out.println("BLANK_LINE");
            } else {
                System.out.println(lowerBuffer.get(i));
            }
        }
        System.out.println("=========================================================");
    }


    // Returns true if the chr is a number.
    public static boolean isNum(char chr) {
        switch (chr) {
            case '0':
                break;
            case '1':
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
            case '7':
                break;
            case '8':
                break;
            case '9':
                break;
            default:
                return false;
        }
        return true;
    }

    // Returns true if [chr] is a legal variable character.
    public static boolean isAlphaNum(char chr) {
        switch (chr) {
            case 'a':
            case 'A':
                break;
            case 'b':
            case 'B':
                break;
            case 'c':
            case 'C':
                break;
            case 'd':
            case 'D':
                break;
            case 'e':
            case 'E':
                break;
            case 'f':
            case 'F':
                break;
            case 'g':
            case 'G':
                break;
            case 'h':
            case 'H':
                break;
            case 'i':
            case 'I':
                break;
            case 'j':
            case 'J':
                break;
            case 'k':
            case 'K':
                break;
            case 'l':
            case 'L':
                break;
            case 'm':
            case 'M':
                break;
            case 'n':
            case 'N':
                break;
            case 'o':
            case 'O':
                break;
            case 'p':
            case 'P':
                break;
            case 'q':
            case 'Q':
                break;
            case 'r':
            case 'R':
                break;
            case 's':
            case 'S':
                break;
            case 'T':
            case 't':
                break;
            case 'u':
            case 'U':
                break;
            case 'v':
            case 'V':
                break;
            case 'w':
            case 'W':
                break;
            case 'x':
            case 'X':
                break;
            case 'y':
            case 'Y':
                break;
            case 'z':
            case 'Z':
                break;
            case '0':
                break;
            case '1':
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
            case '7':
                break;
            case '8':
                break;
            case '9':
                break;
            case '_':
                break;
            default:
                return false;
        }
        return true;
    }

    // Returns if true if the char is made of only alphabetic letters.
    public static boolean isAlpha(char chr) {
        switch (chr) {
            case 'a':
            case 'A':
                break;
            case 'b':
            case 'B':
                break;
            case 'c':
            case 'C':
                break;
            case 'd':
            case 'D':
                break;
            case 'e':
            case 'E':
                break;
            case 'f':
            case 'F':
                break;
            case 'g':
            case 'G':
                break;
            case 'h':
            case 'H':
                break;
            case 'i':
            case 'I':
                break;
            case 'j':
            case 'J':
                break;
            case 'k':
            case 'K':
                break;
            case 'l':
            case 'L':
                break;
            case 'm':
            case 'M':
                break;
            case 'n':
            case 'N':
                break;
            case 'o':
            case 'O':
                break;
            case 'p':
            case 'P':
                break;
            case 'q':
            case 'Q':
                break;
            case 'r':
            case 'R':
                break;
            case 's':
            case 'S':
                break;
            case 'T':
            case 't':
                break;
            case 'u':
            case 'U':
                break;
            case 'v':
            case 'V':
                break;
            case 'w':
            case 'W':
                break;
            case 'x':
            case 'X':
                break;
            case 'y':
            case 'Y':
                break;
            case 'z':
            case 'Z':
                break;
            case '_':
                break;
            default:
                return false;
        }
        return true;
    }
}
