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
    Dir playerStart;
    List<Marble> marbles;
    List<Move> moves;
    Move lastMove;

    public MoveCalculator(Board _board/*, Player _joueur*/){
        this.board = _board;
        this.player = this.board.currentPlayer();
        this.marbles = this.player.getMarbles();
        this.playerStart = player.getStartPoint();
        this.moves = new ArrayList<>();
        this.lastMove = this.board.history.lastMove();
    }

    /**
     * Retourne une liste de coup possible
     * @return 
     */
    public List<Move> coupsPossibles(){
        marblesMoves();
        tilesMoves();
        return moves;
    }

    /**
     * Pas bon
     */
    private void tilesMoves() {
        ArrayList<Tile> movableTiles = new ArrayList<>();
        
        //Pour chaque marble on ajoute sa ligne et sa colonne
        for(Marble ball: marbles) {
            Tile tmpTile = ball.getTile();
            int tmpTileX = tmpTile.getPosition().x;
            int tmpTileY = tmpTile.getPosition().y;
            Tile tileStudied = board.getGrid()[tmpTileX][0];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Dir.N, player));
                }
            }
            tileStudied = board.getGrid()[tmpTileX][4];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Dir.S, player));
                }
            }
            tileStudied = board.getGrid()[0][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Dir.O, player));
                }
            }
            tileStudied = board.getGrid()[4][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Dir.E, player));
                }
            }
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
     * @param c1
     * @param c2
     * @return 
     */
    private boolean areReversed(Move c1, Move c2){
        //if (c1 == null || c2 == null || c1.line == null || c2.line == null)
        return false;
        //return c1.line.equals(c2.line) && c1.positif != c2.positif;
    }

    private void marblesMoves(){
        this.marbles.forEach((b) -> {
            Point pos = b.getTile().getPosition();
            if(this.playerStart != Dir.NO && isTileFree(add(pos, DirToPoint(Dir.NO)))) {
                this.moves.add(new Move(b, Dir.NO, this.player));
            }
            if(this.playerStart != Dir.NE && isTileFree(add(pos,DirToPoint(Dir.NE)))) {
                this.moves.add(new Move(b, Dir.NE, this.player));
            }
            if(this.playerStart != Dir.SE && isTileFree(add(pos,DirToPoint(Dir.SE)))) {
                this.moves.add(new Move(b, Dir.SE, this.player));
            }
            if (this.playerStart != Dir.SO && isTileFree(add(pos,DirToPoint(Dir.SO)))) {
                this.moves.add(new Move(b, Dir.SO, this.player));
            }
        });
    }

    public boolean isTileFree(Point p){
        if (p.x<0 || p.y < 0 || p.x > board.getGrid().length-1 || p.y > board.getGrid().length-1) {
            return false;
        }
        return !board.getGrid()[p.x][p.y].hasMarble();
    }
    
    /*public boolean isTileFree(int x,int y){
        if(x < 0 || y < 0 || x > board.getGrid().length-1 || y > board.getGrid().length-1)
            return false;
        return !board.getGrid()[x][y].hasMarble();
    }*/
    
    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

}
