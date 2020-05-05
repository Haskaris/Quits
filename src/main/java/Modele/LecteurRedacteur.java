package Modele;

import Global.Configuration;
import Modele.Joueurs.*;
import Modele.Support.Plateau;
import Modele.Support.Tuile;
import java.awt.Color;

import java.io.*;

public class LecteurRedacteur {
    String filepath;


    public LecteurRedacteur(String _filepath) {
        filepath = _filepath;
    }

    /**
     * Lis le contenu d'un plateau de jeu d'un fichier externe
     */
    public Plateau LitPartie()throws IOException{
        //InputStream in_stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Sauvegardes/" + filepath);
        InputStream in_stream = new FileInputStream("Sauvegardes/" + filepath);

        //On lit la taille et le nombre de joueurs
        String[] firstline = ReadLine(in_stream).split(" ");
        int taille = Integer.parseInt(firstline[0]);
        int nbjoueur = Integer.parseInt(firstline[1]);

        Plateau plateau = new Plateau();

        //On lit les infos sur les joueurs
        plateau.joueurs = new Joueur[nbjoueur];
        for (int k = 0; k < nbjoueur; k++) {
            //Format : NOM COULEUR TYPE
            String[] metadonees = ReadLine(in_stream).split(" ");
            switch (metadonees[2]){
                case "HUMAIN":plateau.joueurs[k] = new JoueurHumain(metadonees[0],Integer.parseInt(metadonees[1]));
                case "DISTANT":plateau.joueurs[k] = new JoueurDistant(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA0":plateau.joueurs[k] = new JoueurIAFacile(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA1":plateau.joueurs[k] = new JoueurIANormale(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA2":plateau.joueurs[k] = new JoueurIADifficile(metadonees[0],Integer.parseInt(metadonees[1]));
            }
        }


        //On lit le plateau
        byte[] data = new byte[1];
        int i=0,j=0;
        while(i<taille) {
            in_stream.read(data);
            if(data[0] != '\n'){
                if (data[0] != '9'){
                    plateau.PlacerNouvelleBilleA(i, j, Character.getNumericValue(data[0]));
                }
                j++;
            }else {
                i++;
                j=0;
            }
        }
        
        in_stream.close();
        return plateau;
    }

    String ReadLine(InputStream stream) throws IOException {
        String S = "";
        byte[] data = new byte [1];
        stream.read(data);
        while(data[0] != '\n' && data[0] != '\r' && data[0] != 0){
            S += (char)data[0];
            stream.read(data);
        }
        return S;
    }


    /**
     * Ecris le contenu d'un plateau de jeu dans un fichier externe
     */
    public void EcrisPartie(Plateau plateau) throws IOException{
        File out = new File("Sauvegardes/" +filepath);
        OutputStream stream;
        try {
            out.getParentFile().mkdirs();
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(Exception e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie : " + "Sauvegardes/" + filepath);
            return;
        }

        //Taille et nombre de joueur
        stream.write((byte)IntToChar(plateau.GetGrille().length));
        stream.write(' ');
        stream.write((byte)IntToChar(plateau.joueurs.length));
        stream.write('\n');

        //Info sur les joueurs
        for (int i = 0; i < plateau.joueurs.length; i++) {
            stream.write(plateau.joueurs[i].nom.getBytes());
            stream.write(' ');
            stream.write(IntToChar(plateau.joueurs[i].couleur.byteValue()));
            stream.write(' ');
            if(plateau.joueurs[i] instanceof JoueurHumain)
                stream.write("HUMAIN".getBytes());
            if(plateau.joueurs[i] instanceof JoueurIAFacile)
                stream.write("IA0".getBytes());
            if(plateau.joueurs[i] instanceof JoueurIANormale)
                stream.write("IA1".getBytes());
            if(plateau.joueurs[i] instanceof JoueurIADifficile)
                stream.write("IA2".getBytes());
            if(plateau.joueurs[i] instanceof JoueurDistant)
                stream.write("DISTANT".getBytes());
            stream.write('\n');
        }

    //Contenu du plateau
    Tuile[][] tab = plateau.GetGrille();
    for(int i=0;i<tab.length;i++){
        for(int j=0;j<tab[0].length;j++)
            stream.write((byte)IntToChar(tab[i][j].CouleurBille()));
        stream.write('\n');
    }
    stream.write('\n');
    stream.flush();
    stream.close();
 }

 private char IntToChar(int a){
     return (char)(a+48);
 }

    /**
     * Permet d'afficher l'etat du jeu dans la sortie standard.
     * Les 0 sont des cases vides, les chiffres sont les billes des joueurs
     */
    public static void AffichePartie(Plateau p){
        Tuile[][] tab = p.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++){
                if(tab[i][j].CouleurBille() != 9)
                    System.out.print(tab[i][j].CouleurBille());
                else
                    System.out.print(".");
            }
            System.out.print('\n');
        }
        System.out.println("\n");
    }

}