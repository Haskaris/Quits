/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Global.Configuration;
import Global.Properties;
import Model.ReaderWriter;
import Model.Support.Board;
import java.io.IOException;

/**
 * Classe de gestion de fichier
 */
public class FileGestion {
    private final Mediator mediator;
    
    public FileGestion(Mediator mediator) {
        this.mediator = mediator;
    }
    
    /**
     * Charge une partie
     * @return 
     */
    public Board loadGame(String fileName){
        try {
            return new ReaderWriter(fileName).readGame();
        } catch (IOException e){
            System.out.println("Erreur de chargement de la partie");
        }
        return null;
    }
    
    /**
     * Enregistre une partie
     */
    public void saveGame(String fileName) {
        try {
            new ReaderWriter(fileName).writeGame(this.mediator.getBoard());
        } catch (IOException e){
            System.out.println("Erreur d'enregistrement de la partie");
        }
    }
    
    /**
     * Fermeture du jeu
     */
    public void quitGame(){
        try {
            Properties.store();
        } catch (IOException e) {
            Configuration.logger().severe("Erreur d'ecriture des Properties");
            System.exit(1);
        }
        System.exit(0);
    }
}
