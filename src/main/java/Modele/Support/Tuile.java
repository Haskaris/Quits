package Modele.Support;

import java.awt.*;
import java.util.Random;

public class Tuile {
    private Bille bille = null;
    private int indexOfColor;
    
    Tuile() {
        Random random = new Random();
        indexOfColor = random.nextInt(8);
    }
    
    public int getIndexOfColor() {
        return this.indexOfColor;
    }

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
            return 9;
        return bille.CouleurGet();
    }
}
