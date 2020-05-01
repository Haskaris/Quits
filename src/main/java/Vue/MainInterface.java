package Vue;

import java.awt.Dimension;
import javax.swing.JFrame;

public class MainInterface extends JFrame {
    InitGameInterface initGameInterface;
    
    public static void main(String argv[]) {
        new MainInterface();
    }
    
    public MainInterface() {
        super();
        this.setTitle("Quits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500,500));
        initGameInterface = new InitGameInterface(this);
        this.add(initGameInterface);
        this.pack();
        this.setVisible(true);
    }
    
    public void changeForGame() {
        this.remove(initGameInterface);
        this.add(new GameInterface());
        this.repaint();
    }
}
