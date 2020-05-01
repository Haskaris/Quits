package Vue;

import java.awt.Dimension;
import javax.swing.JFrame;

public class InterfaceGraphique extends JFrame {
    
    public static void main(String argv[]) {
        new InterfaceGraphique();
    }
    
    public InterfaceGraphique() {
        super();
        this.setTitle("Quits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500,500));
        this.add(new InterfaceInit());
        this.pack();
        this.setVisible(true);
    }
}
