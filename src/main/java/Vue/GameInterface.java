package Vue;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInterface extends JPanel {
    JLabel toto;
    public GameInterface() {
        toto = new JLabel("toto");
        this.add(toto);
        this.updateUI();
    }
}
