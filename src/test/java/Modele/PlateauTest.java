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
        plateau = new Plateau();
    }

    @Test
    void CreationPlateau(){
        try {
            plateau = new Plateau();
            //LecteurRedacteur.AffichePartie(plateau);
            plateau = new Plateau();
            //LecteurRedacteur.AffichePartie(plateau);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deplacerBille() {
        try {
            Bille b = new Bille(Color.BLUE);
            plateau.GetGrille()[2][2].addBille(b);
            plateau.DeplacerBille(b, Tools.Dir.NO);
            assertTrue(plateau.GetGrille()[1][1].contientBille());
            plateau.DeplacerBille(b, Tools.Dir.SO);
            plateau.DeplacerBille(b, Tools.Dir.SE);
            assertTrue(plateau.GetGrille()[1][3].contientBille());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 1 OK");
    }

    @Test
    void deplacerRangee() {
        try {
            plateau.GetGrille()[2][2].addBille(new Bille(Color.BLUE));
            plateau.GetGrille()[2][3].addBille(new Bille(Color.BLUE));
            plateau.DeplacerRangee(new Point(-1,2),true);
            assertTrue(plateau.GetGrille()[2][3].contientBille());
            assertTrue(plateau.GetGrille()[3][2].contientBille());
        }catch (Exception e){
            fail();
        }
        System.out.println("Plateau test 2 OK");
    }

}