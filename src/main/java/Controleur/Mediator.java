package Controleur;

import Global.Tools;
import Global.Tools.AILevel;
import Global.Tools.Direction;

import Model.Players.*;
import Model.Support.Board;

import View.GraphicInterface;
import View.MainGraphicInterface;

import java.awt.Color;
import java.awt.Point;

public class Mediator {
    private Board board;
    private GraphicInterface graphicInterface;
    private MainGraphicInterface mainInterface;
    
    public Mediator() {
        this.board = new Board();
        this.board.setMediator(this);
    }
    
    private Player newPlayerHuman(String playerName, Color color) {
        return new HumanPlayer(playerName, color);
    }
    
    private Player newPlayerAIEeasy(String playerName, Color color) {
        return new AIEasyPlayer(playerName, color, board);
    }
    
    private Player newPlayerAIMedium(String playerName, Color color) {
        return new AINormalPlayer(playerName, color);
    }
    
    private Player newPlayerAIHard(String playerName, Color color) {
        return new AIHardPlayer(playerName, color);
    }
    
    public void addPlayer(String playerName, Color color, AILevel level) {
        switch(level) {
            case Player:
                board.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                board.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                board.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                board.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }
    
    public Player getPlayer(int index) {
        return board.getPlayer(index);
    }
    
    public void addGraphicInterface(GraphicInterface vue) {
        this.graphicInterface = vue;
    }
    
    public void addMainInterface(MainGraphicInterface vue) {
        this.mainInterface = vue;
    }
    
    /**
     * Prépare le plateau et change l'interface
     * @param gameMode 
     */
    public void initGame(Tools.GameMode gameMode) {
        this.mainInterface.initGame();
        this.board.setGameMode(gameMode);
        this.board.initPlayers();
    } 
    
    public Board getPlateau() {
        return this.board;
    }
    
    public GraphicInterface getGraphicInterface() {
        return this.graphicInterface;
    }

    /**
     * Évenement de clique de souris sur la ligne l et la colonne c
     * @param l
     * @param c 
     */
    public void mouseClick(int l, int c) {
        this.board.moveLine(new Point(l, c), Direction.S);
    }

    public void addObservateur(View.GraphicInterface aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
