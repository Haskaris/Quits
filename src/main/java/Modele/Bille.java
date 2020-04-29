package Modele;

import Global.Configuration;

import java.awt.*;

public class Bille {
    private int couleur;
    private Point position;

    public Bille(int _couleur){
        couleur = _couleur;
    }

    public int CouleurGet(){
        return couleur;
    }

    public void PositionSet(Point _position){
        position = _position;
    }

    public Point PositionGet(){
        return position;
    }

    public boolean EstSortie() {
        int t = (Integer) Configuration.Lis("Taille");
        switch (couleur) {
            case 0:
                return (position.x == t-1 && position.y == t-1);
            case 1:
                return (position.x == 0 && position.y == t-1);
            case 2:
                return (position.x == 0 && position.y == 0);
            case 3:
                return (position.x == t-1 && position.y == 0);
        }
        return false;
    }
}
