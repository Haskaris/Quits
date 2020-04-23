package Modele;

import static Global.Tools.Dir;
import java.awt.*;

public class Coup extends Commande {
    Bille bille = null;
    Dir dir;
    Point rangee = null;
    Boolean positif;

    public Coup(Bille _bille, Dir _dir) {
        bille = _bille;
        dir = _dir;
    }
    public Coup(Point _rangee, Boolean _positif) {
        rangee = _rangee;
        positif = _positif;
    }

    public void Execute() {

    }

    public void Dexecute(){

    }

}
