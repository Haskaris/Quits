package Modele;

import java.awt.*;

public class Tuile {
    private Bille bille = null;

    public boolean ContientBille(){
        return bille != null;
    }

    public Bille EnleverBille(){
        Bille btmp = bille;
        bille = null;
        return btmp;
    }

    public void MettreBille(Bille _bille, Point position){
        bille = _bille;
        bille.PositionSet(position);
    }
    public int CouleurBille(){
        if(bille == null)
            return 0;
        return bille.CouleurGet();
    }
}
