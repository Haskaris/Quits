package Modele.Support;

import Controleur.Mediateur;
import Global.Configuration;
import Global.Tools;
import Global.Tools.GameMode;
import Modele.CalculateurCoup;
import Modele.Coup;
import Modele.Historique;
import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurHumain;
import Modele.Joueurs.JoueurIAFacile;
import Modele.LecteurRedacteur;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Plateau {

    private Tuile[][] grille;
    public int[][] casesSelectionnees;
    public Joueur[] joueurs;
    public int joueurcourant;
    public Historique historique;

    private Mediateur mediateur;

    private GameMode gameMode;

    private int maxPlayer = 0;

    private Object lock1 = new Object();

    /**
     * Initialise un plateau Taille FIXE
     */
    public Plateau() {
        /*joueurs = new Joueur[nbjoueur];
        for (int i = 0; i < nbjoueur; i++) {
            joueurs[i] = new JoueurIAFacile("Default",i);
        }*/

        joueurs = new Joueur[4];

        grille = new Tuile[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grille[i][j] = new Tuile(i, j);
            }
        }
        casesSelectionnees = new int[5][5];
        resetCasesSelectionnees();

        historique = new Historique(this);
        joueurcourant = 0;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setMediateur(Mediateur m) {
        this.mediateur = m;
    }

    /**
     * Initialise les joueurs (billes et position) en fonction du mode de jeu
     */
    public void initPlayers() {
        switch (gameMode) {
            case TwoPlayersFiveBalls:
                this.joueurs[0].setPointDeDepart(Tools.Dir.SO);
                this.joueurs[1].setPointDeDepart(Tools.Dir.NE);
                break;
            case TwoPlayersThreeBalls:
                this.joueurs[0].setPointDeDepart(Tools.Dir.SO);
                this.grille[0][3].addBille(this.joueurs[0].addBille());
                this.grille[1][3].addBille(this.joueurs[0].addBille());
                this.grille[1][4].addBille(this.joueurs[0].addBille());
                this.joueurs[1].setPointDeDepart(Tools.Dir.NE);
                this.grille[3][0].addBille(this.joueurs[1].addBille());
                this.grille[3][1].addBille(this.joueurs[1].addBille());
                this.grille[4][0].addBille(this.joueurs[1].addBille());
                break;
            case FourPlayersFiveBalls:
                this.joueurs[0].setPointDeDepart(Tools.Dir.SO);
                this.joueurs[1].setPointDeDepart(Tools.Dir.NE);
                this.joueurs[2].setPointDeDepart(Tools.Dir.NO);
                this.joueurs[3].setPointDeDepart(Tools.Dir.SE);
                break;
        }
    }

    /**
     * Joue les tours de la partie. S'arrete à la fin
     */
    public void JouePartie(Mediateur m) {
        //while(FinTour()){

        //LecteurRedacteur.AffichePartie(this);
        //}
    }

    public void playTurn(int ligne, int colonne) {
        if (JoueurCourant().getClass().equals(JoueurHumain.class)) {
            //Si c'est un joueur humain :
            resetCasesSelectionnees();
            List<Coup> coupspossibles = new CalculateurCoup(this, JoueurCourant()).coupsPossibles();
            for (Coup c : coupspossibles) {

                try {

                    c.Afficher();
                    //Afficher chaque case des coups avec une surbrillance
                    Point pos = c.getPosition();
                    if (pos.x == ligne && pos.y == colonne) {

                        int x = pos.x;
                        int y = pos.y;
                        Point dir = c.getDirection();
                        int dX = dir.x;
                        int dY = dir.y;
                        casesSelectionnees[x][y] = 1;
                        casesSelectionnees[x + dX][y + dY] = 2;
                    }
                } catch (Exception e) {
                }

            }
            afficherCasesSelectionnees();
            
            
            
            
        } else {
            //Sinon, c'est une IA 
            List<Coup> coupspossible = new CalculateurCoup(this, JoueurCourant()).coupsPossibles();
            Coup coup = JoueurCourant().Jouer(coupspossible);
            historique.Faire(coup);
            this.mediateur.graphicInterface.update();
            joueurSuivant();
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur
     * suivant
     */
    private boolean FinTour() {
        boolean estfini = JoueurCourant().billes.isEmpty();
        /*for (Bille bille:JoueurCourant().billes) {
            if(!bille.EstSortie())
                estfini = false;
        }*/

        if (estfini) {
            System.out.println("Joueur " + joueurcourant + " a gagné");
            return false;
        }

        joueurcourant++;
        if (joueurcourant >= (Integer) Configuration.Lis("Joueurs")) {
            joueurcourant = 0;
        }
        return true;
    }

    /**
     * Deplace la bille précisée, dans la direction précisé. Aucune vérification
     */
    public void DeplacerBille(Bille bille, Tools.Dir direction) {
        Point depart = bille.getTuile().getPosition();
        Point arrivee = new Point(depart.x + Tools.DirToPoint(direction).x, depart.y + Tools.DirToPoint(direction).y);
        grille[depart.x][depart.y].enleverBille();
        grille[arrivee.x][arrivee.y].addBille(bille);//MettreBille(bille,arrivee);
    }

    private void updatePosition() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grille[i][j].setPosition(i, j);
            }
        }
    }

    /**
     * Deplace la rangee précisée, dans le sens précisé. Aucune vérification
     */
    public void DeplacerRangee(Point rangee, boolean positif) {
        if (rangee.x == -1) {
            if (positif) {
                Tuile tmp = grille[grille.length - 1][rangee.y];
                for (int i = grille.length - 1; i > 0; i--) {
                    grille[i][rangee.y] = grille[i - 1][rangee.y];
                }
                grille[0][rangee.y] = tmp;
            } else {
                Tuile tmp = grille[0][rangee.y];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[i][rangee.y] = grille[i + 1][rangee.y];
                }
                grille[grille.length - 1][rangee.y] = tmp;
            }
        } else {
            if (positif) {
                Tuile tmp = grille[rangee.x][grille.length - 1];
                for (int i = grille.length - 1; i > 0; i--) {
                    grille[rangee.x][i] = grille[rangee.x][i - 1];
                }
                grille[rangee.x][0] = tmp;
            } else {
                Tuile tmp = grille[rangee.x][0];
                for (int i = 0; i < grille.length - 1; i++) {
                    grille[rangee.x][i] = grille[rangee.x][i + 1];
                }
                grille[rangee.x][grille.length - 1] = tmp;
            }
        }
        updatePosition();
    }

    /**
     * Place une bille aux coordonnées X Y
     *
     * @param b
     * @param x
     * @param y
     */
    public void placerBilleA(Bille b, int x, int y) {
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
    public Joueur JoueurCourant() {
        return getPlayer(joueurcourant);
    }

    public Tuile[][] GetGrille() {
        return grille;
    }

    public void addPlayer(Joueur player) {
        //Voué à changer
        this.joueurs[maxPlayer++] = player;
    }

    public Joueur getPlayer(int index) {
        return this.joueurs[index];
    }

    private void resetCasesSelectionnees() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                casesSelectionnees[i][j] = 0;
            }
        }
    }

    private void afficherCasesSelectionnees() {
        System.out.println("######################\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(casesSelectionnees[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("######################\n");
    }

    private void joueurSuivant() {
        joueurcourant++;
        if (joueurcourant >= (Integer) Configuration.Lis("Joueurs")) {
            joueurcourant = 0;
        }
    }

}
