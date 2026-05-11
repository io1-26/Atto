import java.util.ArrayList;

public class FileSession {

    public int caretY = 0;
    public int caretX = 0;

    public String fileName = "";
    public String fileAbsolutePath = "";
    int isModified = 0;

    public ArrayList<String> upperBuffer = new ArrayList<>();
    public ArrayList<String> gridBuffer = new ArrayList<>();
    public ArrayList<String> lowerBuffer = new ArrayList<>();
}
