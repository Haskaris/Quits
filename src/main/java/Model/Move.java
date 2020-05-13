package Model;

import Model.Players.Player;
import Model.Support.Marble;
import Model.Support.Board;

import static Global.Tools.*;

import java.awt.*;

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
     * Joueur responsable du mouvement
     */
    Player player;
    
    /**
     * Prochain mouvement de la pile
     */
    Move nextMove;
    
    /**
     * Mouvement précédent de la pile
     */
    Move lastMove;

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
            Display();
            board.moveLine(line, direction);
        }
    }

    public void cancel(Board board) {
        if (marble != null) {
            board.moveMarble(marble, reverse(direction));
        }
        if (line != null) {
            Display();
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
        if (marble != null) {
            System.out.println(marble.getPosition());
            System.out.println(direction);
        }
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

        }
    }
    
    public boolean isEqual(Move toBeCompared) {
        return this.direction == toBeCompared.direction
                && this.line.x == toBeCompared.line.x
                && this.line.y == toBeCompared.line.y;
    }

}
