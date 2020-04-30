package Modele.Support;

import Global.Tools;

import java.awt.*;

public class Plateau {
    public int nbjoueur;
    private Tuile[][] grille;
    private Bille[][] billes;

    /**
     * Initialise un plateau avec le nombre de joueur et la taille précisé.
     * Pour fonctionner pleinement avec le GameManager, ces deux chiffres doivent etre ceux de Configuration
     */
    public Plateau(int _nbjoueur, int taille){
        nbjoueur = _nbjoueur;
        grille = new Tuile [taille][taille];
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++)
                grille[i][j]=new Tuile();

        billes = new Bille[4][];
        if(nbjoueur == 2 )
            Init2Players();
        if(nbjoueur == 4)
            Init4Players();
    }

    /**
     * Deplace la bille précisée, dans la direction précisé. Aucune vérification
     */
    public void DeplacerBille(Bille bille, Tools.Dir direction){
        Point depart = bille.PositionGet();
        Point arrivee = new Point(depart.x + Tools.DirToPoint(direction).x,depart.y + Tools.DirToPoint(direction).y);
        grille[arrivee.x][arrivee.y].MettreBille(grille[depart.x][depart.y].EnleverBille(),arrivee);
    }

    /**
     * Deplace la rangee précisée, dans le sens précisé. Aucune vérification
     */
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

    /**
     * Place une nouvelle bille de la couleur précisé, aux coordonnées précisées
     */
    public void PlacerBilleAt(int x, int y,int couleur){
        grille[x][y].MettreBille(new Bille(couleur),new Point(x,y));
    }
    /**
     * Place une bille donnée, aux coordonnées précisées
     */
    public void PlacerBilleAt(int x, int y,Bille bille){
        grille[x][y].MettreBille(bille,new Point(x,y));
    }

    /**
     * Initialise le plateau avec les règles 2 joueurs classiques
     */
    private void Init2Players(){
        int l =  grille.length;
        boolean pair = l%2==0;

        for (int i = 0; i < billes.length; i++) {
            if(pair)
                billes[i]=new Bille[grille.length-1];
            else
                billes[i]=new Bille[grille.length];
            for (int j = 0; j < billes[i].length; j++) {
                billes[i][j] = new Bille(i);
            }
        }

        int pos0 = 0, pos1 = 0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1))) {
                    PlacerBilleAt(i, j, billes[0][pos0]);
                    pos0++;
                }
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1){
                    PlacerBilleAt(i,j,billes[1][pos1]);
                    pos1++;
                }
            }
        }
    }


    /**
     * Initialise le plateau avec les règles 4 joueurs classiques
     */
    private void Init4Players(){
        int l =  grille.length;
        for (int i = 0; i < billes.length; i++) {
            billes[i]=new Bille[grille.length/2*2-1];
            for (int j = 0; j < billes[i].length; j++) {
                billes[i][j] = new Bille(i);
            }
        }

        int pos0 = 0, pos1 = 0, pos2 = 0,pos3 = 0;
        for (int i = 0; i < l; i++) {

            if(i<l/2) {
                PlacerBilleAt(0,i,billes[0][pos0]);
                pos0++;
                if(i!=0){PlacerBilleAt(i,0,billes[0][pos0]);
                pos0++;}
                if(i!=0){PlacerBilleAt(l-1,1,billes[3][pos3]);
                pos3++;}
                PlacerBilleAt(i,l-1,billes[1][pos1]);
                pos1++;
            }
            if(i>l/2) {
                PlacerBilleAt(i,0,billes[3][pos3]);
                pos3++;
                if(i!=l-1){PlacerBilleAt(0,i,billes[1][pos1]);
                pos1++;}
                PlacerBilleAt(l-1,i,billes[2][pos2]);
                pos2++;
                if(i!=l-1){PlacerBilleAt(i,l-1,billes[2][pos2]);
                pos2++;}
            }
        }
    }

    public Bille[] BillesJoueur(int couleur){
        return billes[couleur];
    }

    public Tuile[][]GetGrille(){
        return grille;
    }


}
