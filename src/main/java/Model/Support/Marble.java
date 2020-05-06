package Model.Support;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

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
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    public void print(OutputStream stream) throws IOException {
        stream.write(this.getTile().getPosition().x);
        stream.write('-');
        stream.write(this.getTile().getPosition().y);
        stream.write('/');
    }
}
