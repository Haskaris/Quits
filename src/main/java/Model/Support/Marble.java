package Model.Support;

import Model.AI.Node;
import Model.Players.Player;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Marble {
    private final Color color;
    private Tile tile;

    /**
     * Constructeur
     * @param _color 
     */
    public Marble(Color _color){
        this.color = _color;
    }

    /**
     * Retourne la couleur de la bille
     * @return 
     */
    public Color getColor(){
        return this.color;
    }
    
    /**
     * Change la tuile de la bille
     * @param _tile 
     */
    public void setTile(Tile _tile) {
        this.tile = _tile;
    }
    
    /**
     * Retourne la tuile sur laquelle est la tuile
     * @return 
     */
    public Tile getTile() {
        return this.tile;
    }
    
    public Point getPosition() {
        return this.tile.getPosition();
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    public void print(OutputStream stream) throws IOException {
        stream.write(String.valueOf(this.getTile().getPosition().x).getBytes());
        stream.write('-');
        stream.write(String.valueOf(this.getTile().getPosition().y).getBytes());
        stream.write('/');
    }
}