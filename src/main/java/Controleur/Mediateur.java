
package Controleur;

import Global.Tools.AILevel;
import Modele.GameManager;
import Modele.Joueurs.*;
import java.awt.Color;

public class Mediateur {
    //Voué à mourir
    GameManager managerDeJeu;
    
    public Mediateur() {
        managerDeJeu = new GameManager();
    }
    
    private Joueur newPlayerHuman(String playerName, Color color) {
        return new JoueurHumain(playerName, color);
    }
    
    private Joueur newPlayerAIEeasy(String playerName, Color color) {
        return new JoueurIAFacile(playerName, color);
    }
    
    private Joueur newPlayerAIMedium(String playerName, Color color) {
        return new JoueurIANormale(playerName, color);
    }
    
    private Joueur newPlayerAIHard(String playerName, Color color) {
        return new JoueurIADifficile(playerName, color);
    }
    
    public void addPlayer(String playerName, Color color, AILevel level) {
        switch(level) {
            case Player:
                this.managerDeJeu.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                this.managerDeJeu.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                this.managerDeJeu.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                this.managerDeJeu.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }
    
    public Joueur getPlayer(int index) {
        return this.managerDeJeu.joueurs.get(index);
    }
}
