package Controleur;

import Global.Tools;
import Global.Tools.AILevel;

import Model.Players.*;
import Model.Support.Board;
import View.EditPlayer;

import View.GraphicInterface;
import View.MainGraphicInterface;

import java.awt.Color;
import java.util.ArrayList;

public class Mediator {

    private Board board;
    private GraphicInterface graphicInterface;
    private MainGraphicInterface mainGraphicInterface;
    private FileGestion fileGestion;

    public Mediator(MainGraphicInterface mainGraphicInterface) {
        this.board = new Board();
        this.board.setMediator(this);
        this.fileGestion = new FileGestion(this);
        this.mainGraphicInterface = mainGraphicInterface;
    }

    /**
     * Créée un nouveau joueur humain
     * @param playerName
     * @param color
     * @return Player
     */
    private Player newPlayerHuman(String playerName, Color color) {
        return new HumanPlayer(playerName, color);
    }

    /**
     * Créée une nouvelle IA facile
     * @param playerName
     * @param color
     * @return Player
     */
    private Player newPlayerAIEeasy(String playerName, Color color) {
        return new AIEasyPlayer(playerName, color);
    }

    /**
     * Créée une nouvelle IA normale
     * @param playerName
     * @param color
     * @return Player
     */
    private Player newPlayerAIMedium(String playerName, Color color) {
        return new AINormalPlayer(playerName, color);
    }

    /**
     * Créée une nouvelle IA difficile
     * @param playerName
     * @param color
     * @return Player
     */
    private Player newPlayerAIHard(String playerName, Color color) {
        return new AIHardPlayer(playerName, color);
    }

    /**
     * Ajoute un nouveau joueur au plateau
     * @param playerName
     * @param color
     * @param level 
     */
    private void addPlayer(String playerName, Color color, AILevel level) {
        switch (level) {
            case Player:
                this.board.addPlayer(newPlayerHuman(playerName, color));
                break;
            case Easy:
                this.board.addPlayer(newPlayerAIEeasy(playerName, color));
                break;
            case Hard:
                this.board.addPlayer(newPlayerAIHard(playerName, color));
                break;
            case Medium:
                this.board.addPlayer(newPlayerAIMedium(playerName, color));
                break;
        }
    }

    public void loadGame(String fileName) {
        this.board = this.fileGestion.loadGame(fileName);
        if (this.graphicInterface != null) {
            this.graphicInterface.reset();
        } else {
            this.mainGraphicInterface.startGame();
        }
    }
    
    public void quitGame() {
        this.fileGestion.quitGame();
    }

    public void saveGame(String fileName) {
        this.fileGestion.saveGame(fileName);
    }
    
    public void setGraphicInterface(GraphicInterface view) {
        this.graphicInterface = view;
    }

    public void setMainGraphicInterface(MainGraphicInterface view) {
        this.mainGraphicInterface = view;
    }

    /**
     * Prépare le plateau, change l'interface et lance la partie
     *
     * @param gameMode
     */
    public void initGame(Tools.GameMode gameMode) {
        //Prépare le plateau
        ArrayList<EditPlayer> tmp = this.mainGraphicInterface.getEditsPlayers();
        for (EditPlayer e : tmp) {
            this.addPlayer(e.playerName, e.playerColor, e.aiLevel);
        }
        this.board.setGameMode(gameMode);
        this.board.initPlayers();
        
        //Change l'interface et lance la partie
        this.mainGraphicInterface.startGame();
    }

    /**
     * Évenement de clique de souris sur la ligne l et la colonne c
     *
     * @param l
     * @param c
     */
    public void mouseClick(int c, int l) {
        //this.board.moveLine(new Point(l, c), Direction.S);
        System.out.println("Clicked on the case " + c + " : " + l + ".");
        board.playTurn(c, l);
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public Board getBoard() {
        return this.board;
    }

    public GraphicInterface getGraphicInterface() {
        return this.graphicInterface;
    }
    
    public Player getPlayer(int index) {
        return this.board.getPlayer(index);
    }
    
    public void addObservateur(View.GraphicInterface aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
