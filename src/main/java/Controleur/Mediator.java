package Controleur;

import Global.Tools;
import Global.Tools.AILevel;

import Model.Players.*;
import Model.Support.Board;
import Paterns.Observateur;
import View.EditPlayer;

import View.GraphicInterface;
import View.MainGraphicInterface;
import View.Dialogs.VictoryDialog;

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
        this.board.setMediator(this);
        if (this.graphicInterface != null) {
            this.graphicInterface.reset();
        } else {
            this.mainGraphicInterface.startGame();
        }
    }

    public void saveGame(String fileName) {
        this.fileGestion.saveGame(fileName);
    }
    
    public void resetGame() {
        this.board.reset();
        this.board.initFromGameMode();
        this.graphicInterface.update();
    }
    
    public void quitGame() {
        this.fileGestion.quitGame();
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
        tmp.forEach((e) -> {
            this.addPlayer(e.playerName, e.playerColor, e.aiLevel);
        });
        this.board.setGameMode(gameMode);
        this.board.initFromGameMode();
        
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
    
    public void addObservateur(Observateur a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean canUndo() {
        return !this.board.getHistory().isEmptyPast();
    }

    public boolean canRedo() {
        return !this.board.getHistory().isEmptyFuture();
    }

    public void undo() {
        this.board.getHistory().undo();
        this.graphicInterface.update();
    }

    public void redo() {
        this.board.getHistory().redo();
        this.graphicInterface.update();
    }

    public void endGame() {
        VictoryDialog VD = new VictoryDialog(this.mainGraphicInterface, true);
        VD.setVictoryText(this.board.getCurrentPlayer().name + " A GAGNÉ !!");
        VD.setVisible(true);
        
        switch(VD.getReturnStatus()) {
            case 0:
                this.quitGame();
                break;
            case 1:
                this.resetGame();
                break;
            case 2:
                //Well, we do nothing :x
                break;
            default:
                System.out.println("AH, une erreure innatendue a spawn");
        }
    }
}
