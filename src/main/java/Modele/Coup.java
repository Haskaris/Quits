package Modele;

import Modele.Joueurs.Joueur;
import Modele.Support.Bille;

import static Global.Tools.Dir;
import static Global.Tools.InverseDir;

import java.awt.*;

public class Coup {
    /**
     * Bille indique la bille a bouger (null sinon)
     * direction est la direction dans laquelle cette bille doit bouger (consulter Global.Tools)
     * Point indique la rangee a bouger (null sinon) (ex : (-1,2) indique la colonne 2, (0,-1) la ligne 0)
     * positif est vrai si la rangee bouge vers le positif (chaque indice i deviendra i+1) et inversement si faux
     * joueur indique le joueur responsable du coup
     */
    Bille bille = null;
    Dir direction;
    Point rangee = null;
    Boolean positif;
    Joueur joueur;
    Coup next;

    /**
    * On veut jouer un deplacement de bille
    */
    public Coup(Bille _bille, Dir _dir/*,Joueur_Interface _joueur*/) {
        bille = _bille;
        direction = _dir;
        //joueur = _joueur;
    }

    /**
     * On veut jouer un deplacement de ligne
     */
    public Coup(Point _rangee, Boolean _positif/*,Joueur_Interface _joueur*/) {
        rangee = _rangee;
        positif = _positif;
        //joueur = _joueur;
    }

    public void Execute() {
        if(bille!=null){
            GameManager.plateau.DeplacerBille(bille, direction);
        }
        if(rangee != null){
            GameManager.plateau.DeplacerRangee(rangee,positif);
        }
    }

    public void Dexecute(){
        if(bille!=null){
            GameManager.plateau.DeplacerBille(bille,InverseDir(direction));
        }
        if(rangee != null){
            GameManager.plateau.DeplacerRangee(rangee,!positif);
        }
    }

    public void Afficher(){
        if(bille!=null){
            System.out.println(bille.PositionGet());
            System.out.println(direction);
        }
        if(rangee != null){
            System.out.println(rangee);
            System.out.println(positif);        }
    }

}
