package Modele;

import Global.Configuration;

public class Plateau {
    private int[][] grille;


    public Plateau(int nbjoueur, int taille){
        grille = new int [taille][taille];
        if(nbjoueur == 2 )
            Init2Players();
        else
            Init4Players();
    }

    public int[][]GetGrille(){
        return grille;
    }



    private void Init2Players(){
        int l =  grille.length;
        boolean pair = l%2==0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1)))
                    grille[i][j] = 1;
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1)
                    grille[i][j] = 2;
            }
        }
    }

    private void Init4Players(){
        int l =  grille.length;
        for (int i = 0; i < l; i++) {
            if(i<l/2) {
                grille[0][i] = 1;
                grille[i][0] = 1;
                grille[l-1][i]=4;
                grille[i][l-1]=2;
            }
            if(i>l/2) {
                grille[0][i] = 2;
                grille[i][0] = 4;
                grille[l-1][i]=3;
                grille[i][l-1]=3;
            }
        }
    }
}
