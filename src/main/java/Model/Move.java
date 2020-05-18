package Model;

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
    public Move(Marble marble, Direction direction/*, Player player*/) {
        this.marble = marble;
        this.direction = direction;
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
        //this.player = player;
    }

    public void perform(Board board) {
        if (marble != null) {
            board.moveMarble(marble, direction);
        }
        if (line != null) {
            switch(direction) {
                case N:
                case E:
                case S:
                case W:
                    board.moveLine(line, direction);
                    break;
                case NE:
                case SE:
                case SW:
                case NW:
                    board.moveMarble(line, direction);
                    break;
            }
        }
    }

    public void cancel(Board board) {
        if (marble != null) {
            board.moveMarble(marble, reverse(direction));
        }
        if (line != null) {
            switch(direction) {
                case N:
                case E:
                case S:
                case W:
                    board.moveLine(line, reverse(direction));
                    break;
                case NE:
                case SE:
                case SW:
                case NW:
                    board.moveMarble(line, reverse(direction));
                    break;
            }
        }
    }

    public Point getPosition() {
        return marble.getPosition();
    }

    /**
     * Retourne le décalage pour acceder à la position possible
     * @return Point - Décalage du mouvement par rapport à sa direction
     * @see Tools.DirToPoint(Direction d)
     */
    public Point getCoordinatesDirection() {
        return DirToPoint(direction);
    }

    /**
     * Retourne la direction du mouvement
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
     * @return boolean - Vrai si c'est un déplacement de tuiles
     */
    public boolean isShift() {
        return (marble == null);
    }

    /**
     * Permet de savoir si un mouvement est égale à un autre
     * @param toBeCompared Mouvement à comparer
     * @return Vrai si les mouvements sont égaux
     */
    public boolean isEqual(Move toBeCompared) {
        boolean sameDirection = (this.direction == toBeCompared.direction);
        if (line != null && toBeCompared.line != null) {
            return sameDirection
                    && this.line.x == toBeCompared.line.x
                    && this.line.y == toBeCompared.line.y;
        } else if (marble != null && toBeCompared.marble != null) {
            return sameDirection
                    && this.marble.getPosition().x == toBeCompared.marble.getPosition().x
                    && this.marble.getPosition().y == toBeCompared.marble.getPosition().y;
        }
        return false;
    }

    /////////////////////////////////  IO  /////////////////////////////////////
    public void printPast(OutputStream stream) throws IOException {
        if (this.lastMove != null) {
            this.lastMove.printPast(stream);
        }
        
        this.print(stream);
    }
    
    public void printFuture(OutputStream stream) throws IOException {
        this.print(stream);
        
        if (this.nextMove != null) {
            this.nextMove.printFuture(stream);
        }
    }
    
    public void print(OutputStream stream) throws IOException {
        //Début du mouvement
        stream.write("#".getBytes());
        if (this.marble != null) {
            this.marble.print(stream);
        } else if (this.line != null) {
            stream.write(String.valueOf(this.line.x).getBytes());
            stream.write('-');
            stream.write(String.valueOf(this.line.y).getBytes());
            stream.write('/');
        }
        stream.write(direction.name().getBytes());
        stream.write("#".getBytes());
        //Fin du mouvement
    }
    
    public static Move loadPast(String in_stream) {
        String param = in_stream.split("#")[1];
        Move oldestMove = load(param);
        
        String tmp = in_stream.substring(param.length()+2);
        if (!"".equals(tmp)) {
            Move tmpMove = loadPast(tmp);
            oldestMove.nextMove = tmpMove;
            tmpMove.lastMove = oldestMove;
            oldestMove = tmpMove;
        }
        
        return oldestMove;
    }
    
    public static Move loadFuture(String in_stream) {
        String param = in_stream.split("#")[1];
        Move toReturn = load(param);
        
        String tmp = in_stream.substring(param.length()+2);
        if (!"".equals(tmp)) {
            toReturn.nextMove = loadFuture(tmp);
            toReturn.nextMove.lastMove = toReturn;
        }
        
        return toReturn;
    }
    
    public static Move load(String in_stream) {
        String[] coord = in_stream.split("/")[0].split("-");
        String dir = in_stream.split("/")[1];
        Move toReturn = new Move(new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])), Direction.valueOf(dir));
        return toReturn;
    }

}
