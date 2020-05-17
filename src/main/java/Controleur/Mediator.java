package Controleur;

import Global.Tools;
import Global.Tools.AILevel;

import Model.Players.*;
import Model.Support.Board;
import Paterns.Observateur;
import View.Dialogs.RulesDialog;
import View.EditPlayer;

import View.GraphicInterface;
import View.MainGraphicInterface;
import View.Dialogs.VictoryDialog;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Timer;

public class Mediator {

    private Board board;
    private GraphicInterface graphicInterface;
    private MainGraphicInterface mainGraphicInterface;
    private final FileGestion fileGestion;
    
    private Timer timer;

    /**
     * Constructeur
     * @param mainGraphicInterface Interface permettant de faire la liaison
     */
    public Mediator(MainGraphicInterface mainGraphicInterface) {
        this.fileGestion = new FileGestion(this);
        this.mainGraphicInterface = mainGraphicInterface;
    }

    /**
     * Charge une partie
     * @param fileName Nom du fichier de la partie à charger
     */
    public void loadGame(String fileName) {
        this.board = this.fileGestion.loadGame(fileName);
        this.board.setMediator(this);
        if (this.graphicInterface != null) {
            this.graphicInterface.reset();
        } else {
            this.mainGraphicInterface.startGame();
        }
    }

    /**
     * Sauvegarde une partie
     * @param fileName Nom de la partie à sauvegarder
     */
    public void saveGame(String fileName) {
        this.fileGestion.saveGame(fileName);
    }
    
    /**
     * Réinitialise la partie
     */
    public void resetGame() {
        this.board.reset();
        this.board.initFromGameMode();
        this.graphicInterface.update();
    }
    
    /**
     * Quitte le jeu
     */
    public void quitGame() {
        this.fileGestion.quitGame();
    }
    
    /**
     * Prépare le plateau, change l'interface et lance la partie
     * @param gameMode GameMode - Mode de jeu du plateau
     */
    public void initGame(Tools.GameMode gameMode) {
        //Prépare le plateau
        this.board = new Board();
        this.board.setMediator(this);
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
     * @param c colonne où le clique a été effectué
     * @param l ligne où le clique a été effectué
     */
    public void mouseClick(int c, int l) {
        board.playTurn(c, l);
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

    /**
     * Evenement de fin de jeu
     */
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
            case 3:
                this.newGame();
                break;
            default:
                System.out.println("AH, une erreure innatendue a spawn");
        }
    }
    
    ///Private
    /**
     * Ajoute un nouveau joueur au plateau
     * @param playerName Nom du joueur
     * @param color Couleur du joueur
     * @param level Type du joueur
     */
    private void addPlayer(String playerName, Color color, AILevel level) {
        switch (level) {
            case Player:
                this.board.addPlayer(new HumanPlayer(playerName, color));
                break;
            case Easy:
                this.board.addPlayer(new AIEasyPlayer(playerName, color));
                break;
            case Hard:
                this.board.addPlayer(new AINormalPlayer(playerName, color));
                break;
            case Medium:
                this.board.addPlayer(new AIHardPlayer(playerName, color));
                break;
        }
    }
    
    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void setGraphicInterface(GraphicInterface view) {
        this.graphicInterface = view;
    }

    public void setMainGraphicInterface(MainGraphicInterface view) {
        this.mainGraphicInterface = view;
    }

    //Getters
    public Board getBoard() {
        return this.board;
    }

    public GraphicInterface getGraphicInterface() {
        return this.graphicInterface;
    }
    
    public Player getPlayer(int index) {
        return this.board.getPlayer(index);
    }

    public void newGame() {
        this.mainGraphicInterface.reset();
        this.graphicInterface.dispose();
    }

    public void rules() {
        RulesDialog rulesDialog = new RulesDialog(new javax.swing.JFrame(), true);
        rulesDialog.setTitle("Règles du Quits");
        rulesDialog.setVisible(true);
    }

    /**
     * Déplace la bille sélectionnée
     */
    public void updateSelectedMarble() {
        this.graphicInterface.boardGraphic.setSelectedMarble(this.board.selectedMarble);
        this.timer = new Timer(10, this.graphicInterface.boardGraphic);
        this.timer.start();
    }

    /**
     * Néttoie l'interface et arrête l'animation
     */
    public void clearSelectedMarble() {
        this.graphicInterface.boardGraphic.setSelectedMarble(null);
        this.timer.stop();
    }
}
