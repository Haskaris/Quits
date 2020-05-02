package Modele;

import Global.Configuration;
import Global.Properties;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIAFacile;
import Modele.Support.Bille;
import Modele.Support.Plateau;
import Vue.InterfaceGraphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;

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
    public static Historique historique;

    /**
     * Permet d'initialiser une partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void InstanceGame(){

        plateau = new Plateau((Integer)Configuration.Lis("Joueurs"),(Integer)Configuration.Lis("Taille"));



        interfacegraphique = new InterfaceGraphique();
        historique = new Historique();

        LecteurRedacteur.AffichePartie(plateau);
        JouerTour();
    }

    /**
     *  Joue les tours de la partie. S'arrete à la fin
     */
    public static void JouerTour(){
        while(FinTour()){
            List<Coup> coupspossible = new CalculateurCoup(plateau.JoueurCourant()).CoupsPossible();
            Coup coup = plateau.JoueurCourant().Jouer(coupspossible);
            historique.Faire(coup);
            LecteurRedacteur.AffichePartie(plateau);
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private static boolean FinTour(){
        boolean estfini = true;
        for (Bille bille:plateau.JoueurCourant().billes) {
            if(!bille.EstSortie())
                estfini = false;
        }

        if(estfini){
            System.out.println("Joueur " + plateau.joueurcourant + " a gagné");
            return false;
        }

        plateau.joueurcourant ++;
        if(plateau.joueurcourant>=(Integer)Configuration.Lis("Joueurs"))
            plateau.joueurcourant =0;
        return true;
    }



    /**
     * Charge une partie
     */
    public static void ChargerPartie(){
        try {
            plateau = new LecteurRedacteur("default.save").LitPartie();
        }
        catch (Exception e){
            System.out.println("Erreur de chargement de la partie");
        }

        interfacegraphique = new InterfaceGraphique();
        historique = new Historique();

        JouerTour();
    }

    /**
     * Enregistre une partie
     */
    public static void EnregistrerPartie(){
        try {
            new LecteurRedacteur("default.save").EcrisPartie(plateau);
        }
        catch (Exception e){
            System.out.println("Erreur d'enregistrement de la partie");
        }

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
