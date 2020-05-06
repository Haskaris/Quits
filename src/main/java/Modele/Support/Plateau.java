package Modele.Support;

import Global.Configuration;
import Global.Tools;
import Modele.CalculateurCoup;
import Modele.Coup;
import Modele.Historique;
import Modele.Joueurs.Joueur;

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
        joueurs = new Joueur[4];

        grille = new Tuile[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grille[i][j] = new Tuile(i, j);

        historique = new Historique(this);
        joueurcourant = 0;
    }

    /**
     *  Joue les tours de la partie. S'arrete à la fin
     */
    public void JouePartie(){
        while(finTour()){
            List<Coup> coupspossible = new CalculateurCoup(this, joueurCourant()).coupsPossibles();
            Coup coup = joueurCourant().Jouer(coupspossible);
            historique.Faire(coup);
            //LecteurRedacteur.AffichePartie(this);
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private boolean finTour(){
        boolean estfini = joueurCourant().billes.isEmpty();

        if(estfini){
            System.out.println("Joueur " + joueurcourant + " a gagné");
            return false;
        }

        joueurcourant ++;
        if(joueurcourant>=(Integer)Configuration.lis("Joueurs"))
            joueurcourant =0;
        return true;
    }

    /**
     * Deplace la bille précisée, dans la direction précisé.Aucune vérification
     * @param bille
     * @param direction
     */
    public void deplacerBille(Bille bille, Tools.Dir direction){
        Point depart = bille.getTuile().getPosition();
        Point arrivee = Tools.getNextPoint(depart, direction);
        grille[depart.x][depart.y].enleverBille();
        grille[arrivee.x][arrivee.y].addBille(bille);
    }
    
    /**
     * DEPRECATED
     * Mets à jours les positions des tuiles
     */
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
    public void deplacerRangee(Point rangee, Tools.Dir direction){
        Tuile tmp = null;
        switch(direction) {
            case N:
                //Factoriser
                tmp = grille[0][rangee.y];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[i][rangee.y] = grille[i+1][rangee.y] ;
                }
                grille[grille.length-1][rangee.y] = tmp;
                break;
            case E:
                tmp = grille[rangee.x][grille.length - 1];
                for (int i = grille.length-1; i>0; i--) {
                    grille[rangee.x][i] = grille[rangee.x][i-1];
                }
                grille[rangee.x][0] = tmp;
                break;
            case S:
                tmp = grille[grille.length-1][rangee.y];
                for (int i = grille.length-1; i>0; i--) {
                    grille[i][rangee.y] = grille[i-1][rangee.y];
                }
                grille[0][rangee.y] = tmp;
                break;
            case O:
                tmp = grille[rangee.x][0];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[rangee.x][i] = grille[rangee.x][i+1] ;
                }
                grille[rangee.x][grille.length - 1] = tmp;
                break;
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

    public Joueur joueurCourant(){
        return getPlayer(joueurcourant);
    }

    public Tuile[][]getGrille(){
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
