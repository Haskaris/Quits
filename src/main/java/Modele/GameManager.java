package Modele;

import Global.Configuration;
import Global.Properties;

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
    public static int[][] map;
    public static Object interfacegraphique;
    public static Object[] playercontroller;


    public static void InstanceGame(){
        map = new int [(Integer)Configuration.Lis("Taille")][(Integer)Configuration.Lis("Taille")];

        int nbjoueur = (Integer)Configuration.Lis("Joueurs");
        if(nbjoueur == 2 )
            Init2Players();
        else
            Init4Players();

        playercontroller = new Object[nbjoueur];
        for (int i = 0; i < nbjoueur; i++) {
            playercontroller[i] = new Object();
        }

        interfacegraphique = new Object();

        RedacteurNiveau.PrintNiveau(map);
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



    public static void Init2Players(){
        int l =  map.length;
        boolean pair = l%2==0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1)))
                    map[i][j] = 1;
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1)
                    map[i][j] = 2;
            }
        }
    }

    public static void Init4Players(){
        int l =  map.length;
        for (int i = 0; i < l; i++) {
            if(i<l/2) {
                map[0][i] = 1;
                map[i][0] = 1;
                map[l-1][i]=4;
                map[i][l-1]=2;
            }
            if(i>l/2) {
                map[0][i] = 2;
                map[i][0] = 4;
                map[l-1][i]=3;
                map[i][l-1]=3;
            }
        }
    }
}
