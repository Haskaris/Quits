package Modele;

import Global.Tools;
import Model.ReaderWriter;
import Model.Players.Player;
import Model.Players.AINormalPlayer;
import Model.Support.Board;
import java.awt.Color;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LecteurRedacteurTest {
    String pathTest = "TestJunit.save";
    int playerNumberTest = 2;
    int boardSizeTest = 5;
    String namePlayer0Test = "Default1";
    Color colorPlayer0Test = Color.BLUE;
    String namePlayer1Test = "Default2";
    Color colorPlayer1Test = Color.GREEN;
    int currentPlayerTest = 0;
    Tools.GameMode gm = Tools.GameMode.TwoPlayersThreeBalls;

    @Test
    @Order(1)
    void ecrisPartie() {
        try {
            Board board = new Board();
            
            board.setGameMode(gm);
            
            Player tmpPlayer0 = new AINormalPlayer(namePlayer0Test, colorPlayer0Test, board);
            tmpPlayer0.setStartPoint(Tools.Direction.SW);
            Player tmpPlayer1 = new AINormalPlayer(namePlayer1Test, colorPlayer1Test, board);
            tmpPlayer1.setStartPoint(Tools.Direction.NE);
            
            board.addPlayer(tmpPlayer0);
            board.placeMarbleOn(tmpPlayer0.addMarble(), 1, 1);
            
            board.addPlayer(tmpPlayer1);
            board.placeMarbleOn(tmpPlayer1.addMarble(), 2, 2);
            
            ReaderWriter rw = new ReaderWriter(pathTest);
            rw.writeGame(board);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            fail();
        }
        System.out.println("Écriture d'une partie OK");
    }

    @Test
    @Order(2)
    void litPartie() {
        Board board = null;
        try {
            ReaderWriter rw = new ReaderWriter(pathTest);
            board = rw.readGame();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(board);
        assertNotNull(board.getPlayers());
        assertEquals(playerNumberTest, board.getPlayers().size());
        assertEquals(boardSizeTest, board.getGrid().length);
        assertEquals(currentPlayerTest, board.currentPlayer);
        assertEquals(namePlayer0Test, board.getPlayer(0).name);
        assertEquals(colorPlayer0Test, board.getPlayer(0).color);
        assertEquals(namePlayer1Test, board.getPlayer(1).name);
        assertEquals(colorPlayer1Test, board.getPlayer(1).color);
        System.out.println("Lecture d'une partie OK");
    }


}