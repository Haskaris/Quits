package Modele.Support;

import java.awt.*;
import java.util.Random;

public class Tuile {
    private Bille bille = null;
    private Point position;
    private int indexOfColor;
    
    /**
     * Constructeur
     */
    Tuile(int x, int y) {
        setIndexOfColor();
        this.position = new Point(x, y);
    }
    
    /**
     * Génère un index aléatoire
     */
    private void setIndexOfColor() {
        Random random = new Random();
        this.indexOfColor = random.nextInt(8);
    }
    
    /**
     * Retourne l'index de l'image de la tuile
     * @return int
     */
    public int getIndexOfColor() {
        return this.indexOfColor;
    }

    /**
     * Retourne vrai si la tuile contient une bille
     * @return boolean
     */
    public boolean contientBille(){
        return bille != null;
    }

    /**
     * Enlève la bille présente sur la tuile
     * @return Bille
     */
    public Bille enleverBille(){
        Bille btmp = bille;
        bille = null;
        return btmp;
    }
    
    /**
     * Ajoute une bille à la tuile
     * et met à jour la tuile de la bille
     * @param b 
     */
    public void addBille(Bille b) {
        b.setTuile(this);
        this.bille = b;
    }
    
    /**
     * Retourne la position de la tuile
     * @return Point
     */
    public Point getPosition() {
        return this.position;
    }
    
    /**
     * Met à jour la position de la tuile
     * @param x
     * @param y 
     */
    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }
    
    /*public void MettreBille(Bille _bille, Point position){
        bille = _bille;
        //bille.PositionSet(position);
    }*/
    
    /**
     * Retourne la couleur de la bille, null sinon
     * @return Color
     */
    public Color getCouleurBille(){
        if(bille == null)
            return null;
        return bille.CouleurGet();
    }
}
