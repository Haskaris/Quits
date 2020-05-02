package Modele.Support;

import Global.Configuration;
import Global.Tools;
import Modele.CalculateurCoup;
import Modele.Coup;
import Modele.Historique;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurIAFacile;
import Modele.LecteurRedacteur;

import java.awt.*;
import java.util.List;

public class Plateau {
    private Tuile[][] grille;
    public Joueur[] joueurs;
    public int joueurcourant;
    public Historique historique;

    /**
     * Initialise un plateau avec le nombre de joueur et la taille précisé.
     */
    public Plateau(int nbjoueur, int taille){
        joueurs = new Joueur[nbjoueur];
        for (int i = 0; i < nbjoueur; i++) {
            joueurs[i] = new JoueurIAFacile("Default",i);
        }

        grille = new Tuile [taille][taille];
        for (int i = 0; i < taille; i++)
            for (int j = 0; j < taille; j++)
                grille[i][j]=new Tuile();

        if(nbjoueur == 2 )
            Init2Players(joueurs);
        if(nbjoueur == 4)
            Init4Players(joueurs);

        historique = new Historique(this);
        joueurcourant = 0;
    }

    /**
     *  Joue les tours de la partie. S'arrete à la fin
     */
    public void JouePartie(){
        while(FinTour()){
            List<Coup> coupspossible = new CalculateurCoup(this,JoueurCourant()).CoupsPossible();
            Coup coup = JoueurCourant().Jouer(coupspossible);
            historique.Faire(coup);
            LecteurRedacteur.AffichePartie(this);
        }

    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private boolean FinTour(){
        boolean estfini = true;
        for (Bille bille:JoueurCourant().billes) {
            if(!bille.EstSortie())
                estfini = false;
        }

        if(estfini){
            System.out.println("Joueur " + joueurcourant + " a gagné");
            return false;
        }

        joueurcourant ++;
        if(joueurcourant>=(Integer)Configuration.Lis("Joueurs"))
            joueurcourant =0;
        return true;
    }



    /**
     * Deplace la bille précisée, dans la direction précisé. Aucune vérification
     */
    public void DeplacerBille(Bille bille, Tools.Dir direction){
        Point depart = bille.PositionGet();
        Point arrivee = new Point(depart.x + Tools.DirToPoint(direction).x,depart.y + Tools.DirToPoint(direction).y);
        grille[depart.x][depart.y].EnleverBille();
        grille[arrivee.x][arrivee.y].MettreBille(bille,arrivee);
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
    private void Init2Players(Joueur[] joueurs){
        int l =  grille.length;
        boolean pair = l%2==0;

        for (int i = 0; i < joueurs.length; i++) {
            if(pair)
                joueurs[i].billes = new Bille[grille.length-1];
            else
                joueurs[i].billes = new Bille[grille.length];
            for (int j = 0; j < joueurs[i].billes.length; j++) {
                joueurs[i].billes[j] = new Bille(i);
            }
        }

        int pos0 = 0, pos1 = 0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1))) {
                    PlacerBilleAt(i, j, joueurs[0].billes[pos0]);
                    pos0++;
                }
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1){
                    PlacerBilleAt(i,j,joueurs[1].billes[pos1]);
                    pos1++;
                }
            }
        }
    }


    /**
     * Initialise le plateau avec les règles 4 joueurs classiques
     */
    private void Init4Players(Joueur[] joueurs){
        int l =  grille.length;
        for (int i = 0; i < joueurs.length; i++) {
            joueurs[i].billes=new Bille[grille.length/2*2-1];
            for (int j = 0; j < joueurs[i].billes.length; j++) {
                joueurs[i].billes[j] = new Bille(i);
            }
        }

        int pos0 = 0, pos1 = 0, pos2 = 0,pos3 = 0;
        for (int i = 0; i < l; i++) {

            if(i<l/2) {
                PlacerBilleAt(0,i,joueurs[0].billes[pos0]);
                pos0++;
                if(i!=0){PlacerBilleAt(i,0,joueurs[0].billes[pos0]);
                pos0++;}
                if(i!=0){PlacerBilleAt(l-1,1,joueurs[3].billes[pos3]);
                pos3++;}
                PlacerBilleAt(i,l-1,joueurs[1].billes[pos1]);
                pos1++;
            }
            if(i>l/2) {
                PlacerBilleAt(i,0,joueurs[3].billes[pos3]);
                pos3++;
                if(i!=l-1){PlacerBilleAt(0,i,joueurs[1].billes[pos1]);
                pos1++;}
                PlacerBilleAt(l-1,i,joueurs[2].billes[pos2]);
                pos2++;
                if(i!=l-1){PlacerBilleAt(i,l-1,joueurs[2].billes[pos2]);
                pos2++;}
            }
        }
    }

    public Joueur JoueurCourant(){
        return joueurs[joueurcourant];
    }

    public Tuile[][]GetGrille(){
        return grille;
    }


}
