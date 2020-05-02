package Modele;

import Global.Tools;
import Modele.Joueurs.Joueur;
import Modele.Support.Bille;
import Modele.Support.Plateau;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Global.Tools.DirToPoint;
import static Global.Tools.Dir;
import static Global.Tools.Dir.NO;
import static Global.Tools.Dir.NE;
import static Global.Tools.Dir.SE;
import static Global.Tools.Dir.SO;


public class CalculateurCoup {
    /**
     * Crée la liste de coup possible pour un joueur
     */

    Plateau plateau;
    Joueur joueur;
    Dir joueurpos;
    List<Bille> billes;
    List<Coup> coups;
    Coup derniercoup;

    public CalculateurCoup(Plateau _plateau,Joueur _joueur){
        plateau = _plateau;
        joueur = plateau.JoueurCourant();
        billes = joueur.billes;
        coups = new ArrayList<>();
        joueurpos = Tools.IntToDir(joueur.couleur);
        derniercoup = plateau.historique.DernierCoup();
    }

    public List<Coup> CoupsPossible(){
        DeplacementsBille();
        DeplacementsTuile();
        return coups;
    }


    private void DeplacementsTuile() {
        for (int i = 0; i < plateau.GetGrille().length ; i++) {
            if(TuileEstLibre(0,i) && !SontCoupInverse(derniercoup,new Coup(new Point(-1,i),false,joueur)))
                coups.add(new Coup(new Point(-1,i),false,joueur));
            if(TuileEstLibre(plateau.GetGrille().length-1,i) && !SontCoupInverse(derniercoup,new Coup(new Point(-1,i),true,joueur)))
                coups.add(new Coup(new Point(-1,i),true,joueur));
            if(TuileEstLibre(i,0)  && !SontCoupInverse(derniercoup,new Coup(new Point(i,-1),false,joueur)))
                coups.add(new Coup(new Point(i,-1),false,joueur));
            if(TuileEstLibre(i, plateau.GetGrille().length-1)&& !SontCoupInverse(derniercoup,new Coup(new Point(i,-1),true,joueur)))
                coups.add(new Coup(new Point(i,-1),true,joueur));
        }
    }


    private boolean SontCoupInverse(Coup c1, Coup c2){
        if (c1 == null || c2 == null || c1.rangee == null || c2.rangee == null)
            return false;
        return c1.rangee.equals(c2.rangee) && c1.positif != c2.positif;

    }


        private void DeplacementsBille(){
        for (Bille b:billes) {
            if(!b.EstSortie()){
                if(joueurpos != NO && TuileEstLibre(add(b.PositionGet(),DirToPoint(NO))))
                    coups.add(new Coup(b,NO,joueur));
                if(joueurpos != NE && TuileEstLibre(add(b.PositionGet(),DirToPoint(NE))))
                    coups.add(new Coup(b,NE,joueur));
                if(joueurpos != SE && TuileEstLibre(add(b.PositionGet(),DirToPoint(SE))))
                    coups.add(new Coup(b,SE,joueur));
                if(joueurpos != SO && TuileEstLibre(add(b.PositionGet(),DirToPoint(SO))))
                    coups.add(new Coup(b,SO,joueur));
            }
        }
    }

    public boolean TuileEstLibre(Point coordonnee){
        if(coordonnee.x<0 || coordonnee.y < 0 || coordonnee.x > plateau.GetGrille().length-1 || coordonnee.y > plateau.GetGrille().length-1)
            return false;
        return !plateau.GetGrille()[coordonnee.x][coordonnee.y].ContientBille();
    }
    public boolean TuileEstLibre(int x,int y){
        if(x < 0 || y < 0 || x > plateau.GetGrille().length-1 || y > plateau.GetGrille().length-1)
            return false;
        return !plateau.GetGrille()[x][y].ContientBille();
    }
    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

}
