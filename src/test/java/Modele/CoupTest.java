package Modele;

import Global.Tools;
import Model.*;
import Model.AI.NNManager;
import Model.Support.*;
import Model.Players.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Board board;
    Marble marble;
    Player player;
    Player player2;

    @BeforeEach
    public void init(){
        board = new Board();
        player = new AIEasyPlayer("default", Color.BLUE, board);
        player.setStartPoint(Tools.Direction.SO);
        board.addPlayer(player);
        marble = player.addMarble();
        board.getGrid()[2][2].addMarble(marble);
    }

    @Test
    public void TestCoup() {
        Move c = new Move(marble, Tools.Direction.NO, player);
        c.perform(board);
        assertFalse(board.getGrid()[2][2].hasMarble());
        assertTrue(board.getGrid()[1][1].hasMarble());
        c.cancel(board);
        assertFalse(board.getGrid()[1][1].hasMarble());
        assertTrue(board.getGrid()[2][2].hasMarble());

        System.out.println("Coup OK");
    }

    @Test
    public void TestHistorique() {
        History historique = new History(board);
        Move c1 = new Move(marble, Tools.Direction.NO, player);
        Move c2 = new Move(marble, Tools.Direction.SO, player);
        historique.doMove(c1);
        historique.doMove(c2);
        historique.undo();
        historique.redo();
        historique.undo();
        assertTrue(board.getGrid()[1][1].hasMarble());
        System.out.println("Historique OK");
    }

    /*@Test
    public void TestEntreeController() {
        player2 = new AIEasyPlayer("default", Color.RED, board);
        player2.setStartPoint(Tools.Direction.NE);
        board.addPlayer(player2);
        marble = player2.addMarble();
        board.getGrid()[3][3].addMarble(marble);
        MoveCalculator mc = new MoveCalculator(board);
        List<Move> coupspossible = mc.coupsPossibles();
        //LecteurRedacteur.AffichePartie(board);
        board.history.doMove(coupspossible.get(0));
        //LecteurRedacteur.AffichePartie(board);
        coupspossible = new MoveCalculator(board).coupsPossibles();
        board.history.doMove(player.Jouer(coupspossible));
        //LecteurRedacteur.AffichePartie(board);
    }
*/
/*    @Test
    public void TestJouerIA(){
        System.out.println("Test jouer AI");
        player2 = new AIEasyPlayer("default", Color.RED, board);
        player2.setStartPoint(Tools.Direction.NE);
        board.addPlayer(player2);
        marble = player2.addMarble();
        board.getGrid()[3][3].addMarble(marble);

        /*MoveCalculator mc = new MoveCalculator(board);
        List<Move> coupspossible = mc.coupsPossibles();
        board.history.doMove(coupspossible.get(0));
        coupspossible = new MoveCalculator(board).coupsPossibles();
        board.history.doMove(player.Jouer(coupspossible));*/
/*
        board.playGame();
    }*/

    @Test
    public void TestTrainIA(){
        System.out.println("In test train IA");
        NNManager nnManager = new NNManager();

    }
}


