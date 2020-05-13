package Model.Support;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class Tile {
    private Marble marble = null;
    private Point position;
    private int indexOfColor;
    
    /**
     * Constructeur
     */
    Tile(int x, int y) {
        setRandomIndexOfColor();
        this.position = new Point(x, y);
    }
    
    /**
     * Génère un index aléatoire
     */
    private void setRandomIndexOfColor() {
        Random random = new Random();
        this.setIndexOfColor(random.nextInt(3));
    }
    
    /**
     * Change l'index de la couleur
     */
    public void setIndexOfColor(int i) {
        this.indexOfColor = i;
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
    public boolean hasMarble(){
        return marble != null;
    }

    /**
     * Enlève la bille présente sur la tuile
     * @return Marble
     */
    public Marble removeMarble(){
        Marble tmp = marble;
        marble = null;
        return tmp;
    }
    
    public Marble getMarble() {
        return marble;
    }
    
    /**
     * Ajoute une bille à la tuile
     * et met à jour la tuile de la bille
     * @param b 
     */
    public void addMarble(Marble b) {
        b.setTile(this);
        this.marble = b;
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
    public void updatePosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }
    
    /**
     * Retourne la couleur de la bille, null sinon
     * @return Color
     */
    public Color getMarbleColor(){
        if(marble == null)
            return null;
        return marble.getColor();
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    public void print(OutputStream stream) throws IOException {
        stream.write(String.valueOf(this.indexOfColor).getBytes());
    }
}
