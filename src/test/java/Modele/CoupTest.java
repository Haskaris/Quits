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
    Bille b;
    Joueur joueurs[] = new Joueur[2];

    @BeforeEach
    public void init(){
        joueurs[0] = new JoueurIAFacile("default",0);
        joueurs[1] = new JoueurIAFacile("default",1);
        GameManager.plateau = new Plateau(2,5,joueurs);
        b = new Bille (2);
        GameManager.historique = new Historique();
    }

    @Test
    public void TestCoup() {
        GameManager.plateau.GetGrille()[2][2].MettreBille(b,new Point(2,2));
        Coup c = new Coup(b, Tools.Dir.NO);
        c.Execute();
        assertFalse(GameManager.plateau.GetGrille()[2][2].ContientBille());
        assertTrue(GameManager.plateau.GetGrille()[1][1].ContientBille());
        c.Dexecute();
        assertFalse(GameManager.plateau.GetGrille()[1][1].ContientBille());
        assertTrue(GameManager.plateau.GetGrille()[2][2].ContientBille());

        System.out.println("Coup OK");
    }

    @Test
    public void TestHistorique() {
        GameManager.plateau.GetGrille()[2][2].MettreBille(b,new Point(2,2));
        Coup c1 = new Coup(b, Tools.Dir.NO);
        Coup c2 = new Coup(b, Tools.Dir.SO);
        GameManager.historique.Faire(c1);
        GameManager.historique.Faire(c2);
        GameManager.historique.Annuler();
        GameManager.historique.Refaire();
        GameManager.historique.Annuler();
        assertTrue(GameManager.plateau.GetGrille()[1][1].ContientBille());
        System.out.println("Historique OK");
    }

    @Test
    public void TestEntreeController() {
        List<Coup> coupspossible = new CalculateurCoup(0,joueurs[0].billes).CoupsPossible();
        LecteurRedacteur.AffichePartie(GameManager.plateau);
        GameManager.historique.Faire(joueurs[0].Jouer(coupspossible));
        LecteurRedacteur.AffichePartie(GameManager.plateau);
        coupspossible = new CalculateurCoup(1,joueurs[1].billes).CoupsPossible();
        GameManager.historique.Faire(joueurs[1].Jouer(coupspossible));
        LecteurRedacteur.AffichePartie(GameManager.plateau);

    }


}