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
    public static Joueur_Interface[] joueurs = new Joueur_Interface[4];
    public static Historique historique;
    public static int joueurcourant ;

    /**
     * Permet d'initialiser une partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void InstanceGame(){
        plateau = new Plateau((Integer)Configuration.Lis("Taille"),(Integer)Configuration.Lis("Joueurs"));

        interfacegraphique = new InterfaceGraphique();
        historique = new Historique();
        joueurcourant = 0;

    }

    /**
     * Permet au manageur de connaitre les joueurs
     */
    public static void EnregistrerJoueur(Joueur_Interface joueur){
        for (int i = 0; i < 4; i++) {
            if(joueurs[i] == null){
                joueurs[i] = joueur;
            }
        }
    }

    /**
     * Permet de jouer un coup. Ne doit etre utilisé que si joueurcourant est votre no de joueur. A voir si on rajoute une verification pour ca
     */
    public static void JouerTour(Coup coup){
        while(FinTour()){
            historique.Faire(joueurs[joueurcourant].Jouer(CoupsValide(joueurcourant)));
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private static boolean FinTour(){
        joueurcourant++;
        if(joueurcourant>=(Integer)Configuration.Lis("Joueurs"))
            joueurcourant =0;
        return true;
    }

    /**
     * Crée la liste de coup possible pour un joueur
     */
    public static Coup[] CoupsValide(int nojoueur){
        Coup[] coups = new Coup[0];
        return coups;
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
