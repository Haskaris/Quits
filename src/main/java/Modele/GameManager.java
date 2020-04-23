package Modele;

import Controleur.Joueur;
import Global.Configuration;
import Global.Properties;
import Vue.InterfaceGraphique;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameManager {
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

    /*
     * Ici la liste des Objets necessaire accessibles aux autres parties du jeu
     */
    public static Plateau plateau;
    public static InterfaceGraphique interfacegraphique;
    public static Joueur[] joueurs;


    public static void InstanceGame(){
        plateau = new Plateau((Integer)Configuration.Lis("Taille"),(Integer)Configuration.Lis("Joueurs"));

        joueurs = new Joueur[(Integer)Configuration.Lis("Joueurs")];
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i] = new Joueur();
        }

        interfacegraphique = new InterfaceGraphique();

        RedacteurNiveau.PrintNiveau(plateau);
    }

    public static void EndTurn(){
    }

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
