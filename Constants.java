public class Constants {
    public static final String delim = "%$delim$%";
    public static final int COMMAND_MODE = 1;
    public static final int EDIT_MODE = 2;
    public static final String afterDirString = "> ";
    public static final String currentOS = System.getProperty("os.name").toLowerCase();
    public static final String fs = System.getProperty("file.separator");
    public static final String userHomeDirectory = System.getProperty("user.home");
    public static final String editorHomeDirectory = String.format("%s%sAtto", userHomeDirectory, fs);
    public static final String editorConfigFile = String.format("%s%sConfig.txt", editorHomeDirectory, fs);
}
