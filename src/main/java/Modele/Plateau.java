package Modele;

import Global.Configuration;
import Global.Tools;

import java.awt.*;

public class Plateau {
    private Tuile[][] grille;

    public Plateau(int nbjoueur, int taille){
        grille = new Tuile [taille][taille];
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++)
                grille[i][j]=new Tuile();

        if(nbjoueur == 2 )
            Init2Players();
        if(nbjoueur == 4)
            Init4Players();
    }


    public void DeplacerBille(Bille bille, Tools.Dir direction){
        Point depart = bille.PositionGet();
        Point arrivee = new Point(depart.x + Tools.DirToPoint(direction).x,depart.y + Tools.DirToPoint(direction).y);
        grille[arrivee.x][arrivee.y].MettreBille(grille[depart.x][depart.y].EnleverBille(),arrivee);
    }

    public void DeplacerRangee(Point rangee, boolean positif){
        if(rangee.x == -1){
            if(positif) {
                Tuile tmp = grille[grille.length-1][rangee.y];
                for (int i = grille.length-1; i>0; i--) {
                    grille[i][rangee.y] = grille[i-1][rangee.y];
                }
                grille[0][rangee.y] = tmp;
            }
            else{
                Tuile tmp = grille[0][rangee.y];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[i][rangee.y] = grille[i+1][rangee.y] ;
                }
                grille[grille.length-1][rangee.y] = tmp;
            }
        }
        else{
            if(positif) {
                Tuile tmp = grille[rangee.x][grille.length - 1];
                for (int i = grille.length-1; i>0; i--) {
                    grille[rangee.x][i] = grille[rangee.x][i-1];
                }
                grille[rangee.x][0] = tmp;
            }
            else{
                Tuile tmp = grille[rangee.x][0];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[rangee.x][i] = grille[rangee.x][i+1] ;
                }
                grille[rangee.x][grille.length - 1] = tmp;
            }
        }
    }

    public void PlacerBilleAt(int x, int y,int couleur){
        grille[x][y].MettreBille(new Bille(couleur),new Point(x,y));
    }
    public void PlacerBilleAt(int x, int y,Bille bille){
        grille[x][y].MettreBille(bille,new Point(x,y));
    }

    private void Init2Players(){
        int l =  grille.length;
        boolean pair = l%2==0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1)))
                    grille[i][j].MettreBille(new Bille(1),new Point(i,j));
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1)
                    grille[i][j].MettreBille(new Bille(2),new Point(i,j));
            }
        }
    }

    private void Init4Players(){
        int l =  grille.length;

        for (int i = 0; i < l; i++) {
            if(i<l/2) {
                grille[0][i].MettreBille(new Bille(1),new Point(0,i));
                grille[i][0].MettreBille(new Bille(1),new Point(i,0));
                grille[l-1][i].MettreBille(new Bille(4),new Point(l-1,i));
                grille[i][l-1].MettreBille(new Bille(2),new Point(i,l-1));
            }
            if(i>l/2) {
                grille[0][i].MettreBille(new Bille(2),new Point(0,i));
                grille[i][0].MettreBille(new Bille(4),new Point(i,0));
                grille[l-1][i].MettreBille(new Bille(3),new Point(l-1,i));
                grille[i][l-1].MettreBille(new Bille(3),new Point(i,l-1));
            }
        }
    }

    public Tuile[][]GetGrille(){
        return grille;
    }
}
