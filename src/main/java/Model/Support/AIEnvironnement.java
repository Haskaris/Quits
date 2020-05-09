package Model.Support;

import Global.Tools;
import Model.History;
import Model.Move;
import Model.Players.Player;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static Global.Tools.DirToPoint;

public class AIEnvironnement {
    private int[][] _grid;
    private ArrayList<Integer> _players;
    private ArrayList<ArrayList<Point>> _playerMarble;
    public int _currentPlayer;
    private ArrayList<Point> _startingPoint;

    public AIEnvironnement(){
        this._players = new ArrayList<>();
        this._grid = new int[5][5];
        this._currentPlayer = 0;
    }
    public AIEnvironnement(Board board){
        this._players = new ArrayList<>();
        this._startingPoint = new ArrayList<>();
        this._playerMarble = new ArrayList<>();

        this._grid = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this._grid[i][j] = -1;
            }
        }
        int numCurrentPlayer = 0;
        for(Player player : board.getPlayers()){
            this._players.add(numCurrentPlayer);
            ArrayList<Point> listMarble = new ArrayList<>();
            for(Marble marble: player.getMarbles()){
                Point pos = marble.getTile().getPosition();
                this._grid[pos.x][pos.y] = numCurrentPlayer;
                listMarble.add(pos);
            }
            this._playerMarble.add(listMarble);

            Tools.Direction direction = player.getStartPoint();
            if(direction == Tools.Direction.SO) {
                Point p = new Point(0,0);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.SE) {
                Point p = new Point(0,4);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.NO) {
                Point p = new Point(4,0);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.NE) {
                Point p = new Point(4,4);
                this._startingPoint.add(p);
            } else {
                System.out.println("AIEnvironnement constructor this direction is not handle yet : " + direction);
            }
        }

        this._currentPlayer = board.currentPlayer;
    }

    public void printAIEnvironnement(){
        System.out.println("Player list : ");
        for(int n : this._players){
            System.out.println("Numéro joueur : " + n);
        }
        System.out.println("Grille : ");
        for(int[] n : this._grid){
            for(int number: n){
                System.out.print(number);
            }
            System.out.println("");
        }
        System.out.println("Current player : " + this._currentPlayer);
    }

    public ArrayList<Integer> getPlayers(){
        return this._players;
    }

    public int getCurrentPlayer(){
        return this._currentPlayer;
    }

    public int[][] getGrid(){
        return this._grid;
    }

    public void addPlayer(int numPlayer){
        this._players.add(numPlayer);
    }

    public void setNumberInGrid(int x, int y, int value){
        this._grid[x][y] = value;
    }

    public void setCurrentPlayer(int numCurrentPlayer){
        this._currentPlayer = numCurrentPlayer;
    }

    public ArrayList<Point> getStartingPoint(){
        return this._startingPoint;
    }

    public void addStartingPoint(Point p){
        this._startingPoint.add(p);
    }

    public AIEnvironnement copy(){
        AIEnvironnement copyEnv = new AIEnvironnement();
        for(int player: getPlayers()){
            copyEnv.addPlayer(player);
        }
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                copyEnv.setNumberInGrid(i,j, getGrid()[i][j]);
            }
        }
        copyEnv.setCurrentPlayer(getCurrentPlayer());
        for(Point p: getStartingPoint()){
            Point startingPoint = new Point(p.x, p.y);
            copyEnv.addStartingPoint(startingPoint);
        }
        return copyEnv;

    }

    public ArrayList<ArrayList<Point>> coupsPossibles(){
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        listMove.addAll(marblesMoves());
        listMove.addAll(tilesMoves());
        return listMove;
    }

    private ArrayList<ArrayList<Point>> tilesMoves() {
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        ArrayList<Point> movableTiles = new ArrayList<>();

        //Pour chaque marble on ajoute sa ligne et sa colonne
        for(Point point: this._playerMarble.get(this._currentPlayer)) {
            //Point pos2 = ball.getTile().getPosition();
            //Tile tmpTile = ball.getTile();
            int tmpTileX = point.x;
            //int tmpTileX = tmpTile.getPosition().x;
            int tmpTileY = point.y;
            //int tmpTileY = tmpTile.getPosition().y;
            //Tile tileStudied = board.getGrid()[tmpTileX][0];
            Point pointStudied = new Point(tmpTileX, 0);
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: this._playerMarble){
                    if(playerMarble.contains(pointStudied)){
                        hasMarble = true;
                    }
                }
                if(!hasMarble){
                    movableTiles.add(pointStudied);
                    ArrayList<Point> move = new ArrayList<>();
                    move.add(null);
                    move.add(point);
                    move.add(pointStudied);
                }
            }
            /*if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Tools.Direction.N, player));
                }
            }*//*
            tileStudied = board.getGrid()[tmpTileX][4];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Tools.Direction.S, player));
                }
            }*/
            pointStudied = new Point(tmpTileX, 4);
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: this._playerMarble){
                    if(playerMarble.contains(pointStudied)){
                        hasMarble = true;
                    }
                }
                if(!hasMarble){
                    movableTiles.add(pointStudied);
                    ArrayList<Point> move = new ArrayList<>();

                    move.add(null);
                    move.add(point);
                    move.add(pointStudied);
                }
            }
            pointStudied = new Point(0, tmpTileY);
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: this._playerMarble){
                    if(playerMarble.contains(pointStudied)){
                        hasMarble = true;
                    }
                }
                if(!hasMarble){
                    movableTiles.add(pointStudied);
                    ArrayList<Point> move = new ArrayList<>();
                    move.add(null);
                    move.add(point);
                    move.add(pointStudied);
                }
            }
            pointStudied = new Point(4, tmpTileY);
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: this._playerMarble){
                    if(playerMarble.contains(pointStudied)){
                        hasMarble = true;
                    }
                }
                if(!hasMarble){
                    movableTiles.add(pointStudied);
                    ArrayList<Point> move = new ArrayList<>();
                    move.add(null);
                    move.add(point);
                    move.add(pointStudied);
                }
            }/*
            tileStudied = board.getGrid()[0][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Tools.Direction.O, player));
                }
            }
            tileStudied = board.getGrid()[4][tmpTileY];
            if (!movableTiles.contains(tileStudied)) {
                if (!tileStudied.hasMarble()) {
                    movableTiles.add(tileStudied);
                    moves.add(new Move(tileStudied.getPosition(), Tools.Direction.E, player));
                }
            }*/
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
        return listMove;
    }

    private ArrayList<ArrayList<Point>> marblesMoves(){
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        this._playerMarble.get(this._currentPlayer).forEach((pos) -> {
            //this.playerStart != Tools.Direction.NO
            if((pos.x != 4 && pos.y != 0) && isTileFree(add(pos, new Point(pos.x-1, pos.y-1)))) {
                ArrayList<Point> move = new ArrayList<>();
                move.add(pos);
                move.add(new Point(pos.x-1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.NE
            if((pos.x != 4 && pos.y != 4) && isTileFree(add(pos,new Point(pos.x+1, pos.y-1)))) {
                ArrayList<Point> move = new ArrayList<>();
                move.add(pos);
                move.add(new Point(pos.x+1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SE
            if((pos.x != 0 && pos.y != 4) && isTileFree(add(pos,new Point(pos.x+1, pos.y+1)))) {
                ArrayList<Point> move = new ArrayList<>();
                move.add(pos);
                move.add(new Point(pos.x+1, pos.y+1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SO
            if ((pos.x != 0 && pos.y != 0) && isTileFree(add(pos,new Point(pos.x-1, pos.y+1)))) {
                ArrayList<Point> move = new ArrayList<>();
                move.add(pos);
                move.add(new Point(pos.x-1, pos.y+1));
                listMove.add(move);
            }
        });
        return listMove;
    }

    public boolean isTileFree(Point p){
        if (p.x<0 || p.y < 0 || p.x > this._grid.length-1 || p.y > this._grid.length-1) {
            return false;
        }
        return !(this._grid[p.x][p.y] != -1);
    }

    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

}
