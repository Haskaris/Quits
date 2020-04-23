package Modele;

import Global.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RedacteurNiveau {
        OutputStream stream;
    public RedacteurNiveau(String filepath) {
        File out = new File(filepath);
        try {
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(Exception e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie " + filepath);
        }

    }

    public void ecrisNiveau(int[][] tab) throws IOException{
    for(int i=0;i<tab.length;i++){
        for(int j=0;j<tab[0].length;j++)
            if(tab[i][j]!=0)stream.write((byte)tab[i][j]);
        stream.write('\n');
    }
    stream.write('\n');
    stream.flush();
    stream.close();
 }


    public static void PrintNiveau(int[][] tab){
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++)
                System.out.print(tab[i][j]);
            System.out.print('\n');
        }
        System.out.println("\n");
    }

}