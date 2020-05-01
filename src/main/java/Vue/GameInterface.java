package Vue;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInterface extends JPanel {
    JLabel Player1;
    MainInterface parent;
    
    public GameInterface(MainInterface parent) {
        super();
        this.parent = parent;
        Player1 = new JLabel("toto");
        this.add(Player1);
        this.updateUI();
    }
    
    public void updatePlayer1(String player1Name) {
        Player1.setText(player1Name);
        this.updateUI();
    }
}
