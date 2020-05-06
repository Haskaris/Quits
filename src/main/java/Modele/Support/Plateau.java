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
    
    private int maxPlayer = 0;

    /**
     * Initialise un plateau
     * Taille FIXE
     */
    public Plateau(){
        /*joueurs = new Joueur[nbjoueur];
        for (int i = 0; i < nbjoueur; i++) {
            joueurs[i] = new JoueurIAFacile("Default",i);
        }*/
        
        joueurs = new Joueur[4];

        grille = new Tuile[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grille[i][j] = new Tuile(i, j);

        /*if(nbjoueur == 2 )
            Init2Players();
        if(nbjoueur == 4)
            Init4Players();*/

        historique = new Historique(this);
        joueurcourant = 0;
    }

    /**
     *  Joue les tours de la partie. S'arrete à la fin
     */
    public void JouePartie(){
        while(FinTour()){
            List<Coup> coupspossible = new CalculateurCoup(this,JoueurCourant()).coupsPossibles();
            Coup coup = JoueurCourant().Jouer(coupspossible);
            historique.Faire(coup);
            //LecteurRedacteur.AffichePartie(this);
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private boolean FinTour(){
        boolean estfini = JoueurCourant().billes.isEmpty();
        /*for (Bille bille:JoueurCourant().billes) {
            if(!bille.EstSortie())
                estfini = false;
        }*/

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
        Point depart = bille.getTuile().getPosition();
        Point arrivee = new Point(depart.x + Tools.DirToPoint(direction).x,depart.y + Tools.DirToPoint(direction).y);
        grille[depart.x][depart.y].enleverBille();
        grille[arrivee.x][arrivee.y].addBille(bille);//MettreBille(bille,arrivee);
    }
    
    private void updatePosition() {
        for(int i = 0; i <  5; i++) {
            for(int j = 0; j < 5; j++) {
                grille[i][j].setPosition(i, j);
            }
        }
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
        updatePosition();
    }

    /**
     * Place une bille aux coordonnées X Y
     * @param b
     * @param x
     * @param y
     */
    public void placerBilleA(Bille b, int x, int y){
        grille[x][y].addBille(b);
    }

    /**
     * Initialise le plateau avec les règles 2 joueurs classiques
     */
   /* private void Init2Players(){
        int l =  grille.length;
        boolean pair = l%2==0;

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if((pair && (j == l/2 - i - 1 || j == l/2 - i - 2)) || (!pair && (j == l/2 - i || j == l/2 - i - 1))) {
                    PlacerNouvelleBilleA(i, j, 0);
                }
                if(j == 3*l/2 - i || j == 3*l/2 - i - 1){
                    PlacerNouvelleBilleA(i, j, 1);
                }
            }
        }
    }*/


    /**
     * Initialise le plateau avec les règles 4 joueurs classiques
     */
    /*private void Init4Players(){
        int l =  grille.length;

        for (int i = 0; i < l; i++) {

            if(i<l/2) {
                PlacerNouvelleBilleA(0,i,0);

                if(i!=0)
                    PlacerNouvelleBilleA(i,0,0);

                if(i!=0)
                    PlacerNouvelleBilleA(l-1,1,3);

                PlacerNouvelleBilleA(i,l-1,1);
            }

            if(i>l/2) {
                PlacerNouvelleBilleA(i,0,3);

                if(i!=l-1)
                    PlacerNouvelleBilleA(0,i,1);

                PlacerNouvelleBilleA(l-1,i,2);

                if(i!=l-1)
                    PlacerNouvelleBilleA(i,l-1,2);

            }
        }
    }*/

    public Joueur JoueurCourant(){
        return getPlayer(joueurcourant);
    }

    public Tuile[][]GetGrille(){
        return grille;
    }

    public void addPlayer(Joueur player) {
        //Voué à changer
        this.joueurs[maxPlayer++] = player;
    }

    public Joueur getPlayer(int index) {
        return this.joueurs[index];
    }


}
