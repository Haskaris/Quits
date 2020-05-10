package Model.Support;

import Global.Tools;
import Model.History;
import Model.Move;
import Model.Players.Player;

import javax.tools.Tool;
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
        this._startingPoint = new ArrayList<>();
        this._playerMarble = new ArrayList<>();

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
                Point p = new Point(0,4);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.SE) {
                Point p = new Point(0,0);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.NO) {
                Point p = new Point(4,4);
                this._startingPoint.add(p);
            } else if(direction == Tools.Direction.NE) {
                Point p = new Point(4,0);
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

    public ArrayList<ArrayList<Point>> getPlayerMarble(){
        return this._playerMarble;
    }

    public void addPlayerMarble(ArrayList<Point> playerMarble){
        this._playerMarble.add(playerMarble);
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
        for(ArrayList<Point> arrayPoint : getPlayerMarble()){
            ArrayList<Point> copyArrayPoint = new ArrayList<>();
            for(Point p: arrayPoint){
                copyArrayPoint.add(new Point(p.x, p.y));
            }
            copyEnv.addPlayerMarble(copyArrayPoint);
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
            //Tools.Direction.N
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
                    listMove.add(move);
                }
            }
            pointStudied = new Point(tmpTileX, 4);
            //Tools.Direction.S
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
                    listMove.add(move);
                }
            }
            pointStudied = new Point(0, tmpTileY);
            //Tools.Direction.O
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
                    listMove.add(move);
                }
            }
            pointStudied = new Point(4, tmpTileY);
            //Tools.Direction.E
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
                    listMove.add(move);
                }
            }
        }
        return listMove;
    }

    private ArrayList<ArrayList<Point>> marblesMoves(){
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        this._playerMarble.get(this._currentPlayer).forEach((pos) -> {
            //this.playerStart != Tools.Direction.NO
            //System.out.println("Pos x : " + this._startingPoint.get(this._currentPlayer).x + " pos y " + this._startingPoint.get(this._currentPlayer).y);
            if(!(this._startingPoint.get(this._currentPlayer).x == 4 && this._startingPoint.get(this._currentPlayer).y == 4) && isTileFree(new Point(pos.x-1, pos.y-1))) {
                ArrayList<Point> move = new ArrayList<>();
                //System.out.println("in NO");
                move.add(pos);
                move.add(new Point(pos.x-1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.NE
            if(!(this._startingPoint.get(this._currentPlayer).x == 4 && this._startingPoint.get(this._currentPlayer).y == 0) && isTileFree(new Point(pos.x+1, pos.y-1))) {
                ArrayList<Point> move = new ArrayList<>();
                //System.out.println("in NE");
                move.add(pos);
                move.add(new Point(pos.x+1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SE
            if(!(this._startingPoint.get(this._currentPlayer).x == 0 && this._startingPoint.get(this._currentPlayer).y == 0) && isTileFree(new Point(pos.x+1, pos.y+1))) {
                ArrayList<Point> move = new ArrayList<>();
                //System.out.println("in SE");
                move.add(pos);
                move.add(new Point(pos.x+1, pos.y+1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SO
            if (!(this._startingPoint.get(this._currentPlayer).x == 0 && this._startingPoint.get(this._currentPlayer).y == 4) && isTileFree(new Point(pos.x-1, pos.y+1))) {
                //System.out.println("in SO");
                ArrayList<Point> move = new ArrayList<>();
                move.add(pos);
                move.add(new Point(pos.x-1, pos.y+1));
                listMove.add(move);
            }
        });
        return listMove;
    }

    public boolean isTileFree(Point p){
        //System.out.println("IN");
        if (p.x<0 || p.y < 0 || p.x > this._grid.length-1 || p.y > this._grid.length-1) {
            //System.out.println("in is tile free false");
            return false;
        }
        //System.out.println(this._grid[p.x][p.y] + " x " + p.x + " y " + p.y);
        return !(this._grid[p.x][p.y] != -1);
    }

    public void moveLine(ArrayList<Point> move){
        //case same x
        if(move.get(1).x == move.get(2).x){
            //case move up move.get(2).y == 0
            if(move.get(2).y == 0){
                int tmp = this._grid[move.get(2).x][0];
                for (int i = 0; i < this._grid.length - 1; i++) {
                    //if marble on the last tile change in _playerMarble
                    if(this._grid[move.get(2).x][i+1] != -1){
                        //test if on goal for this marble
                        if(onGoalPlayer(new Point(move.get(2).x, i+1), this._grid[move.get(2).x][i+1])){
                            //remove from list player marble
                            this._playerMarble.get(this._grid[move.get(2).x][i+1]).remove(move.get(0));
                            this._grid[move.get(2).x][i] = -1;
                        } else {
                            for(Point point: this._playerMarble.get(this._grid[move.get(2).x][i+1])){
                                if(point.x == move.get(2).x && point.y == i+1){
                                    point.y = i;
                                }
                            }
                            this._grid[move.get(2).x][i] = this._grid[move.get(2).x][i+1];
                        }
                    } else {
                        this._grid[move.get(2).x][i] = this._grid[move.get(2).x][i+1];
                    }
                }
                this._grid[move.get(2).x][this._grid.length - 1] = tmp;
            } else {
                int tmp = this._grid[move.get(2).x][this._grid.length-1];
                for (int i = this._grid.length - 1; i > 0; i--) {
                    //if marble on the last tile change in _playerMarble
                    if(this._grid[move.get(2).x][i-1] != -1){
                        //test if on goal for this marble
                        if(onGoalPlayer(new Point(move.get(2).x, i-1), this._grid[move.get(2).x][i-1])){
                            //remove from list player marble
                            this._playerMarble.get(this._grid[move.get(2).x][i-1]).remove(move.get(0));
                            this._grid[move.get(2).x][i] = -1;
                        } else {
                            for(Point point: this._playerMarble.get(this._grid[move.get(2).x][i-1])){
                                if(point.x == move.get(2).x && point.y == i-1){
                                    point.y = i;
                                }
                            }
                            this._grid[move.get(2).x][i] = this._grid[move.get(2).x][i-1];
                        }
                    } else {
                        this._grid[move.get(2).x][i] = this._grid[move.get(2).x][i-1];
                    }
                }
                this._grid[move.get(2).x][0] = tmp;
            }
        } else {
            if(move.get(2).x == 0){
                int tmp = this._grid[0][move.get(2).y];
                for (int i = 0; i < this._grid.length - 1; i++) {
                    //if marble on the last tile change in _playerMarble
                    if(this._grid[i+1][move.get(2).y] != -1){
                        //test if on goal for this marble
                        if(onGoalPlayer(new Point(i+1, move.get(2).y), this._grid[i+1][move.get(2).y])){
                            //remove from list player marble
                            this._playerMarble.get(this._grid[i+1][move.get(2).y]).remove(move.get(0));
                            this._grid[i][move.get(2).y] = -1;
                        } else {
                            for(Point point: this._playerMarble.get(this._grid[i+1][move.get(2).y])){
                                if(point.x == i+1 && point.y == move.get(2).y){
                                    point.x = i;
                                }
                            }
                            this._grid[i][move.get(2).y] = this._grid[i+1][move.get(2).y];
                        }
                    } else {
                        this._grid[i][move.get(2).y] = this._grid[i+1][move.get(2).y];
                    }
                }
                this._grid[this._grid.length - 1][move.get(2).y] = tmp;
            } else {
                int tmp = this._grid[this._grid.length-1][move.get(2).y];
                for (int i = this._grid.length - 1; i > 0; i--) {
                    //if marble on the last tile change in _playerMarble
                    if(this._grid[i-1][move.get(2).y] != -1){
                        //test if on goal for this marble
                        if(onGoalPlayer(new Point(i-1, move.get(2).y), this._grid[i-1][move.get(2).y])){
                            //remove from list player marble
                            this._playerMarble.get(this._grid[i-1][move.get(2).y]).remove(move.get(0));
                            this._grid[i][move.get(2).y] = -1;
                        } else {
                            for(Point point: this._playerMarble.get(this._grid[i-1][move.get(2).y])){
                                if(point.x == i-1 && point.y == move.get(2).y){
                                    point.x = i;
                                }
                            }
                            this._grid[i][move.get(2).y] = this._grid[i-1][move.get(2).y];
                        }
                    } else {
                        this._grid[i][move.get(2).y] = this._grid[i-1][move.get(2).y];
                    }
                }
                this._grid[0][move.get(2).y] = tmp;
            }
        }
    }

    public void moveMarble(ArrayList<Point> move){
        int marble = this._grid[move.get(0).x][move.get(0).y];
        //System.out.println("before change point");
        this._grid[move.get(0).x][move.get(0).y] = -1;
        //test finish marble on goal
        if(onGoal(move.get(1))){
            //remove marble from marble player of current player
            this._playerMarble.get(this._currentPlayer).remove(move.get(0));
        } else {
            this._grid[move.get(1).x][move.get(1).y] = marble;

            //System.out.println("after change point");
            //System.out.println(this._playerMarble);
            //System.out.println(this._currentPlayer);
            for(Point point: this._playerMarble.get(this._currentPlayer)){
                //System.out.println("before change point in for statement " );
                //System.out.println("x "+ move.get(0).x + " y " + move.get(0).y);
                if(point.x == move.get(0).x && point.y == move.get(0).y){
                    point.x = move.get(1).x;
                    point.y = move.get(1).y;
                }
                //System.out.println("after  change point in for statement");
            }
            //System.out.println("after change point in list");
        }
    }

    public void perform(ArrayList<Point> move) {
        //move marble if first point not null
        if(move.get(0) != null){
            moveMarble(move);
        } else {
            moveLine(move);
        }
    }

    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

    public void nextPlayer(){
        if(this._players.size() == this._currentPlayer + 1){
            this._currentPlayer = 0;
        } else {
            this._currentPlayer++;
        }
    }

    public boolean onGoal(Point marble){
        Point goal = new Point(Math.abs(4-getStartingPoint().get(getCurrentPlayer()).x) , Math.abs(4-getStartingPoint().get(getCurrentPlayer()).y));
        if(goal.x == marble.x && goal.y == marble.y){
            return true;
        }
        return false;
    }

    public boolean onGoalPlayer(Point marble, int numPlayerMarble){
        Point goal = new Point(Math.abs(4-getStartingPoint().get(numPlayerMarble).x) , Math.abs(4-getStartingPoint().get(numPlayerMarble).y));
        if(goal.x == marble.x && goal.y == marble.y){
            return true;
        }
        return false;
    }

    public Move convertMove(ArrayList<Point> move, Board board){
        Move convertMove = null;
        //case move line
        if(move.get(0) == null){
            convertMove = new Move(move.get(1), convertDirectionLine(move.get(1), move.get(2)), board.currentPlayer());
        } else {
            Marble goodMarble = null;
            for(Marble m: board.currentPlayer().getMarbles()){
                if(m.getTile().getPosition().x == move.get(0).x && m.getTile().getPosition().y == move.get(0).y){
                    goodMarble = m;
                    break;
                }
            }
            convertMove = new Move(goodMarble, convertDirectionMarble(move.get(0), move.get(1)), board.currentPlayer());
        }
        return convertMove;
    }

    public Tools.Direction convertDirectionMarble(Point pMarble, Point pDir){
        int x = (pDir.x - pMarble.x);
        int y = (pDir.y - pMarble.y);
        if(x == -1){
            //case o
            if(y == 1){
                //case S
                return Tools.Direction.SO;
            } else {
                return Tools.Direction.NO;
            }
        } else {
            //case E
            if (y == 1){
                return Tools.Direction.SE;
            } else {
                return Tools.Direction.NE;
            }
        }
    }

    public Tools.Direction convertDirectionLine(Point pMarble, Point pDir){
        if(pMarble.x == pDir.x){
            if(pDir.y == 0){
                return Tools.Direction.N;
            } else {
                return Tools.Direction.S;
            }
        } else {
            if(pDir.x == 0){
                return Tools.Direction.O;
            } else {
                return Tools.Direction.E;
            }
        }
    }

}
