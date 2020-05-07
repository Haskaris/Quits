package View;

import Global.Configuration;
import Model.Support.Board;
import Model.Support.Tile;
import java.io.InputStream;
import java.util.ArrayList;

public class ViewBoard extends BoardGraphic {
    ArrayList<ImageQuits> tilesImage;
    Board board;
    int widthTile;
    int heightTile;
    
    // Décalage des éléments (pour pouvoir les animer)
    Vecteur[][] decalages;
    
    ViewBoard(Board plateau) {
        this.board = plateau;
        initTuile();
    }
    
    private void initTuile() {
        tilesImage = new ArrayList<>();
        tilesImage.add(readImage("Tuile1"));
        tilesImage.add(readImage("Tuile2"));
        tilesImage.add(readImage("Tuile3"));
        tilesImage.add(readImage("Tuile4"));
        tilesImage.add(readImage("Tuile5"));
        tilesImage.add(readImage("Tuile6"));
        tilesImage.add(readImage("Tuile7"));
        tilesImage.add(readImage("Tuile8"));
    }
    
    private ImageQuits readImage(String name) {
        String ressource = (String)Configuration.instance().read(name);
        Configuration.instance().logger().info("Lecture de l'image " + ressource + " comme " + name);
        InputStream in = Configuration.charge(ressource);
        try {
            // Chargement d'une image utilisable dans Swing
            return super.readImage(in);
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de charger l'image " + ressource);
            System.exit(1);
        }
        return null;
    }


    @Override
    void tracerNiveau() {
        if (decalages == null) {
            decalages = new Vecteur[5][5];
        }
        
        widthTile = largeur() / 5;
        heightTile = hauteur() / 5;
        
        widthTile = Math.min(widthTile, heightTile);
        heightTile = widthTile;
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = j * widthTile;
                int y = i * heightTile;
                Tile currentTile = board.getGrid()[i][j];
                int index = currentTile.getIndexOfColor();
                tracer(tilesImage.get(index), x, y, widthTile, heightTile);
                if (currentTile.hasMarble()) {
                    drawBall(currentTile.getMarbleColor(), x, y, widthTile, heightTile);
                }
            }
        }
    }

    @Override
    int hauteurCase() {
        return heightTile;
    }

    @Override
    int largeurCase() {
        return widthTile;
    }

    @Override
    public void decale(int l, int c, double dl, double dc) {
        if ((dl != 0) || (dc != 0)) {
            Vecteur v = decalages[l][c];
            if (v == null) {
                    v = new Vecteur();
                    decalages[l][c] = v;
            }
            v.x = dc;
            v.y = dl;
        } else {
            decalages[l][c] = null;
        }
        miseAJour();
    }

    @Override
    public void metAJourDirection(int dL, int dC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeEtape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
