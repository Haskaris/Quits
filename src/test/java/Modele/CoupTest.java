package Modele;

import Global.Tools;
import Modele.Support.Bille;
import Modele.Support.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Bille b;

    @BeforeEach
    public void init(){
        GameManager.plateau = new Plateau(2,5);
        b = new Bille (2);
        GameManager.plateau.GetGrille()[2][2].MettreBille(b,new Point(2,2));
    }

    @Test
    public void TestCoup() {
        try{
            Coup c = new Coup(b, Tools.Dir.NO);
            c.Execute();
            assertFalse(GameManager.plateau.GetGrille()[2][2].ContientBille());
            assertTrue(GameManager.plateau.GetGrille()[1][1].ContientBille());
            c.Dexecute();
            assertFalse(GameManager.plateau.GetGrille()[1][1].ContientBille());
            assertTrue(GameManager.plateau.GetGrille()[2][2].ContientBille());
        }
        catch (Exception e){
            fail();
        }
        System.out.println("Coup OK");
    }

    @Test
    public void TestHistorique() {
        try{
            Coup c1 = new Coup(b, Tools.Dir.NO);
            Coup c2 = new Coup(b, Tools.Dir.SO);
            Historique h = new Historique();
            h.Faire(c1);
            h.Faire(c2);
            h.Annuler();
            h.Refaire();
            h.Annuler();
            assertTrue(GameManager.plateau.GetGrille()[1][1].ContientBille());
        }
        catch (Exception e){
            fail();
        }
        System.out.println("Historique OK");
    }

    @Test
    public void TestEntreeController() {

    }


}