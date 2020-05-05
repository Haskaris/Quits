
package Controleur;

import Global.Tools.AILevel;
import Modele.Joueurs.*;
import Modele.Support.Plateau;
import Vue.GraphicInterface;
import Vue.MainInterface;
import java.awt.Color;

public class Mediateur {
    Plateau plateau;
    GraphicInterface graphicInterface;
    MainInterface mainInterface;
    
    public Mediateur() {
    }
    
    private Joueur newPlayerHuman(String playerName, Color color) {
        return null;//new JoueurHumain(playerName, color);
    }
    
    private Joueur newPlayerAIEeasy(String playerName, Color color) {
        return null;//new JoueurIAFacile(playerName, color);
    }
    
    private Joueur newPlayerAIMedium(String playerName, Color color) {
        return null;//new JoueurIANormale(playerName, color);
    }
    
    private Joueur newPlayerAIHard(String playerName, Color color) {
        return null;//new JoueurIADifficile(playerName, color);
    }
    
    public void addPlayer(String playerName, Color color, AILevel level) {
        switch(level) {
            case Player:
                //plateau.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                //GameManager.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                //GameManager.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                //GameManager.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }
    
    public Joueur getPlayer(int index) {
        return null;//plateau.joueurs.get(index);
    }
    
    public void addGraphicInterface(GraphicInterface vue) {
        this.graphicInterface = vue;
    }
    
    public void addMainInterface(MainInterface vue) {
        this.mainInterface = vue;
    }
    
    public void initGame() {
        this.mainInterface.initGame();
    } 
}
