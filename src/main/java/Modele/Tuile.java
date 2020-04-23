package Modele;

public class Tuile {
    private Bille bille = null;

    public boolean ContientBille(){
        return bille != null;
    }

    public Bille OterBille(){
        Bille btmp = bille;
        bille = null;
        return btmp;
    }

    public void MettreBille(Bille _bille){
        bille = _bille;
    }

    public int CouleurBille(){
        if(bille == null)
            return 0;
        return bille.CouleurGet();
    }
}
