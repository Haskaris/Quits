import Global.Configuration;
import Global.Properties;
import Modele.LecteurRedacteur;
import Modele.Support.Plateau;
import Vue.MainInterface;
//import Vue.InterfaceGraphique;

import java.io.IOException;

public class Quits {
    //static InterfaceGraphique interfacegraphique;
    static Plateau plateau;

    /**
     * Permet d'initialiser ka partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        new MainInterface();
        Properties.Load();
        //interfacegraphique = new InterfaceGraphique();
        plateau = new Plateau((Integer) Configuration.Lis("Joueurs"),(Integer)Configuration.Lis("Taille"));
        plateau.JouePartie();
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
        plateau.JouePartie();
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
