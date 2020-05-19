package Model;

import Model.Players.Player;
import Model.Support.Marble;
import Model.Support.Board;

import static Global.Tools.*;

import java.awt.*;
import java.io.Serializable;

public class Move implements Serializable {
    /**
     * Marble indique la marble a bouger (null sinon)
 direction est la direction dans laquelle cette marble doit bouger (consulter Global.Tools)
 Point indique la line a bouger (null sinon) (ex : (-1,2) indique la colonne 2, (0,-1) la ligne 0)
 positif est vrai si la line bouge vers le positif (chaque indice i deviendra i+1) et inversement si faux
 player indique le player responsable du coup
     */
    Point marble = null;
    Direction direction;
    Point line = null;
    String playername;
    Move nextMove;

    /**
     * On veut jouer un deplacement de marble
     * @param marble Bille à déplacer
     * @param direction Direction du mouvement
     * @param playername Responsable du mouvement
    */
    public Move(Marble marble, Direction direction, String playername) {
        this.marble = marble.getTile().getPosition();
        this.direction = direction;
        this.playername = playername;
    }

    /**
     * On veut jouer un deplacement de ligne
     * @param line Bille à déplacer
     * @param direction Direction du mouvement
     * @param playername Responsable du mouvement
    */
    public Move(Point line, Direction direction, String playername) {
        this.line = line;
        this.direction = direction;
        this.playername = playername;
    }

    public void perform(Board board) {
        if(marble != null){
            board.moveMarble(getMarble(board), direction);
        }
        if(line != null){
            board.moveLine(line, direction);
        }
    }

    public void cancel(Board board){
        if(marble != null){
            board.moveMarble(getMarble(board), reverse(direction));
        }
        if(line != null){
            board.moveLine(line, reverse(direction));
        }
    }

    public void print() {
        if (marble != null) {
            System.out.println(marble);
            System.out.println(direction);
        }
        if (line != null) {
            System.out.println(line);
            System.out.println(direction);
        }
    }

    public Marble getMarble(Board b){
        return b.getGrid()[marble.x][marble.y].getMarble();
    }
}
