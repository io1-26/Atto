import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Grid {

    Theme theme = new Theme("origin");
    public boolean isSyntaxHighlightingActive = false;

    public int gridLoaded = 0;
    public int mode = Constants.COMMAND_MODE;
    public int caretFocusStatus = 0;

    public Font defaultTextAreaFont;

    public ArrayList<FileSession> fileSessions = new ArrayList<>();
    public TerminalSession terminalSession = new TerminalSession();

    public String currentOpenFile = "";
    public int isCurrentOpenFileModified = 0;

    public String terminalDirectory = Constants.userHomeDirectory;

    public int caretY = 0;
    public int caretX = 0;

    public int maxCaretY = 0;
    public int maxCaretX = 0;

    public int commandLineStartY = 0;
    public int commandLineStartX = 0;

    public int selectionStartY = 0;
    public int selectionStartX = 0;
    public int selectionEndY = 0;
    public int selectionEndX = 0;

    public int initialXPosit = 0;
    public int initialYPosit = 0;

    public int panelWidth = 10;
    public int panelHeight = 19;

    public int gridHeight = 0;
    public int gridWidth = 0;

    public JPanel[][] panelArray;
    public JLabel[][] labelArray;

    public JPanel[][] hudPanelArray;
    public JLabel[][] hudLabelArray;

    public ArrayList<String> upperBuffer = new ArrayList<>();
    public ArrayList<String> lowerBuffer = new ArrayList<>();

    Grid() {
        if (Constants.currentOS.contains("win")) {
            this.defaultTextAreaFont = new Font("Consolas", Font.PLAIN, 17);
            this.panelWidth = 10;
            this.panelHeight = 19;
        } else if(Constants.currentOS.contains("nux")) {
            this.defaultTextAreaFont = new Font("Monospaced", Font.PLAIN, 20);
            this.panelWidth = 13;
            this.panelHeight = 24;
        }
    }
}
