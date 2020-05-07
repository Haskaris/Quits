import Global.Configuration;
import Global.Properties;
import Model.ReaderWriter;
import Model.Support.Board;
import View.MainGraphicInterface;
//import Vue.InterfaceGraphique;

import java.io.IOException;

public class Quits {
    //static InterfaceGraphique interfacegraphique;
    static Board plateau;

    /**
     * Permet d'initialiser ka partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        Properties.load();
        new MainGraphicInterface();
        //interfacegraphique = new InterfaceGraphique();
        //plateau = new Board();
        //plateau.playGame();
    }

    /**
     * Charge une partie
     */
    public static void ChargerPartie(){
        try {
            plateau = new ReaderWriter("default.save").readGame();
        }
        catch (Exception e){
            System.out.println("Erreur de chargement de la partie");
        }
        //plateau.playGame();
    }

    /**
     * Enregistre une partie
     */
    public static void EnregistrerPartie(){
        try {
            new ReaderWriter("default.save").writeGame(plateau);
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
            Properties.store();
        } catch (IOException e) {
            Configuration.logger().severe("Erreur d'ecriture des Properties");
            System.exit(1);
        }
        System.exit(0);
    }
}
