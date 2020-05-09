package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    public MoveCalculator(Board board/*, Player _joueur*/){
        this.board = board;
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
        System.out.println("Coup possible liste : ");
        ListIterator<Move> it = moves.listIterator();
        while(it.hasNext()) {
            Move move = it.next();
            Board new_board = board.clone();
            move.Afficher();
        }
        tilesMoves();
        System.out.println("Ajout tiles moves : ");
        ListIterator<Move> it2 = moves.listIterator();
        while(it2.hasNext()) {
            Move move = it2.next();
            Board new_board = board.clone();
            move.Afficher();
        }
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
                    moves.add(new Move(tileStudied.getPosition(), Direction.N, player));
                }
            }
            tileStudied = board.getGrid()[tmpTileX][4];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Direction.S, player));
                }
            }
            tileStudied = board.getGrid()[0][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Direction.O, player));
                }
            }
            tileStudied = board.getGrid()[4][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Direction.E, player));
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
        System.out.println("Debut du calcul des coups possibles : ");
        this.marbles.forEach((b) -> {
            Point pos = b.getTile().getPosition();
            System.out.println("Pos x : " + pos.x + " pos y : " + pos.y);
            if(this.playerStart != Direction.NO && isTileFree(add(pos, DirToPoint(Direction.NO)))) {
                System.out.println("NO");
                this.moves.add(new Move(b, Direction.NO, this.player));
            }
            if(this.playerStart != Direction.NE && isTileFree(add(pos,DirToPoint(Direction.NE)))) {
                System.out.println("NE");
                System.out.println("In ne : " + b.getTile().getPosition().x + " " + b.getTile().getPosition().y);
                this.moves.add(new Move(b, Direction.NE, this.player));
            }
            if(this.playerStart != Direction.SE && isTileFree(add(pos,DirToPoint(Direction.SE)))) {
                System.out.println("SE");
                this.moves.add(new Move(b, Direction.SE, this.player));
            }
            if (this.playerStart != Direction.SO && isTileFree(add(pos,DirToPoint(Direction.SO)))) {
                System.out.println("SO");
                this.moves.add(new Move(b, Direction.SO, this.player));
            }
        });
    }

    public boolean isTileFree(Point p){
        System.out.println("P x : " + p.x + " p y : " + p.y);
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
