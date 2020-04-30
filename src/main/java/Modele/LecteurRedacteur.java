package Modele;

import Global.Configuration;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurDistant;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIA;
import Modele.Support.Plateau;
import Modele.Support.Tuile;

import static java.lang.Character.forDigit;

import java.io.*;
import java.net.URL;

public class LecteurRedacteur {
    String filepath;
    Plateau plateau;
    Joueur[] joueurs = new Joueur[4];
    int joueurcourant;


    public LecteurRedacteur(String _filepath) {
        filepath = _filepath;
    }
    public LecteurRedacteur(String _filepath, Plateau _plateau,Joueur[] _joueurs, int _joueurcourant ) {
        filepath = _filepath;
        plateau = _plateau;
        joueurs = _joueurs;
        joueurcourant = _joueurcourant;
    }

    /**
     * Lis le contenu d'un plateau de jeu d'un fichier externe
     */
    public void LitPartie()throws IOException{
        //InputStream in_stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Sauvegardes/" + filepath);
        InputStream in_stream = new FileInputStream("Sauvegardes/" + filepath);

        //On recupère la taille et le nombre de joueurs
        String[] firstline = ReadLine(in_stream).split(" ");
        int taille = Integer.parseInt(firstline[0]);
        System.out.println("Taille = " + taille);
        int nbjoueur = Integer.parseInt(firstline[1]);
        System.out.println("NbJoueur = " + nbjoueur);

        //On ecrit le plateau
        plateau = new Plateau(0,taille);
        plateau.nbjoueur = nbjoueur;

        byte[] data = new byte[1];
        int i=0,j=0;
        while(i<taille) {
            in_stream.read(data);
            if(data[0] != '\n'){
                if (data[0] != '9')
                    plateau.PlacerBilleAt(i, j, Character.getNumericValue(data[0]));
                j++;
            }else {
                i++;
                j=0;
            }
        }

        for (int k = 0; k < nbjoueur; k++) {
            //Format : NOM COULEUR TYPE
            String[] metadonees = ReadLine(in_stream).split(" ");
            switch (metadonees[2]){
                case "HUMAIN":joueurs[k] = new JoueurHumain(metadonees[0],Integer.parseInt(metadonees[1]));
                case "DISTANT":joueurs[k] = new JoueurDistant(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA0":joueurs[k] = new JoueurIA(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA1":joueurs[k] = new JoueurIA(metadonees[0],Integer.parseInt(metadonees[1]));
                case "IA2":joueurs[k] = new JoueurIA(metadonees[0],Integer.parseInt(metadonees[1]));
            }
        }

        in_stream.close();
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
    public void EcrisPartie() throws IOException{
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
        stream.write((byte)IntToChar(plateau.nbjoueur));
        stream.write('\n');

        //Contenu du plateau
        Tuile[][] tab = plateau.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++)
                stream.write((byte)IntToChar(tab[i][j].CouleurBille()));
            stream.write('\n');
        }
        //Info sur les joueurs
        for (int i = 0; i < plateau.nbjoueur; i++) {
            stream.write(joueurs[i].nom.getBytes());
            stream.write(' ');
            stream.write(IntToChar(joueurs[i].couleur.byteValue()));
            stream.write(' ');
            if(joueurs[i] instanceof JoueurHumain)
                stream.write("HUMAIN".getBytes());
            if(joueurs[i] instanceof JoueurIA)
                stream.write("IA0".getBytes());
            if(joueurs[i] instanceof JoueurDistant)
                stream.write("DISTANT".getBytes());
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