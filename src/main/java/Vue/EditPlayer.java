package Vue;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPlayer extends JPanel {
    JTextField playerName;
    JComboBox iaLevel;
    
    public EditPlayer(String playerName) {
        this.playerName = new JTextField(playerName);
        this.playerName.setPreferredSize(new Dimension(200,50));
        this.iaLevel = new JComboBox(new Object[]{"Humain", "Ordinateur facile", "Ordinateur normal", "Ordinateur difficile"});
        this.add(this.playerName);
        this.add(this.iaLevel);
    }
}
