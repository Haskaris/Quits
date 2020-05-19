package View;

import Controleur.Mediator;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainGraphicInterface extends JFrame {
    private InitGame initGame;
    private final Mediator mediator;
    
    /**
     * Constructeur
     */
    public MainGraphicInterface() {
        super("Quits");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mediator = new Mediator(this);
        
        super.setMinimumSize(new Dimension(675, 225));
        super.setSize(675, 225);
        
        this.initGame = new InitGame(this, mediator);
        
        super.add(initGame);
        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setVisible(true);
    }
    
    /**
     * Démare la partie
     */
    public void startGame() {
        //Pour "fermer" la fenêtre
        this.setVisible(false);
        GraphicInterface.start(this.mediator);
    }
    
    /**
     * Mets à jour la taille actuelle de la fenêtre et sa taille minimum
     * @param minWidth int - minimum width the frame can be
     * @param minHeight int - minimum height the frame can be
     */
    public void updateSize(int minWidth, int minHeight) {
        //this.setMinimumSize(new Dimension(minWidth, minHeight));
        this.setSize(minWidth, minHeight);
    }
    
    ///Getters
    public ArrayList<EditPlayer> getEditsPlayers() {
        return this.initGame.getEditsPlayers();
    }

    public void reset() {
        this.initGame.reset();
        this.setVisible(true);
    }
}
