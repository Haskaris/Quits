package Modele;

import Global.Tools;
import Model.*;
import Model.Support.*;
import Model.Players.*;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Board board;
    Marble marble;
    Player player;

    @BeforeEach
    public void init(){
        player = new AIEasyPlayer("default", Color.BLUE);
        player.setStartPoint(Tools.Direction.SO);
        board = new Board();
        board.addPlayer(player);
        marble = player.addMarble();
        board.getGrid()[2][2].addMarble(marble);
    }

    @Test
    public void TestCoup() {
        Move c = new Move(marble, Tools.Direction.NO, player.name);
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
        Move c1 = new Move(marble, Tools.Direction.NO, player.name);
        Move c2 = new Move(marble, Tools.Direction.SO, player.name);
        historique.doMove(c1);
        historique.doMove(c2);
        historique.undo();
        historique.redo();
        historique.undo();
        assertTrue(board.getGrid()[1][1].hasMarble());
        System.out.println("Historique OK");
    }

    @Test
    public void TestEntreeController() {
        MoveCalculator mc = new MoveCalculator(board);
        List<Move> coupspossible = mc.coupsPossibles();
        //LecteurRedacteur.AffichePartie(board);
        board.history.doMove(coupspossible.get(0));
        //LecteurRedacteur.AffichePartie(board);
        coupspossible = new MoveCalculator(board).coupsPossibles();
        board.history.doMove(player.Jouer(coupspossible));
        //LecteurRedacteur.AffichePartie(board);
    }

    @Test
    public void TestSerialization(){
        Move m = new Move(new Point(1,1), Tools.Direction.NO,player.name);
        SerializationUtils.serialize(m);
    }

    @Test
    public void TestReseau() throws Exception {
        WebSender webSender = new WebSender();
        webSender.Send(new Move(new Point(1,1), Tools.Direction.NO,player.name));
        Player player = new DistantPlayer("p", Color.RED);
        Move m = player.Jouer(null);
        m.print();
    }
}


