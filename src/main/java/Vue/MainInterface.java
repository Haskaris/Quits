package Vue;

import Controleur.Mediateur;
import Global.Tools;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainInterface extends JFrame {
    InitGame initGameInterface;
    Mediateur mediateur;
    
    public MainInterface() {
        super();
        this.mediateur = new Mediateur();
        this.mediateur.addMainInterface(this);
        
        this.setTitle("Quits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //this.setMinimumSize(new Dimension(500,500));
        initGameInterface = new InitGame(mediateur);
        //gameInterface = new GameInterface(this);
        
        this.add(initGameInterface);
        this.pack();
        this.setVisible(true);
    }
    
    public void initGame() {
        //À mettre dans le mediateur ?
        //Ajout des joueurs dans la partie
        ArrayList<EditPlayer> tmp = this.initGameInterface.getEditsPlayers();
        for (EditPlayer e : tmp) {
            this.mediateur.addPlayer(e.playerName, e.playerColor, e.aiLevel);
        }
        
        //Pour "fermer" la fenêtre
        this.setVisible(false);
        
        //Démarer la partie
        GraphicInterface.demarrer(mediateur);
    }
}
