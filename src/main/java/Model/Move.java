package Model;

import Model.Players.Player;
import Model.Support.Marble;
import Model.Support.Board;

import static Global.Tools.*;

import java.awt.*;

public class Move implements Cloneable{
    /**
     * Marble indique la marble a bouger (null sinon)
 direction est la direction dans laquelle cette marble doit bouger (consulter Global.Tools)
 Point indique la line a bouger (null sinon) (ex : (-1,2) indique la colonne 2, (0,-1) la ligne 0)
 positif est vrai si la line bouge vers le positif (chaque indice i deviendra i+1) et inversement si faux
 player indique le player responsable du coup
     */
    Marble marble = null;
    Direction direction;
    Point line = null;
    Player player;
    Move nextMove;

    /**
     * On veut jouer un deplacement de marble
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
        //Afficher();
        if(marble != null){
            board.moveMarble(marble, direction);
        } else if (line != null){
            board.moveLine(line, direction);
        }
    }

    public void cancel(Board board){
        if(marble != null){
            board.moveMarble(marble, reverse(direction));
        }
        if(line != null){
            board.moveLine(line, reverse(direction));
        }
    }

    public void Afficher(){
        if(marble!=null){
            System.out.println(marble.getTile().getPosition() + " direction : " + direction);
        }
        if(line != null){
            System.out.println(line);
            //System.out.println(positif);
        }
    }
}
