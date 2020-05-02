package Modele;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIANormale;
import Modele.Support.Plateau;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LecteurRedacteurTest {
    String pathtest = "TestJunit.save";
    int nbjoueurtest = 2;
    int tailletest = 5;
    String nomtest0 = "Default1";
    int couleurtest0 = 0;
    String nomtest1 = "Default2";
    int couleurtest1 = 1;
    int joueurcouranttest = 0;

    @Test
    @Order(1)
    void ecrisPartie() {
        try {
            Plateau plateau = new Plateau(nbjoueurtest, tailletest);
            plateau.joueurs = new Joueur[2];
            plateau.joueurs[0] = new JoueurIANormale(nomtest0, couleurtest0);
            plateau.joueurs[1] = new JoueurIANormale(nomtest1, couleurtest1);

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