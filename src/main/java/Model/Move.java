package Model;

import Model.Players.Player;
import Model.Support.Marble;
import Model.Support.Board;

import static Global.Tools.*;

import java.awt.*;

public class Move {

    /**
     * Marble indique la marble a bouger (null sinon) direction est la direction
     * dans laquelle cette marble doit bouger (consulter Global.Tools) Point
     * indique la line a bouger (null sinon) (ex : (-1,2) indique la colonne 2,
     * (0,-1) la ligne 0) positif est vrai si la line bouge vers le positif
     * (chaque indice i deviendra i+1) et inversement si faux player indique le
     * player responsable du coup
     */
    Marble marble = null;
    Direction direction;
    Point line = null;
    Player player;
    Move nextMove;

    /**
     * On veut jouer un deplacement de marble
     *
     * @param marble Bille à déplacer
     * @param direction Direction du mouvement
     * @param player Responsable du mouvement
     */
    public Move(Marble marble, Direction direction, Player player) {
        this.marble = marble;
        this.direction = direction;
        this.player = player;
    }

    /**
     * On veut jouer un deplacement de ligne
     *
     * @param line Bille à déplacer
     * @param direction Direction du mouvement
     * @param player Responsable du mouvement
     */
    public Move(Point line, Direction direction, Player player) {
        this.line = line;
        this.direction = direction;
        this.player = player;
    }

    public void perform(Board board) {
        if (marble != null) {
            board.moveMarble(marble, direction);
        }
        if (line != null) {
            board.moveLine(line, direction);
        }
    }

    public void cancel(Board board) {
        if (marble != null) {
            board.moveMarble(marble, reverse(direction));
        }
        if (line != null) {
            board.moveLine(line, reverse(direction));
        }
    }

    public Point getPosition() {
        return marble.getPosition();
    }

    public Point getCoordinatesDirection() {
        return DirToPoint(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public Point getLine() {
        return line;
    }

    public boolean isShift() {
        return (marble == null);
    }

    public void Display() {

        if (line != null) {
            //System.out.println(line);
            //System.out.println(direction);
            int number = -1;
            String cardinalPoint = "";
            String isRowOrCol = "";
            switch (direction) {
                case S:
                    //Moving on y axis
                    cardinalPoint = "South";
                    isRowOrCol = "Column";
                    number = line.x;
                    break;
                case N:
                    //Moving on y axis
                    cardinalPoint = "North";
                    isRowOrCol = "Column";
                    number = line.x;
                    break;
                case W:
                    //Moving on x axis
                    cardinalPoint = "West";
                    isRowOrCol = "Row";
                    number = line.y;
                    break;
                case E:
                    //Moving on x axis
                    cardinalPoint = "East";
                    isRowOrCol = "Row";
                    number = line.y;
                    break;
                default:
                    System.out.println("Erreur");
                    break;
            }

            System.out.println(isRowOrCol + " n°" + number + " shiftable towards " + cardinalPoint + ".");
            return;
        }
        if (marble != null) {
            System.out.println(marble.getPosition());
            System.out.println(direction);

        }
    }

    public boolean isEqual(Move toBeCompared) {
        if (line != null && toBeCompared.line != null) {
            System.out.println("LINE MODE");
            System.out.println("dir1 = " + this.direction + "| dir2 = " + toBeCompared.direction);
            System.out.println("x1 = " + this.line.x + "| x2 = " +  + toBeCompared.line.x);
            System.out.println("y1 = " + this.line.y + "| y2 = " + toBeCompared.line.y);

            return this.direction == toBeCompared.direction
                    && this.line.x == toBeCompared.line.x
                    && this.line.y == toBeCompared.line.y;
        }
        if (marble != null && toBeCompared.marble != null) {
            System.out.println("MARBLE MODE");
            System.out.println(this.direction + " " + toBeCompared.direction);
            System.out.println(this.marble.getPosition().x + " " + toBeCompared.marble.getPosition().x);
            System.out.println(this.marble.getPosition().y + " " + toBeCompared.marble.getPosition().y);
            return this.direction == toBeCompared.direction
                    && this.marble.getPosition().x == toBeCompared.marble.getPosition().x
                    && this.marble.getPosition().y == toBeCompared.marble.getPosition().y;
        }
        return false;
    }

}
