package View;

import Controleur.Mediator;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainGraphicInterface extends JFrame {
    private final InitGame initGame;
    private final Mediator mediator;
    
    public MainGraphicInterface() {
        super("Quits");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mediator = new Mediator(this);
        
        super.setMinimumSize(new Dimension(675, 225));
        initGame = new InitGame(this, mediator);
        
        super.add(initGame);
        super.pack();
        super.setVisible(true);
    }
    
    public ArrayList<EditPlayer> getEditsPlayers() {
        return this.initGame.getEditsPlayers();
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
     * Uptade the minimum size
     * @param minWidth int - minimum width the frame can be
     * @param minHeight int - minimum height the frame can be
     */
    public void updateSize(int minWidth, int minHeight) {
        this.setMinimumSize(new Dimension(minWidth, minHeight));
        this.setSize(new Dimension(minWidth, minHeight));
    }
}
