package Modele;

import Global.Tools;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIAFacile;
import Modele.Joueurs.JoueurIANormale;
import Modele.Support.Bille;
import Modele.Support.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Plateau plateau;
    Bille b;
    Joueur joueur;

    @BeforeEach
    public void init(){
        joueur = new JoueurIAFacile("default", Color.BLUE);
        plateau = new Plateau();
        b = new Bille(Color.BLUE);
    }

    @Test
    public void TestCoup() {
        plateau.GetGrille()[2][2].addBille(b);
        Coup c = new Coup(b, Tools.Dir.NO, joueur);
        c.Execute(plateau);
        assertFalse(plateau.GetGrille()[2][2].contientBille());
        assertTrue(plateau.GetGrille()[1][1].contientBille());
        c.Dexecute(plateau);
        assertFalse(plateau.GetGrille()[1][1].contientBille());
        assertTrue(plateau.GetGrille()[2][2].contientBille());

        System.out.println("Coup OK");
    }

    @Test
    public void TestHistorique() {
        Historique historique = new Historique(plateau);
        plateau.GetGrille()[2][2].addBille(b);
        Coup c1 = new Coup(b, Tools.Dir.NO,joueur);
        Coup c2 = new Coup(b, Tools.Dir.SO,joueur);
        historique.Faire(c1);
        historique.Faire(c2);
        historique.Annuler();
        historique.Refaire();
        historique.Annuler();
        assertTrue(plateau.GetGrille()[1][1].contientBille());
        System.out.println("Historique OK");
    }

    @Test
    public void TestEntreeController() {
        List<Coup> coupspossible = new CalculateurCoup(plateau,joueur).coupsPossibles();
        //LecteurRedacteur.AffichePartie(plateau);
        plateau.historique.Faire(coupspossible.get(0));
        //LecteurRedacteur.AffichePartie(plateau);
        coupspossible = new CalculateurCoup(plateau,joueur).coupsPossibles();
        plateau.historique.Faire(joueur.Jouer(coupspossible));
        //LecteurRedacteur.AffichePartie(plateau);
    }
}


