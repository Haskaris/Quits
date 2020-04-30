package Modele;

import Global.Tools;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;
import Modele.Support.Bille;
import Modele.Support.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoupTest {
    Bille b;

    @BeforeEach
    public void init(){
        GameManager.plateau = new Plateau(2,5);
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
        Joueur joueur = new JoueurIA("IA0",0);
        List<Coup> coupspossible = new CalculateurCoup(0,GameManager.plateau.BillesJoueur(0)).CoupsPossible();
        LecteurRedacteur.AffichePartie(GameManager.plateau);
        GameManager.historique.Faire(joueur.Jouer(coupspossible));
        LecteurRedacteur.AffichePartie(GameManager.plateau);
        coupspossible = new CalculateurCoup(1,GameManager.plateau.BillesJoueur(1)).CoupsPossible();
        GameManager.historique.Faire(joueur.Jouer(coupspossible));
        LecteurRedacteur.AffichePartie(GameManager.plateau);

    }


}