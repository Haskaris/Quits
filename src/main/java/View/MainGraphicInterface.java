package View;

import Controleur.Mediator;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainGraphicInterface extends JFrame {
    private final InitGame initGame;
    private final Mediator mediator;
    
    public MainGraphicInterface() {
        super("Quits");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.mediator = new Mediator(this);
        
        //this.setMinimumSize(new Dimension(500,500));
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
}
