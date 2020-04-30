package Modele;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIA;
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
            Joueur[] joueurs = new Joueur[2];
            joueurs[0] = new JoueurIA(nomtest0, couleurtest0);
            joueurs[1] = new JoueurIA(nomtest1, couleurtest1);

            LecteurRedacteur lr = new LecteurRedacteur(pathtest, plateau, joueurs, joueurcouranttest);
            lr.EcrisPartie();

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void litPartie() {
            LecteurRedacteur lr = new LecteurRedacteur(pathtest);
            try {
                lr.LitPartie();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            assertNotNull(lr.plateau);
            assertNotNull(lr.joueurs);
            assertEquals(nbjoueurtest, lr.plateau.nbjoueur);
            assertEquals(tailletest, lr.plateau.GetGrille().length);
            assertEquals(joueurcouranttest, lr.joueurcourant);
            assertEquals(nomtest0, lr.joueurs[0].nom);
            assertEquals(couleurtest0, lr.joueurs[0].couleur);
            assertEquals(nomtest1, lr.joueurs[1].nom);
            assertEquals(couleurtest1, lr.joueurs[1].couleur);

    }


}