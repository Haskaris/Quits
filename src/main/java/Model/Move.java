package Model;

import Global.Tools;
import Model.Players.Player;
import Model.Support.Marble;
import Model.Support.Board;

import static Global.Tools.*;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Move {

    /**
     * Indique si une bille doit être bougé dans ce mouvement
     */
    Marble marble = null;

    /**
     * Indique si une ligne doit être bougé dans ce mouvement
     */
    Point line = null;

    /**
     * Indique la direction du mouvement
     */
    Direction direction;

    /**
     * Indique la position initiale, peu importe si le mouvement est un décalage
     * ou un déplacement de bille
     */
    Point position;

    /**
     * Indique le type de mouvement
     */
    MoveType type;

    /**
     * Joueur responsable du mouvement
     */
    Player player;

    /**
     * On veut jouer un deplacement de marble
     *
     * @param marble Bille à déplacer
     * @param direction Direction du mouvement
     * @param player Responsable du mouvement
     */
    public Move(Marble marble, Direction direction/*, Player player*/) {
        this.marble = marble;
        this.direction = direction;
        if (isDiagonal(direction)) {
            type = MoveType.MARBLE;
        } else {
            type = MoveType.TILE;
        }
        this.position = new Point(marble.getPosition().x, marble.getPosition().y);
        //this.player = player;
    }

    /**
     * On veut jouer un deplacement de ligne
     *
     * @param line Bille à déplacer
     * @param direction Direction du mouvement
     * @param player Responsable du mouvement
     */
    public Move(Point line, Direction direction/*, Player player*/) {
        this.line = line;
        this.direction = direction;
        if (isDiagonal(direction)) {
            type = MoveType.MARBLE;
        } else {
            type = MoveType.TILE;
        }
        this.position = new Point(line.x, line.y);
        //this.player = player;
    }

    public void perform(Board board) {
        if (!isShift()) {
            board.moveMarble(position, direction);
        } else {
            board.moveLine(position, direction);
        }
    }

    public void cancel(Board board) {
        if (!isShift()) {
            board.moveMarble(Tools.getNextPoint(position, direction), reverse(direction));
        } else {
            board.moveLine(position, reverse(direction));
        }
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Retourne le décalage pour acceder à la position possible
     *
     * @return Point - Décalage du mouvement par rapport à sa direction
     * @see Tools.DirToPoint(Direction d)
     */
    public Point getCoordinatesDirection() {
        return DirToPoint(direction);
    }

    /**
     * Retourne la direction du mouvement
     *
     * @return Direction - Direction du mouvement
     */
    public Direction getDirection() {
        return direction;
    }

    public Point getLine() {
        return line;
    }

    /**
     * Permet de savoir si le mouvement est un déplacement de tuiles
     *
     * @return boolean - Vrai si c'est un déplacement de tuiles
     */
    public boolean isShift() {
        return (type == MoveType.TILE);
    }

    /**
     * Permet de savoir si un mouvement est égale à un autre
     *
     * @param toBeCompared Mouvement à comparer
     * @return Vrai si les mouvements sont égaux
     */
    public boolean isEqual(Move toBeCompared) {
        boolean sameDirection = (this.direction == toBeCompared.direction);
        return sameDirection
                && position.x == toBeCompared.position.x
                && position.y == toBeCompared.position.y;
    }

    public void print(OutputStream stream) throws IOException {
        //Début du mouvement
        // x,y DIR
        stream.write(String.valueOf(position.x).getBytes());
        stream.write(',');
        stream.write(String.valueOf(position.y).getBytes());

        stream.write(' ');
        stream.write(direction.name().getBytes());
        //Fin du mouvement
    }

    public static Move load(String in_stream) {
        // 2,3 NE
        String[] sSplit = in_stream.split(" ");//On sépare les coordonnées de la direction
        String[] coordTab = sSplit[0].split(",");//On sépare les deux coordonnées
        int x = Integer.valueOf(coordTab[0]);//Ici le x
        int y = Integer.valueOf(coordTab[1]);//Ici le y
        Direction d = Tools.Direction.valueOf(sSplit[1]);//Ici la direction
        return new Move(new Point(x, y), d);
    }

    public void display() {
        if (isShift()) {
            System.out.print("Line move ");
        } else {
            System.out.print("Marble move ");
        }
        System.out.print(position.x + "|" + position.y);
        System.out.println("-" + direction.name());

    }

}
