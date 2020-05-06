package Modele;

import Global.Tools;
import Modele.Joueurs.Joueur;
import Modele.Support.Bille;
import Modele.Support.Plateau;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Global.Tools.*;
import Modele.Support.Tuile;


public class CalculateurCoup {
    /**
     * Crée la liste de coup possible pour un joueur
     */

    Plateau plateau;
    Joueur joueur;
    Dir joueurpos;
    List<Bille> billes;
    List<Coup> coups;
    Coup dernierCoup;

    public CalculateurCoup(Plateau _plateau, Joueur _joueur){
        plateau = _plateau;
        joueur = plateau.joueurCourant();
        billes = joueur.billes;
        coups = new ArrayList<>();
        joueurpos = joueur.getPointDeDepart();
        dernierCoup = plateau.historique.DernierCoup();
    }

    /**
     * Retourne une liste de coup possible
     * @return 
     */
    public List<Coup> coupsPossibles(){
        deplacementsBille();
        deplacementsTuile();
        return coups;
    }

    /**
     * Pas bon
     */
    private void deplacementsTuile() {
        ArrayList<Tuile> movableTiles = new ArrayList<>();
        
        //Pour chaque bille on ajoute sa ligne et sa colonne
        for(Bille ball: billes) {
            Tuile tmpTile = ball.getTuile();
            int tmpTileX = tmpTile.getPosition().x;
            int tmpTileY = tmpTile.getPosition().y;
            Tuile tileStudied = plateau.getGrille()[tmpTileX][0];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.contientBille()) {
                    movableTiles.add(tileStudied);
                    coups.add(new Coup(tileStudied.getPosition(), Dir.N, joueur));
                }
            }
            tileStudied = plateau.getGrille()[tmpTileX][4];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.contientBille()) {
                    movableTiles.add(tileStudied);
                    coups.add(new Coup(tileStudied.getPosition(), Dir.S, joueur));
                }
            }
            tileStudied = plateau.getGrille()[0][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.contientBille()) {
                    movableTiles.add(tileStudied);
                    coups.add(new Coup(tileStudied.getPosition(), Dir.O, joueur));
                }
            }
            tileStudied = plateau.getGrille()[4][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.contientBille()) {
                    movableTiles.add(tileStudied);
                    coups.add(new Coup(tileStudied.getPosition(), Dir.E, joueur));
                }
            }
        }
        
        /*for(Tuile tile: movableTiles) {
            if (!tile.contientBille() && !reverseCoup(dernierCoup, new Coup(tile.getPosition(), joueurpos, joueur)))
        }*/
        
        /*for (int i = 0; i < plateau.GetGrille().length ; i++) {
            if(TuileEstLibre(0,i) && !SontCoupInverse(dernierCoup, new Coup(new Point(-1,i),false,joueur)))
                coups.add(new Coup(new Point(-1,i),false,joueur));
            if(TuileEstLibre(plateau.GetGrille().length-1,i) && !SontCoupInverse(dernierCoup, new Coup(new Point(-1,i),true,joueur)))
                coups.add(new Coup(new Point(-1,i),true,joueur));
            if(TuileEstLibre(i,0)  && !SontCoupInverse(dernierCoup, new Coup(new Point(i,-1),false,joueur)))
                coups.add(new Coup(new Point(i,-1),false,joueur));
            if(TuileEstLibre(i, plateau.GetGrille().length-1)&& !SontCoupInverse(dernierCoup, new Coup(new Point(i,-1),true,joueur)))
                coups.add(new Coup(new Point(i,-1),true,joueur));
        }*/
    }

    private boolean SontCoupInverse(Coup c1, Coup c2){
        if (c1 == null || c2 == null || c1.rangee == null || c2.rangee == null)
            return false;
        return c1.rangee.equals(c2.rangee) && c1.positif != c2.positif;
    }

    private void deplacementsBille(){
        for (Bille b:billes) {
            Point pos = b.getTuile().getPosition();
            if(joueurpos != Dir.NO && tuileEstLibre(add(pos, DirToPoint(Dir.NO))))
                coups.add(new Coup(b,Dir.NO,joueur));
            if(joueurpos != Dir.NE && tuileEstLibre(add(pos,DirToPoint(Dir.NE))))
                coups.add(new Coup(b,Dir.NE,joueur));
            if(joueurpos != Dir.SE && tuileEstLibre(add(pos,DirToPoint(Dir.SE))))
                coups.add(new Coup(b,Dir.SE,joueur));
            if(joueurpos != Dir.SO && tuileEstLibre(add(pos,DirToPoint(Dir.SO))))
                coups.add(new Coup(b,Dir.SO,joueur));
        }
    }

    public boolean tuileEstLibre(Point coordonnee){
        if(coordonnee.x<0 || coordonnee.y < 0 || coordonnee.x > plateau.getGrille().length-1 || coordonnee.y > plateau.getGrille().length-1)
            return false;
        return !plateau.getGrille()[coordonnee.x][coordonnee.y].contientBille();
    }
    
    public boolean tuileEstLibre(int x,int y){
        if(x < 0 || y < 0 || x > plateau.getGrille().length-1 || y > plateau.getGrille().length-1)
            return false;
        return !plateau.getGrille()[x][y].contientBille();
    }
    
    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

}
