package Vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout; //Peut être à changer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InitGameInterface extends JPanel {
    private JButton buttonRules;
    private JLabel rulesMod;
    private JComboBox listMod;
    private JButton buttonPlay;
    private JPanel editPlayers;
    private MainInterface parent;
    
    public InitGameInterface(MainInterface parent) {
        super();
        this.parent = parent;
        this.rulesMod = new JLabel("Mode de jeu :");
        this.buttonRules = new JButton("Règles");
        this.buttonPlay = new JButton("Jouer");
        this.editPlayers = new JPanel();
        this.editPlayers.setLayout(new BoxLayout(this.editPlayers, BoxLayout.Y_AXIS));
        this.listMod = new JComboBox(new Object[]{"2 joueurs 5 billes", "2 joueurs 3 billes", "4 joueurs"});
        
        //Initialise le premier cas
        this.editPlayers.add(new EditPlayer("JoueurA", Color.BLUE));
        this.editPlayers.add(new EditPlayer("JoueurB", Color.RED));
        
        //Ajout du listener permettant de modifier l'affichage du nombre de joueur
        this.listMod.addActionListener ((ActionEvent e) -> {
            this.editPlayers.removeAll();
            switch(this.listMod.getSelectedIndex()) {
                case 0:
                    this.editPlayers.add(new EditPlayer("JoueurA", Color.BLUE));
                    this.editPlayers.add(new EditPlayer("JoueurB", Color.RED));
                    break;
                case 1:
                    this.editPlayers.add(new EditPlayer("JoueurA", Color.BLUE));
                    this.editPlayers.add(new EditPlayer("JoueurB", Color.RED));
                    break;
                case 2:
                    this.editPlayers.add(new EditPlayer("JoueurA", Color.BLUE));
                    this.editPlayers.add(new EditPlayer("JoueurB", Color.RED));
                    this.editPlayers.add(new EditPlayer("JoueurC", Color.YELLOW));
                    this.editPlayers.add(new EditPlayer("JoueurD", Color.GREEN));
                    break;
            }
            this.updateUI();
        });
        
        this.buttonPlay.addActionListener((ActionEvent e) -> {
            parent.initGame();
        });
        this.add(buttonRules);
        this.add(rulesMod);
        this.add(listMod);
        this.add(editPlayers);
        this.add(buttonPlay);
    }
    
    public ArrayList<EditPlayer> getEditPlayers() {
        ArrayList<EditPlayer> tmp = new ArrayList<>();
        //Je récupère tous le contenu de mon panel editPlayers
        //Il ne devrait y avoir que des EditPlayers
        Component[] components = this.editPlayers.getComponents();
        for (Component c: components) {
            //Si le component récupéré est un EditPlayer
            if (c.getClass().equals(EditPlayer.class)) {
                //Je l'ajoute à ma liste
                tmp.add((EditPlayer)c);
            }
        }
        return tmp;
    }
    
}
