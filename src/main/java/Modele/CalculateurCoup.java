package Modele;

import Global.Tools;
import Modele.Joueurs.Joueur;
import Modele.Support.Bille;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Global.Tools.DirToPoint;
import static Modele.GameManager.plateau;
import static Global.Tools.Dir;
import static Global.Tools.Dir.NO;
import static Global.Tools.Dir.NE;
import static Global.Tools.Dir.SE;
import static Global.Tools.Dir.SO;


public class CalculateurCoup {
    /**
     * Crée la liste de coup possible pour un joueur
     */

    Joueur joueur;
    Dir joueurpos;
    Bille[] billes;
    List<Coup> coups;

    CalculateurCoup(Joueur _joueur){
        joueur = _joueur;
        billes = joueur.billes;
        coups = new ArrayList<>();
        joueurpos = Tools.IntToDir(joueur.couleur);
    }

    public List<Coup> CoupsPossible(){
        DeplacementsBille();
        DeplacementsTuile();
        return coups;
    }


    private void DeplacementsTuile() {
        Coup dernier = GameManager.historique.passe;
        for (int i = 0; i < plateau.GetGrille().length ; i++) {
            if(TuileEstLibre(0,i) && !SontCoupInverse(dernier,new Coup(new Point(-1,i),false)))
                coups.add(new Coup(new Point(-1,i),false));
            if(TuileEstLibre(plateau.GetGrille().length-1,i) && !SontCoupInverse(dernier,new Coup(new Point(-1,i),true)))
                coups.add(new Coup(new Point(-1,i),true));
            if(TuileEstLibre(i,0)  && !SontCoupInverse(dernier,new Coup(new Point(i,-1),false)))
                coups.add(new Coup(new Point(i,-1),false));
            if(TuileEstLibre(i, plateau.GetGrille().length-1)&& !SontCoupInverse(dernier,new Coup(new Point(i,-1),true)))
                coups.add(new Coup(new Point(i,-1),true));
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
                    coups.add(new Coup(b,NO));
                if(joueurpos != NE && TuileEstLibre(add(b.PositionGet(),DirToPoint(NE))))
                    coups.add(new Coup(b,NE));
                if(joueurpos != SE && TuileEstLibre(add(b.PositionGet(),DirToPoint(SE))))
                    coups.add(new Coup(b,SE));
                if(joueurpos != SO && TuileEstLibre(add(b.PositionGet(),DirToPoint(SO))))
                    coups.add(new Coup(b,SO));
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
