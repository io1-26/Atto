import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class MainFrame implements KeyListener, ActionListener {

    //=============== INSTANTIATED OBJECTS =================
    private Timer caretBlinkTimer = new Timer(600, this);
    private Grid grid = new Grid();
    private JFrame frame;

    //=============== FLAGS ===================
    private int controlPressed = 0;

    public void showMainFrame(String arg) {

        FileHandler.createLocalFiles(Constants.editorHomeDirectory, Constants.editorConfigFile);

        applyUserConfig();

        // Comes after applyUserConfig(), because applyUserConfig() fetches the starting directory
        // from editorConfigFile and assigns it to this.grid.terminalDirectory.
        if (arg.equals("null") == false) {
            this.grid.terminalDirectory = arg;
        }

        this.frame = new JFrame("Atto");
        this.frame.addKeyListener(this);
        this.frame.getContentPane().setBackground(this.grid.theme.frameBackground);
        this.frame.setSize(1000, 700);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setLayout(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);

        // Waits for 1 second to ensure the window is fully open before calling loadGrid().
        // If the window is not fully open when loadGrid() is called, there could be some issues
        // when calculating a grid size that fits in the screen.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
            System.exit(1);
        }

        GridRendering.loadGrid(this.grid, this.frame);

        this.frame.revalidate();
        this.frame.repaint();

        this.caretBlinkTimer.start();
    }

    private void setOpenFileAsModified() {
        this.grid.isCurrentOpenFileModified = 1;
    }

    // Updates in which line the caret is so it can be displayed in hudLabelArray.
    private void updateCaretTracking() {
        int caretPosit = this.grid.upperBuffer.size() + this.grid.caretY + 1;
        String str = String.format("[Line %d/%d]", caretPosit, GridRendering.getLineCount(this.grid));
        str += "   [Ctrl+S] Save [Ctrl+X] Exit";
        GridRendering.stringToLabelArray(str, this.grid.hudLabelArray[this.grid.hudLabelArray.length - 1]);
    }

    private void applyUserConfig() {
        File file = new File(Constants.editorConfigFile);

        if (file.exists() == false) {
            return;
        }

        String themeName = FileHandler.getValue(Constants.editorConfigFile, "Theme");

        if (themeName == null) {
            return;
        }

        this.grid.theme = new Theme(themeName);

        String isSyntaxActive = FileHandler.getValue(Constants.editorConfigFile, "IsSyntaxHighlightingActive");

        if (isSyntaxActive == null) {
            return;
        }

        if (isSyntaxActive.equals("true") == true) {
            this.grid.isSyntaxHighlightingActive = true;
        } else if (isSyntaxActive.equals("false") == true) {
            this.grid.isSyntaxHighlightingActive = false;
        }

        String defaultTerminalDirectory = FileHandler.getValue(Constants.editorConfigFile, "DefaultStartingDirectory");

        if (defaultTerminalDirectory == null) {
            return;
        }

        this.grid.terminalDirectory = defaultTerminalDirectory;
    }

    private void processCommand() {
        String commandString = CommandFunctions.getCommandString(this.grid);

        ArrayList<String> commandTokens = Helper.tokenizeString(commandString);

        // User didn't input any command and pressed enter.
        if (commandTokens == null) {
            GridRendering.terminalOut(String.format("%s%s%s",
                this.grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, this.grid
            );
            return;
        }

        if (commandTokens.size() == 0) {
            GridRendering.terminalOut(String.format("%s%s%s",
                this.grid.terminalDirectory, Constants.afterDirString, Constants.delim), 0, this.grid
            );
            return;
        }

        switch (commandTokens.getFirst()) {
            case "commands":
                CommandFunctions.displayCommands(commandTokens, this.grid);
                break;
            case "cd":
                CommandFunctions.changeDirectory(commandTokens, commandString, this.grid);
                break;
            case "edit":
            case "open":
                CommandFunctions.openFile(commandTokens, commandString, this.grid, this.frame);
                break;
            case "dir":
            case "ls":
                CommandFunctions.listDirectory(commandTokens, this.grid);
                break;
            case "cls":
            case "clear":
                CommandFunctions.clearTerminal(commandTokens, this.grid);
                break;
            case "mkdir":
                CommandFunctions.mkdir(commandTokens, commandString, this.grid);
                break;
            case "mkfile":
                CommandFunctions.mkfile(commandTokens, commandString, this.grid);
                break;
            case "delete":
                CommandFunctions.deleteFile(commandTokens, commandString, this.grid);
                break;
            case "rename":
                CommandFunctions.renameFile(commandTokens, commandString, this.grid);
                break;
            case "shl":
            case "tmn":
            case "cmd":
                CommandFunctions.opencmd(commandTokens, this.grid);
                break;
            case "new":
                CommandFunctions.openNewEditorInstance(commandTokens, this.grid);
                break;
            case "syntax":
                CommandFunctions.activateOrDeactivateSyntaxHighlighting(commandTokens, this.grid);
                break;
            case "theme":
                CommandFunctions.changeTheme(commandTokens, this.grid, this.frame);
                break;
            case "themes":
                CommandFunctions.displayThemes(commandTokens, this.grid);
                break;
            case "ssd":
                CommandFunctions.changeDefaultStartingDirectory(commandTokens, this.grid);
                break;
            case "shgrid":
                CommandFunctions.showGridPattern(commandTokens, this.grid);
                break;
            case "hdgrid":
                CommandFunctions.hideGridPattern(commandTokens, this.grid);
                break;
            case "quit":
                System.out.println("quitting the application");
                System.exit(0);
            default:
                GridRendering.terminalOut(String.format("\n%sInvalid command [%s]%s\n%s",
                    Constants.delim, commandTokens.getFirst(), Constants.delim, Constants.delim), 1, this.grid
                );
                break;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        if (this.grid.gridLoaded == 0) {
            return;
        }

        int keyCode = e.getKeyCode();

        GridRendering.resetSyntaxHighlighting(this.grid);

        if (keyCode == KeyCodes.X) {
            if (this.grid.mode == Constants.EDIT_MODE && this.controlPressed == 1) {
                this.grid.mode = Constants.COMMAND_MODE;
                GridRendering.clearLine(this.grid.hudLabelArray[this.grid.hudLabelArray.length - 1]);
                GridRendering.saveFileSession(this.grid);
                this.frame.setTitle("Atto");
                this.grid.currentOpenFile = "";
                this.grid.isCurrentOpenFileModified = 0;
                GridRendering.clearGrid(this.grid);
                GridRendering.terminalOut(this.grid.terminalSession.sessionBigString, 0, this.grid);
                return;
            }
        }

        if (keyCode == KeyCodes.S) {
            if (this.grid.mode == Constants.EDIT_MODE && this.controlPressed == 1) {
                int saveFileStatus = FileHandler.saveFile(String.format("%s%s%s",
                    this.grid.terminalDirectory, Constants.fs, this.grid.currentOpenFile), this.grid
                );
                if (saveFileStatus == 0) {
                    this.grid.isCurrentOpenFileModified = 0;
                    GridRendering.displayCurrentOpenFile(this.grid, this.frame);
                    SyntaxHighlighter.highlightSyntax(this.grid);
                    GridRendering.focusCell(this.grid);
                    return;
                }
            }
        }

        if (e.getKeyCode() == KeyCodes.CONTROL) {
            if (this.grid.mode == Constants.EDIT_MODE && this.controlPressed == 0) {
                this.controlPressed = 1;
            }
            if (this.grid.mode == Constants.EDIT_MODE) {
                SyntaxHighlighter.highlightSyntax(this.grid);
            }
            GridRendering.focusCell(this.grid);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyCodes.BACKSPACE:
                if (this.grid.mode == Constants.EDIT_MODE) {
                    InputHandler.backSpace(this.grid);
                    setOpenFileAsModified();
                } else {
                    if (this.grid.caretY < this.grid.commandLineStartY) {
                        return;
                    }
                    if (this.grid.caretX <= this.grid.commandLineStartX) {
                        if (this.grid.caretY == this.grid.commandLineStartY) {
                            return;
                        }
                    }
                    InputHandler.backSpace(this.grid);
                }
                break;
            case KeyCodes.ENTER:
                if (this.grid.mode == Constants.COMMAND_MODE) {
                    processCommand();
                } else {
                    InputHandler.lineBreak(this.grid);
                    setOpenFileAsModified();
                }
                break;
            case KeyCodes.ARROW_LEFT:
                if (this.controlPressed == 1) {
                    InputHandler.moveCaretToLeftWithCtrl(this.grid);
                } else {
                    InputHandler.moveCaretToLeft(this.grid);
                }
                break;
            case KeyCodes.ARROW_UP:
                if (this.controlPressed == 1) {
                    InputHandler.moveCaretUpWithCtrl(this.grid);
                } else {
                    InputHandler.moveCaretUp(this.grid);
                }
                break;
            case KeyCodes.ARROW_RIGHT:
                if (this.controlPressed == 1) {
                    InputHandler.moveCaretToRightWithCtrl(this.grid);
                } else {
                    InputHandler.moveCaretToRight(this.grid);
                }
                break;
            case KeyCodes.ARROW_DOWN:
                if (this.controlPressed == 1) {
                    InputHandler.moveCaretDownWithCtrl(this.grid);
                } else {
                    InputHandler.moveCaretDown(this.grid);
                }
                break;
            default:
                if (this.grid.mode == Constants.EDIT_MODE) {
                    int printStatus = InputHandler.printCharacter(this.grid, e.getKeyChar());
                    if (printStatus == 0) {
                        setOpenFileAsModified();
                    }
                } else {

                    if (this.grid.caretY < this.grid.commandLineStartY) {
                        GridRendering.shiftTerminalUp(this.grid);
                        this.grid.caretY = this.grid.commandLineStartY;
                        this.grid.caretX = this.grid.commandLineStartX;
                    }

                    if (this.grid.caretX < this.grid.commandLineStartX) {
                        if (this.grid.caretY == this.grid.commandLineStartY) {
                            this.grid.caretY = this.grid.commandLineStartY;
                            this.grid.caretX = this.grid.commandLineStartX;
                        }
                    }

                    InputHandler.printCharacter(this.grid, e.getKeyChar());
                }
        }

        if (this.grid.mode == Constants.EDIT_MODE) {
            SyntaxHighlighter.highlightSyntax(this.grid);
            updateCaretTracking();
            GridRendering.displayCurrentOpenFile(grid, this.frame);
        }

        GridRendering.focusCell(this.grid);
        this.grid.caretFocusStatus = 1;

        this.caretBlinkTimer.restart();
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyCodes.CONTROL) {
            this.controlPressed = 0;
        }
    }

    // TODO: WHEN UNFOCUSING CELL IN EDIT_MODE, ONLY RESET AND REHIGHLIGHT THE CURRENT LINE FOR PERFORMANCE.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.caretBlinkTimer)) {
            if (this.grid.caretFocusStatus == 0) {
                this.grid.caretFocusStatus = 1;
                GridRendering.focusCell(this.grid);
            } else {
                this.grid.caretFocusStatus = 0;

                if (this.grid.mode == Constants.EDIT_MODE) {
                    GridRendering.resetSyntaxHighlighting(this.grid);
                    SyntaxHighlighter.highlightSyntax(this.grid);
                } else if (this.grid.mode == Constants.COMMAND_MODE) {
                    GridRendering.unfocusCell(this.grid);
                }
            }
        }
    }
}
