package Modele;

import Global.Tools;
import Model.*;
import Model.Support.*;
import Model.Players.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Board plateau;
    Marble b;
    Player joueur;

    @BeforeEach
    public void init(){
        joueur = new AIEasyPlayer("default", Color.BLUE);
        plateau = new Board();
        plateau.addPlayer(joueur);
        b = new Marble(Color.BLUE);
    }

    @Test
    public void TestCoup() {
        plateau.getGrid()[2][2].addMarble(b);
        Move c = new Move(b, Tools.Direction.NO, joueur);
        c.perform(plateau);
        assertFalse(plateau.getGrid()[2][2].hasMarble());
        assertTrue(plateau.getGrid()[1][1].hasMarble());
        c.cancel(plateau);
        assertFalse(plateau.getGrid()[1][1].hasMarble());
        assertTrue(plateau.getGrid()[2][2].hasMarble());

        System.out.println("Coup OK");
    }

    @Test
    public void TestHistorique() {
        History historique = new History(plateau);
        plateau.getGrid()[2][2].addMarble(b);
        Move c1 = new Move(b, Tools.Direction.NO,joueur);
        Move c2 = new Move(b, Tools.Direction.SO,joueur);
        historique.doMove(c1);
        historique.doMove(c2);
        historique.undo();
        historique.redo();
        historique.undo();
        assertTrue(plateau.getGrid()[1][1].hasMarble());
        System.out.println("Historique OK");
    }

    @Test
    public void TestEntreeController() {
        List<Move> coupspossible = new MoveCalculator(plateau).coupsPossibles();
        //LecteurRedacteur.AffichePartie(plateau);
        plateau.history.doMove(coupspossible.get(0));
        //LecteurRedacteur.AffichePartie(plateau);
        coupspossible = new MoveCalculator(plateau).coupsPossibles();
        plateau.history.doMove(joueur.Jouer(coupspossible));
        //LecteurRedacteur.AffichePartie(plateau);
    }
}


