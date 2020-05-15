package Modele;

import Global.Tools;
import Global.Tools.Direction;
import Model.Support.Marble;
import Model.Support.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {
    Board plateau;

    @BeforeEach
    public void init(){
        plateau = new Board();
    }

    @Test
    void CreationPlateau(){
        try {
            plateau = new Board();
            //LecteurRedacteur.AffichePartie(plateau);
            plateau = new Board();
            //LecteurRedacteur.AffichePartie(plateau);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deplacerBille() {
        try {
            Marble b = new Marble(Color.BLUE);
            plateau.getGrid()[2][2].addMarble(b);
            plateau.moveMarble(b, Tools.Direction.NW);
            assertTrue(plateau.getGrid()[1][1].hasMarble());
            plateau.moveMarble(b, Tools.Direction.SW);
            plateau.moveMarble(b, Tools.Direction.SE);
            assertTrue(plateau.getGrid()[1][3].hasMarble());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 1 OK");
    }

    @Test
    void deplacerRangee() {
        try {
            plateau.getGrid()[2][2].addMarble(new Marble(Color.BLUE));
            plateau.getGrid()[2][3].addMarble(new Marble(Color.BLUE));
            plateau.moveLine(new Point(2,2), Direction.E);
            assertTrue(plateau.getGrid()[2][3].hasMarble());
            assertTrue(plateau.getGrid()[3][2].hasMarble());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 2 OK");
    }

}