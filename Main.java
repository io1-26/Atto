
public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        if (args.length > 0) {
            mainFrame.showMainFrame(args[0]);
        } else {
            mainFrame.showMainFrame("null");
        }
    }
}
