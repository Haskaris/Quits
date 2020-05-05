package Modele;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIANormale;
import Modele.Support.Plateau;
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
            Plateau plateau = new Plateau(nbjoueurtest, tailletest);
            plateau.joueurs = new Joueur[2];
            plateau.joueurs[0] = new JoueurIANormale(nomtest0, 0);
            plateau.joueurs[1] = new JoueurIANormale(nomtest1, 1);

            new LecteurRedacteur(pathtest).EcrisPartie(plateau);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void litPartie() {
            Plateau plateau = null;
            try {
                plateau = new LecteurRedacteur(pathtest).LitPartie();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            assertNotNull(plateau);
            assertNotNull(plateau.joueurs);
            assertEquals(nbjoueurtest,plateau.joueurs.length);
            assertEquals(tailletest, plateau.GetGrille().length);
            assertEquals(joueurcouranttest, plateau.joueurcourant);
            assertEquals(nomtest0, plateau.joueurs[0].nom);
            assertEquals(couleurtest0, plateau.joueurs[0].couleur);
            assertEquals(nomtest1, plateau.joueurs[1].nom);
            assertEquals(couleurtest1, plateau.joueurs[1].couleur);

    }


}