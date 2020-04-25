package Modele;

import static Global.Tools.Dir;
import static Global.Tools.InverseDir;

import java.awt.*;

public class Coup extends Commande {
    /**
     * Bille indique la bille a bouger (null sinon)
     * dir est la direction dans laquelle cette bille doit bouger (consulter Global.Tools)
     * Point indique la rangee a bouger (null sinon) (ex : (-1,2) indique la colonne 2, (0,-1) la ligne 0)
     * positif est vrai si la rangee bouge vers le positif (chaque indice i deviendra i+1) et inversement si faux
     */
    Bille bille = null;
    Dir dir;
    Point rangee = null;
    Boolean positif;

    /**
    * On veut jouer un deplacement de bille
    */
    public Coup(Bille _bille, Dir _dir) {
        bille = _bille;
        dir = _dir;
    }

    /**
     * On veut jouer un deplacement de ligne
     */
    public Coup(Point _rangee, Boolean _positif) {
        rangee = _rangee;
        positif = _positif;
    }

    public void Execute() {
        if(bille!=null){
            GameManager.plateau.DeplacerBille(bille,dir);
        }
        if(rangee != null){
            GameManager.plateau.DeplacerRangee(rangee,positif);
        }
    }

    public void Dexecute(){
        if(bille!=null){
            GameManager.plateau.DeplacerBille(bille,InverseDir(dir));
        }
        if(rangee != null){
            GameManager.plateau.DeplacerRangee(rangee,!positif);
        }
    }

}
