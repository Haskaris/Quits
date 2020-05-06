
package Controleur;

import Global.Tools.AILevel;
import Modele.Joueurs.*;
import Modele.Support.Plateau;
import Vue.GraphicInterface;
import Vue.MainInterface;
import java.awt.Color;
import java.awt.Point;

public class Mediateur {
    private Plateau plateau;
    GraphicInterface graphicInterface;
    MainInterface mainInterface;
    
    public Mediateur() {
        plateau = new Plateau();
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
                plateau.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                plateau.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                plateau.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                plateau.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }
    
    public Joueur getPlayer(int index) {
        return plateau.getPlayer(index);
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
    
    public Plateau getPlateau() {
        return this.plateau;
    }

    public void mouseClick(int l, int c) {
        this.plateau.DeplacerRangee(new Point(l, c), true);
    }
}
