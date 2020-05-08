package View;

import Global.Configuration;
import Model.Move;
import Model.Support.Board;
import Model.Support.Tile;
import java.awt.Color;
import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;

public class ViewBoard extends BoardGraphic {

    ArrayList<ImageQuits> tileImages;
    ImageQuits defaultMarble;
    ImageQuits selectedTile;
    ImageQuits arrowUp;
    ImageQuits arrowDown;
    ImageQuits arrowLeft;
    ImageQuits arrowRight;

    Board board;
    int widthTile;
    int heightTile;

    // Décalage des éléments (pour pouvoir les animer)
    Vecteur[][] shifts;

    ViewBoard(Board plateau) {
        this.board = plateau;
        initTile();
    }

    private void initTile() {
        tileImages = new ArrayList<>();
        tileImages.add(readImage("Tuile1"));
        tileImages.add(readImage("Tuile2"));
        tileImages.add(readImage("Tuile3"));
        selectedTile = readImage("SelectedTile");
        arrowUp = readImage("ArrowUp");
        arrowRight = readImage("ArrowRight");
        arrowDown = readImage("ArrowDown");
        arrowLeft = readImage("ArrowLeft");
        defaultMarble = readImage("DefaultMarble");
    }

    private ImageQuits readImage(String name) {
        String ressource = (String) Configuration.instance().read(name);
        Configuration.instance().logger().info("Image " + ressource + " read as " + name);
        InputStream in = Configuration.charge(ressource);
        try {
            // Chargement d'une image utilisable dans Swing
            return super.readImage(in);
        } catch (Exception e) {
            Configuration.instance().logger().severe("Image " + ressource + " impossible to charge.");
            System.exit(1);
        }
        return null;
    }

    @Override
    void drawBoard() {
        if (shifts == null) {
            shifts = new Vecteur[5][5];
        }

        widthTile = largeur() / 6;
        heightTile = hauteur() / 6;

        widthTile = Math.min(widthTile, heightTile);
        heightTile = widthTile;
        Color allyColor = null;
        boolean isColorNull = true;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getGrid()[i][j].hasMarble() && board.availableTiles[i][j] == 1) {
                    allyColor = board.getGrid()[i][j].getMarbleColor();
                    isColorNull = false;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = j * widthTile + (widthTile / 2);
                int y = i * heightTile + (heightTile / 2);
                Tile currentTile = board.getGrid()[i][j];
                if (isColorNull) {
                    allyColor = currentTile.getMarbleColor();
                }
                int index = currentTile.getIndexOfColor();

                tracer(tileImages.get(index), x, y, widthTile, heightTile);

                //Display of a line that can be shifted towards positive values
                //TODO
                //Display of a line that can be shifted towards negative values
                //TODO
                if (currentTile.hasMarble()) {
                    if (board.availableTiles[i][j] == 1) {
                        tracer(selectedTile, x, y, widthTile, heightTile);
                        //drawBall(new Color(1f, 1f, 1f, 0.3f), x, y, widthTile, heightTile);

                    }
                    drawBall(currentTile.getMarbleColor(), x, y, widthTile, heightTile);
                    tracer(defaultMarble, x, y, widthTile, heightTile);
                }
                if (board.availableTiles[i][j] == 2) {//The available moves for this marble
                    Color n = new Color(
                            allyColor.getRed(),
                            allyColor.getGreen(),
                            allyColor.getBlue(),
                            100);
                    drawBall(n, x, y, widthTile, heightTile);
                }
            }
        }
        for (Move m : board.allPotentialShifts) {

            Point p = m.getLine();
            int rowOrColIndex = -1;
            switch (m.getDirection()) {
                case S:
                    rowOrColIndex = p.x;
                    //Display a down arrow on the upper part of the board
                    tracer(arrowDown, widthTile * rowOrColIndex + (widthTile / 2), 0, widthTile, heightTile / 2);
                    break;
                case N:

                    rowOrColIndex = p.x;
                    //Display a down arrow on the upper part of the board
                    tracer(arrowUp, widthTile * rowOrColIndex + (widthTile / 2), (int) (widthTile * 5.5), widthTile, heightTile / 2);
                    break;
                case W:
                    rowOrColIndex = p.y;
                    //Display a down arrow on the upper part of the board
                    tracer(arrowLeft, (int) (widthTile * 5.5), widthTile * rowOrColIndex + (widthTile / 2), widthTile / 2, heightTile);
                    break;
                case E:
                    rowOrColIndex = p.y;
                    //Display a down arrow on the upper part of the board
                    tracer(arrowRight, 0, widthTile * rowOrColIndex + (widthTile / 2), widthTile / 2, heightTile);
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    int getHeightTile() {
        return heightTile;
    }

    @Override
    int getWidthTile() {
        return widthTile;
    }

    @Override
    public void shift(int l, int c, double dl, double dc) {
        if ((dl != 0) || (dc != 0)) {
            Vecteur v = shifts[l][c];
            if (v == null) {
                v = new Vecteur();
                shifts[l][c] = v;
            }
            v.x = dc;
            v.y = dl;
        } else {
            shifts[l][c] = null;
        }
        miseAJour();
    }

    @Override
    public void updateDirection(int dL, int dC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
