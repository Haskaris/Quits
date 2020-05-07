package Controleur;

import Model.Players.*;
import Global.Tools.*;
import Model.Support.Board;

import View.GraphicInterface;
import View.MainGraphicInterface;

import java.awt.Color;
import java.awt.Point;

public class Mediator {
    private Board board;
    GraphicInterface graphicInterface;
    MainGraphicInterface mainInterface;
    
    public Mediator() {
        board = new Board();
    }
    
    private Player newPlayerHuman(String playerName, Color color) {
        return new HumanPlayer(playerName, color);
    }
    
    private Player newPlayerAIEeasy(String playerName, Color color) {
        return new AIEasyPlayer(playerName, color);
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
    
    public void initGame() {
        this.mainInterface.initGame();
    } 
    
    public Board getPlateau() {
        return this.board;
    }

    public void mouseClick(int l, int c) {
        this.board.moveLine(new Point(l, c), Direction.S);
    }
}
