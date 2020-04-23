package Modele;

import Global.Configuration;

import java.awt.*;

public class Plateau {
    private Tuile[][] grille;


    public Plateau(int nbjoueur, int taille){
        grille = new Tuile [taille][taille];
        if(nbjoueur == 2 )
            Init2Players();
        else
            Init4Players();
    }

    public Tuile[][]GetGrille(){
        return grille;
    }



    private void Init2Players(){
        int l =  grille.length;
        boolean pair = l%2==0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                grille[i][j]=new Tuile();
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1)))
                    grille[i][j].MettreBille(new Bille(1,new Point(i,j)));
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1)
                    grille[i][j].MettreBille(new Bille(2,new Point(i,j)));
            }
        }
    }

    private void Init4Players(){
        int l =  grille.length;
        for (int i = 0; i < l; i++)
            for (int j = 0; j < l; j++)
                grille[i][j]=new Tuile();

        for (int i = 0; i < l; i++) {
            if(i<l/2) {
                grille[0][i].MettreBille(new Bille(1,new Point(0,i)));
                grille[i][0].MettreBille(new Bille(1,new Point(i,0)));
                grille[l-1][i].MettreBille(new Bille(4,new Point(l-1,i)));
                grille[i][l-1].MettreBille(new Bille(2,new Point(i,l-1)));
            }
            if(i>l/2) {
                grille[0][i].MettreBille(new Bille(2,new Point(0,i)));
                grille[i][0].MettreBille(new Bille(4,new Point(i,0)));
                grille[l-1][i].MettreBille(new Bille(3,new Point(l-1,i)));
                grille[i][l-1].MettreBille(new Bille(3,new Point(i,l-1)));
            }
        }
    }
}
