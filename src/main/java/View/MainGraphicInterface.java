package View;

import Controleur.Mediator;
import Global.Tools;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainGraphicInterface extends JFrame {
    InitGame initGameInterface;
    Mediator mediator;
    
    public MainGraphicInterface() {
        super();
        this.mediator = new Mediator();
        this.mediator.addMainInterface(this);
        
        this.setTitle("Quits");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //this.setMinimumSize(new Dimension(500,500));
        initGameInterface = new InitGame(mediator);
        initGameInterface.setParent(this);
        //gameInterface = new GameInterface(this);
        
        this.add(initGameInterface);
        this.pack();
        this.setVisible(true);
    }
    
    public void initGame() {
        //À mettre dans le mediator ?
        //Ajout des joueurs dans la partie
        ArrayList<EditPlayer> tmp = this.initGameInterface.getEditsPlayers();
        for (EditPlayer e : tmp) {
            this.mediator.addPlayer(e.playerName, e.playerColor, e.aiLevel);
        }
        
        //Pour "fermer" la fenêtre
        this.setVisible(false);
        
        //Démarer la partie
        GraphicInterface.start(mediator);
    }
}
