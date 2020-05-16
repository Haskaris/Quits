package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Model.Players.Player;
import Model.Support.*;

import static Global.Tools.*;

public class MoveCalculator {

    /**
     * Crée la liste de coup possible pour un player
     */
    Board board;
    Player player;
    Direction playerStart;
    List<Marble> marbles;
    List<Move> moves;
    Move lastMove;

    public MoveCalculator(Board board/*, Player _joueur*/) {
        this.board = board;
        this.player = this.board.getCurrentPlayer();
        this.marbles = this.player.getMarbles();
        this.playerStart = player.getStartPoint();
        this.moves = new ArrayList<>();
        this.lastMove = this.board.getHistory().lastMove();
    }

    /**
     * Retourne une liste de coup possible
     *
     * @return
     */
    public List<Move> possibleMoves() {
        marblesMoves();
        return moves;
    }
    
    public List<Move> possibleShifts() {
        tilesMoves();
        return moves;
    }

    public List<Move> possibleMovesWithSource(int line, int column) {
        marblesMovesWithSource(line, column);
        tilesMovesWithSource(line, column);
        return moves;
    }

    /**
     * Pas bon
     */
    private void tilesMoves() {
        ArrayList<Tile> movableTiles = new ArrayList<>();

        //Pour chaque marble on ajoute sa ligne et sa colonne
        for (Marble ball : marbles) {
            tilesMovesWithSource(ball.getPosition().x, ball.getPosition().y);
        }
        
        /*for (int i = 0; i < board.GetGrille().length ; i++) {
            if(TuileEstLibre(0,i) && !areReversed(lastMove, new Move(new Point(-1,i),false,player)))
                moves.add(new Move(new Point(-1,i),false,player));
            if(TuileEstLibre(board.GetGrille().length-1,i) && !areReversed(lastMove, new Move(new Point(-1,i),true,player)))
                moves.add(new Move(new Point(-1,i),true,player));
            if(TuileEstLibre(i,0)  && !areReversed(lastMove, new Move(new Point(i,-1),false,player)))
                moves.add(new Move(new Point(i,-1),false,player));
            if(TuileEstLibre(i, board.GetGrille().length-1)&& !areReversed(lastMove, new Move(new Point(i,-1),true,player)))
                moves.add(new Move(new Point(i,-1),true,player));
        }*/
    }

    /**
     * À refaire du coup
     *
     * @param c1
     * @param c2
     * @return
     */
    private boolean areReversed(Move c1, Move c2) {
        //if (c1 == null || c2 == null || c1.line == null || c2.line == null)
        return false;
        //return c1.line.equals(c2.line) && c1.positif != c2.positif;
    }

    private void marblesMoves() {
        this.marbles.forEach((b) -> {
            Point pos = b.getTile().getPosition();
            marblesMovesWithSource(pos.x, pos.y);
        });
    }

    private void tilesMovesWithSource(int line, int column) {
        if (board.getGrid()[line][column].hasMarble()) {
            Marble ball = board.getGrid()[line][column].getMarble();
            Tile tmpTile = ball.getTile();
            int tmpTileX = tmpTile.getPosition().x;
            int tmpTileY = tmpTile.getPosition().y;
            Tile tileStudied = board.getGrid()[tmpTileX][0];

            if (!tileStudied.hasMarble()) {
                Move toBeAdded = new Move(tileStudied.getPosition(), Direction.N, player);
                if (!moveExists(toBeAdded)) {
                    moves.add(toBeAdded);
                }
            }

            tileStudied = board.getGrid()[tmpTileX][4];

            if (!tileStudied.hasMarble()) {
                Move toBeAdded = new Move(tileStudied.getPosition(), Direction.S, player);
                if (!moveExists(toBeAdded)) {
                    moves.add(toBeAdded);
                }
            }

            tileStudied = board.getGrid()[0][tmpTileY];

            if (!tileStudied.hasMarble()) {
                Move toBeAdded = new Move(tileStudied.getPosition(), Direction.W, player);
                if (!moveExists(toBeAdded)) {
                    moves.add(toBeAdded);
                }
            }

            tileStudied = board.getGrid()[4][tmpTileY];

            if (!tileStudied.hasMarble()) {
                Move toBeAdded = new Move(tileStudied.getPosition(), Direction.E, player);
                if (!moveExists(toBeAdded)) {
                    moves.add(toBeAdded);
                }
            }

        }
    }

    private void marblesMovesWithSource(int line, int column) {
        if (board.getGrid()[line][column].hasMarble()) {
            Marble b = board.getGrid()[line][column].getMarble();
            Point pos = b.getTile().getPosition();
            if (this.playerStart != Direction.NW && isTileFree(add(pos, DirToPoint(Direction.NW)))) {
                this.moves.add(new Move(b, Direction.NW, this.player));
            }
            if (this.playerStart != Direction.NE && isTileFree(add(pos, DirToPoint(Direction.NE)))) {
                this.moves.add(new Move(b, Direction.NE, this.player));
            }
            if (this.playerStart != Direction.SE && isTileFree(add(pos, DirToPoint(Direction.SE)))) {
                this.moves.add(new Move(b, Direction.SE, this.player));
            }
            if (this.playerStart != Direction.SW && isTileFree(add(pos, DirToPoint(Direction.SW)))) {
                this.moves.add(new Move(b, Direction.SW, this.player));
            }
        }
    }

    public boolean isTileFree(Point p) {
        if (p.x < 0 || p.y < 0 || p.x > board.getGrid().length - 1 || p.y > board.getGrid().length - 1) {
            return false;
        }
        return !board.getGrid()[p.x][p.y].hasMarble();
    }

    /*public boolean isTileFree(int x,int y){
        if(x < 0 || y < 0 || x > board.getGrid().length-1 || y > board.getGrid().length-1)
            return false;
        return !board.getGrid()[x][y].hasMarble();
    }*/
    private Point add(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    private boolean moveExists(Move myM) {
        boolean found = false;
        for (Move m : moves) {
            //Compare myM to m

            if (m.isEqual(myM)) {
                found = true;
                break;
            }
        }
        return found;
    }

}
