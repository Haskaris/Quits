package Vue;

import Global.Tools.AILevel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPlayer extends JPanel {
    JTextField playerName;
    JComboBox CB_aiLevel;
    AILevel aiLevel;
    
    public EditPlayer(String playerName) {
        this.playerName = new JTextField(playerName);
        this.playerName.setPreferredSize(new Dimension(200,50));
        this.aiLevel = AILevel.Player;
        this.CB_aiLevel = new JComboBox(new Object[]{"Humain", "Ordinateur facile", "Ordinateur normal", "Ordinateur difficile"});
        this.CB_aiLevel.addActionListener ((ActionEvent e) -> {
            switch(this.CB_aiLevel.getSelectedIndex()) {
                case 0:
                    aiLevel = AILevel.Player;
                    break;
                case 1:
                    aiLevel = AILevel.Easy;
                    break;
                case 2:
                    aiLevel = AILevel.Medium;
                    break;
                case 3:
                    aiLevel = AILevel.Hard;
                    break;
            }
            this.updateUI();
        });
        this.add(this.playerName);
        this.add(this.CB_aiLevel);
        
    }
}
