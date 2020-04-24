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
    public boolean EstSortie(){
        int t= (Integer)Configuration.Lis("Taille");
        return position.x < 0 || position.y < 0 || position.x > t  || position.y > t;
    }
}
