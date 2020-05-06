package Modele;

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
    String pathtest = "TestJunit.save";
    int nbjoueurtest = 2;
    int tailletest = 5;
    String nomtest0 = "Default1";
    Color couleurtest0 = Color.BLUE;
    String nomtest1 = "Default2";
    Color couleurtest1 = Color.GREEN;
    int joueurcouranttest = 0;

    @Test
    @Order(1)
    void ecrisPartie() {
        try {
            Board plateau = new Board();
            plateau.players = new Player[2];
            plateau.players[0] = new AINormalPlayer(nomtest0, Color.BLUE);
            plateau.players[1] = new AINormalPlayer(nomtest1, Color.RED);

            new ReaderWriter(pathtest).writeGame(plateau);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void litPartie() {
            Board plateau = null;
            try {
                plateau = new ReaderWriter(pathtest).readGame();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            assertNotNull(plateau);
            assertNotNull(plateau.players);
            assertEquals(nbjoueurtest,plateau.players.length);
            assertEquals(tailletest, plateau.getGrid().length);
            assertEquals(joueurcouranttest, plateau.currentPlayer);
            assertEquals(nomtest0, plateau.players[0].name);
            assertEquals(couleurtest0, plateau.players[0].color);
            assertEquals(nomtest1, plateau.players[1].name);
            assertEquals(couleurtest1, plateau.players[1].color);

    }


}