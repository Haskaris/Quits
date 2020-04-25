package Modele;

import Controleur.Joueur;
import Global.Configuration;
import Global.Properties;
import Vue.InterfaceGraphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameManager {
    /**
     * Les actions raccourcis claviers (elle viennent du Sokoban). A voir si on garde ou non
     */
    public static KeyListener GameKeyListener=new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {}

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            // if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) interfacegraphique.toggleFullScreen();

            // if (keyEvent.getKeyCode() == KeyEvent.VK_F11) interfacegraphique.toggleAnimation();

            if (keyEvent.getKeyCode() == KeyEvent.VK_Q)
                Exit();

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {}
    };

    /**
     * Ici la liste des Objets accessibles aux autres parties du jeu
     */
    public static Plateau plateau;
    public static InterfaceGraphique interfacegraphique;
    public static Joueur[] joueurs;
    public static Historique historique;
    public static int joueurcourant ;

    /**
     * Permet d'initialiser une partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void InstanceGame(){
        plateau = new Plateau((Integer)Configuration.Lis("Taille"),(Integer)Configuration.Lis("Joueurs"));

        joueurs = new Joueur[(Integer)Configuration.Lis("Joueurs")];
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i] = new Joueur();
        }

        interfacegraphique = new InterfaceGraphique();
        historique = new Historique();
        joueurcourant = 0;

    }

    /**
     * Permet de jouer un coup. Ne doit etre utilisé que si joueurcourant est votre no de joueur. A voir si on rajoute une verification pour ca
     */
    public static boolean JouerTour(Coup coup){
        if(EstCoupValide(coup,joueurcourant)){
            historique.Faire(coup);
            FinTour();
            return true;
        }
        else
            return false;
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private static void FinTour(){
        joueurcourant++;
        if(joueurcourant>=(Integer)Configuration.Lis("Joueurs"))
            joueurcourant =0;
    }

    /**
     * Verifie qu'un coup joué verifie bien les règles. Prend le coup et le numero de joueur en parametre
     */
    public static boolean EstCoupValide(Coup coup, int nojoueur){
        return true;
    }

    /**
     * Fermeture du jeu
     */
    public static void Exit(){
        try {
            Properties.Store();
        } catch (IOException e) {
            Configuration.logger().severe("Erreur d'ecriture des Properties");
            System.exit(1);
        }
        System.exit(0);
    }



}
