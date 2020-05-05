package Vue;

import Controleur.Mediateur;
import Global.Tools;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainInterface extends JFrame {
    InitGameInterface initGameInterface;
    GameInterface gameInterface;
    Mediateur mediateur;
    
    public MainInterface() {
        super();
        this.mediateur = new Mediateur();
        this.mediateur.addMainInterface(this);
        
        this.setTitle("Quits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //this.setMinimumSize(new Dimension(500,500));
        //initGameInterface = new InitGameInterface(this);
        //gameInterface = new GameInterface(this);
        
        this.add(new InitGameAuto(mediateur));
        this.pack();
        this.setVisible(true);
    }
    
    public void initGame() {
        //À mettre dans le mediateur ?
        //Ajout des joueurs dans la partie
        ArrayList<EditPlayer> tmp = this.initGameInterface.getEditPlayers();
        for (EditPlayer e : tmp) {
            this.mediateur.addPlayer(e.playerName.getText(), Color.yellow, e.aiLevel);
        }
        this.remove(initGameInterface);
        this.add(gameInterface);
        gameInterface.updatePlayer1(this.mediateur.getPlayer(0).nom);
        this.repaint();
    }
}
