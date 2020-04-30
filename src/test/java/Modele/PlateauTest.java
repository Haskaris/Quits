package Modele;

import Global.Tools;
import Modele.Support.Bille;
import Modele.Support.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {
    Plateau plateau;

    @BeforeEach
    public void init(){
        plateau = new Plateau(0,5);
    }

    @Test
    void CreationPlateau(){
        try {
            plateau = new Plateau(2, 5);
            LecteurRedacteur.PrintNiveau(plateau);
            plateau = new Plateau(4, 8);
            LecteurRedacteur.PrintNiveau(plateau);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deplacerBille() {
        try {
            Bille b = new Bille(1);
            plateau.GetGrille()[2][2].MettreBille(b,new Point(2,2));
            plateau.DeplacerBille(b, Tools.Dir.NO);
            assertTrue(plateau.GetGrille()[1][1].ContientBille());
            plateau.DeplacerBille(b, Tools.Dir.SO);
            plateau.DeplacerBille(b, Tools.Dir.SE);
            assertTrue(plateau.GetGrille()[1][3].ContientBille());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 1 OK");
    }

    @Test
    void deplacerRangee() {
        try {
            plateau.GetGrille()[2][2].MettreBille(new Bille(1),new Point(2,2));
            plateau.GetGrille()[2][3].MettreBille(new Bille(1),new Point(2,3));
            plateau.DeplacerRangee(new Point(-1,2),true);
            assertTrue(plateau.GetGrille()[2][3].ContientBille());
            assertTrue(plateau.GetGrille()[3][2].ContientBille());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 2 OK");
    }

}