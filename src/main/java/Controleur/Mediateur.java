
package Controleur;

import Global.Tools;
import Global.Tools.AILevel;
import Modele.Joueurs.*;
import Modele.Support.Plateau;
import Paterns.Observateur;
import Vue.GraphicInterface;
import Vue.MainInterface;
import java.awt.Color;
import java.awt.Point;

public class Mediateur {
    private Plateau plateau;
    public GraphicInterface graphicInterface;
    MainInterface mainInterface;
    
    public Mediateur() {
        plateau = new Plateau();
        this.plateau.setMediateur(this);
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
    
    /**
     * Prépare le plateau et change l'interface
     * @param gameMode 
     */
    public void initGame(Tools.GameMode gameMode) {
        this.mainInterface.initGame();
        this.plateau.setGameMode(gameMode);
        this.plateau.initPlayers();
    } 
    
    public Plateau getPlateau() {
        return this.plateau;
    }

    /**
     * Évenement de clique de souris sur la ligne l et la colonne c
     * @param l
     * @param c 
     */
    public void mouseClick(int l, int c) {
        this.plateau.playTurn();
    }

    public void addObservateur(GraphicInterface aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
