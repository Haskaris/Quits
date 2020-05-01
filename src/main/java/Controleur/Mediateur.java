
package Controleur;

import Global.Tools.AILevel;
import Modele.GameManager;
import Modele.Joueurs.*;
import java.awt.Color;

public class Mediateur {
    
    public Mediateur() {
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
                GameManager.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                GameManager.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                GameManager.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                GameManager.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }
    
    public Joueur getPlayer(int index) {
        return GameManager.joueurs.get(index);
    }
}
