package Modele;

import Global.Configuration;
import Modele.Support.Plateau;
import Modele.Support.Tuile;

import java.io.*;

public class LecteurRedacteur {
    String filepath;

    public LecteurRedacteur(String _filepath) {
        filepath = _filepath;
    }

    /**
     * Lis le contenu d'un plateau de jeu d'un fichier externe
     */
    public Plateau LisNiveau()throws IOException{
        InputStream in_stream = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);

        //On recupère la taille
        int taille = 0;
        byte data[] = new byte[1];
        in_stream.read(data);
        if(data[0]==0)return null;
        while(data[0] != 0 && data[0] != '\n') {
            taille ++;
            in_stream.read(data);
        }
        in_stream.close();

        //On ecrit le plateau
        in_stream = ClassLoader.getSystemClassLoader().getResourceAsStream(filepath);
        Plateau p = new Plateau(0,taille);
        int i=0,j=0;
        while(data[0] != 0) {
            if(data[0] != '\n'){
                j++;
                i=0;
            }else {
                if (data[0] != '0')
                    p.PlacerBilleAt(i, j, Character.getNumericValue(data[0]));
                i++;
            }
            in_stream.read(data);
        }
        return p;
    }


    /**
     * Ecris le contenu d'un plateau de jeu dans un fichier externe
     */
    public void EcrisNiveau(Plateau p) throws IOException{
        File out = new File(filepath);
        OutputStream stream;
        try {
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(Exception e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie " + filepath);
            return;
        }
        Tuile[][] tab = p.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++)
                stream.write((byte)tab[i][j].CouleurBille());
            stream.write('\n');
        }
    stream.write('\n');
    stream.flush();
    stream.close();
 }


    /**
     * Permet d'afficher l'etat du jeu dans la sortie standard.
     * Les 0 sont des cases vides, les chiffres sont les billes des joueurs
     */
    public static void PrintNiveau(Plateau p){
        Tuile[][] tab = p.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++)
                System.out.print(tab[i][j].CouleurBille());
            System.out.print('\n');
        }
        System.out.println("\n");
    }

}