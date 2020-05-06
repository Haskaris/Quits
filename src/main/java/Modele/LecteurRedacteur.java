package Modele;

import Global.Configuration;
import Modele.Joueurs.*;
import Modele.Support.Bille;
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
        String[] firstline = readLine(in_stream).split(" ");
        int taille = Integer.parseInt(firstline[0]);
        int nbjoueur = Integer.parseInt(firstline[1]);

        Plateau plateau = new Plateau();

        //On lit les infos sur les joueurs
        plateau.joueurs = new Joueur[nbjoueur];
        for (int k = 0; k < nbjoueur; k++) {
            //Format :
            //NOM COULEUR TYPE
            //Bille1X-Bille1Y/Bille2X-Bille2Y
            String[] metadonees = readLine(in_stream).split(" ");
            Joueur tmp = null;
            switch (metadonees[2]){
                case "HUMAIN":
                    tmp = new JoueurHumain(metadonees[0], new Color(Integer.parseInt(metadonees[1])));
                    break;
                case "DISTANT":
                    tmp = new JoueurDistant(metadonees[0], new Color(Integer.parseInt(metadonees[1])));
                    break;
                case "IA0":
                    tmp = new JoueurIAFacile(metadonees[0], new Color(Integer.parseInt(metadonees[1])));
                    break;
                case "IA1":
                    tmp = new JoueurIANormale(metadonees[0], new Color(Integer.parseInt(metadonees[1])));
                    break;
                case "IA2":
                    tmp = new JoueurIADifficile(metadonees[0], new Color(Integer.parseInt(metadonees[1])));
                    break;
            }
            metadonees = readLine(in_stream).split("/");
            for(String coord : metadonees) {
                String[] xy = coord.split("-");
                plateau.placerBilleA(tmp.addBille(), Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            }
            plateau.joueurs[k] = tmp;
        }


        //On lit le plateau
        /*byte[] data = new byte[1];
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
        }*/
        
        in_stream.close();
        return plateau;
    }

    private String readLine(InputStream stream) throws IOException {
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
     * @param plateau plateau à sauvegarder
     */
    public void EcrisPartie(Plateau plateau) throws IOException{
        String path = "Sauvegardes/" + filepath;
        File out = new File(path);
        OutputStream stream;
        try {
            out.getParentFile().mkdirs();
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(IOException e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie : " + path);
            return;
        }

        //On écrit la taille du tableau (pourquoi?)
        stream.write((byte)IntToChar(plateau.GetGrille().length));
        stream.write(' ');
        //On écrit le nombre de joueur
        stream.write((byte)IntToChar(plateau.joueurs.length));
        stream.write('\n');

        //Info sur les joueurs
        for (Joueur joueur : plateau.joueurs) {
            //On écrit le nom
            stream.write(joueur.nom.getBytes());
            stream.write(' ');
            //On écrit la couleur
            stream.write(joueur.couleur.getRGB());
            stream.write(' ');
            //On écrit le type
            if (joueur instanceof JoueurHumain) {
                stream.write("HUMAIN".getBytes());
            }
            if (joueur instanceof JoueurIAFacile) {
                stream.write("IA0".getBytes());
            }
            if (joueur instanceof JoueurIANormale) {
                stream.write("IA1".getBytes());
            }
            if (joueur instanceof JoueurIADifficile) {
                stream.write("IA2".getBytes());
            }
            if (joueur instanceof JoueurDistant) {
                stream.write("DISTANT".getBytes());
            }
            stream.write('\n');
            //On écrit ses billes
            for (Bille b : joueur.billes) {
                stream.write(b.getTuile().getPosition().x);
                stream.write('-');
                stream.write(b.getTuile().getPosition().y);
                stream.write('/');
            }
            stream.write('\n');
        }

    //TODO: Écrire l'indice des tuiles pour récupérer le même visuel
    //Contenu du plateau
    Tuile[][] tab = plateau.GetGrille();
    /*for(int i=0;i<tab.length;i++){
        for(int j=0;j<tab[0].length;j++)
            stream.write((byte)IntToChar(tab[i][j].CouleurBille()));
        stream.write('\n');
    }
    stream.write('\n');*/
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
    /*public static void AffichePartie(Plateau p){
        Tuile[][] tab = p.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++){
                if(tab[i][j].getCouleurBille() != 9)
                    System.out.print(tab[i][j].CouleurBille());
                else
                    System.out.print(".");
            }
            System.out.print('\n');
        }
        System.out.println("\n");
    }*/

}