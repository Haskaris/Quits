package Model.Support;

import Global.Tools;
import Model.Players.Player;
import Model.Move;

import javax.tools.Tool;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AIEnvironnement {
    /**
     * Contient le plateau actuel
     * -1 si la case est vide
     * un nombre >= 0 si la case contient une bille
     * le numéro de la bille correspond au numéro du joueur
     * */
    private int[][] _grid;
    /**
     * Liste de tous les joueurs
     * un numéro correspond à un joueur
     * */
    private ArrayList<Integer> _players;
    /**
     * Liste contenant toutes les billes d'un joueur
     * le joueur i peut accéder à sa liste de bille avec
     * _playerMarble.get(i)
     * */
    private ArrayList<ArrayList<Point>> _playerMarble;
    /**
     * Numéro du joueur courant
     * */
    public int _currentPlayer;
    /**
     * Liste contenant les points de départ des différents joueurs
     * le joueur i peut accéder à son point de départ avec
     * _startingPoint.get(i)
     * */
    private ArrayList<Point> _startingPoint;
    /**
     * Numéro du joueur correspondant à l'IA qui doit renvoyer le coup
     * (le joueur courant du Board actuel)
     * */
    private int _iaPlayer;

    /**
     * Initialise l'AIEnvironnement avec des paramètres vide
     * @return ArrayList<Integer>
     */
    public AIEnvironnement(){
        this._players = new ArrayList<>();
        this._grid = new int[5][5];
        //initialisation du plateau
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this._grid[i][j] = -1;
            }
        }
        this._currentPlayer = 0;
        this._iaPlayer = 0;
        this._startingPoint = new ArrayList<>();
        this._playerMarble = new ArrayList<>();

    }

    /**
     * Créé l'environnement de l'IA à partir du tableau de jeu actuel
     * @param board
     */
    public AIEnvironnement(Board board){
        this._players = new ArrayList<>();
        this._startingPoint = new ArrayList<>();
        this._playerMarble = new ArrayList<>();

        this._grid = new int[5][5];
        //initialisation du plateau
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this._grid[i][j] = -1;
            }
        }

        //On crée la liste des joueurs
        int numCurrentPlayer = 0;
        for(Player player : board.getPlayers()){
            addPlayer(numCurrentPlayer);
            //On crée la liste des billes du joueurs courant et on les ajoute au plateau
            ArrayList<Point> listMarble = new ArrayList<>();
            for(Marble marble: player.getMarbles()){
                Point pos = marble.getTile().getPosition();
                setNumberInGrid(pos.x, pos.y, numCurrentPlayer);
                listMarble.add(pos);
            }
            //on ajoute la liste dans la liste contenant toutes les billes de chaque joueurs
            addPlayerMarble(listMarble);

            //On ajoute le point de départ à la liste des points de départ des joueurs
            Tools.Direction direction = player.getStartPoint();
            Point p = null;
            if(direction == Tools.Direction.SO) {
                p = new Point(4,0);
            } else if(direction == Tools.Direction.SE) {
                p = new Point(4,4);
            } else if(direction == Tools.Direction.NO) {
                p = new Point(0,0);
            } else if(direction == Tools.Direction.NE) {
                p = new Point(0,4);
            } else {
                //Dans le cas ou le point de départ ne correspond à aucun point connu
                System.out.println("AIEnvironnement constructor this direction is not handle yet : " + direction);
            }
            addStartingPoint(p);
            numCurrentPlayer++;
        }

        setCurrentPlayer(board.currentPlayer);
        setIaPlayer(board.currentPlayer);
    }

    /**
     * Affiche l'environnment de l'IA
     */
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
        }
        System.out.println("Current player : " + this._currentPlayer);
    }

    /**
     * Renvoi la liste de tous les joeuurs
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getPlayers(){
        return this._players;
    }

    /**
     * Renvoi le numéro du joueur courant
     * @return int
     */
    public int getCurrentPlayer(){
        return this._currentPlayer;
    }

    /**
     * Renvoi le plateau
     * @return int[][]
     */
    public int[][] getGrid(){
        return this._grid;
    }

    /**
     * Ajoute un joueur à la liste des joueurs
     * @param numPlayer
     */
    public void addPlayer(int numPlayer){
        this._players.add(numPlayer);
    }

    /**
     * Change la valeur d'une case du plateau
     * @param x
     * @param x
     * @param value
     */
    public void setNumberInGrid(int x, int y, int value){
        this._grid[x][y] = value;
    }

    /**
     * Change le joueur courant
     * @param numCurrentPlayer
     */
    public void setCurrentPlayer(int numCurrentPlayer){
        this._currentPlayer = numCurrentPlayer;
    }

    /**
     * Renvoi la liste des points de départ
     * @return ArrayList<Point>
     */
    public ArrayList<Point> getStartingPoint(){
        return this._startingPoint;
    }

    /**
     * Retourne le point de départ du joueur donné
     * @param player
     * @return Point
     * */
    public Point getOnePlayerStartingPoint(int player) {
        return getStartingPoint().get(player);
    }

    /**
     * Ajoute un point de départ dans la liste _startingPoint
     * @param p
     */
    public void addStartingPoint(Point p){
        this._startingPoint.add(p);
    }

    /**
     * Renvoi la liste de toutes les billes de tous les joueurs
     * @return ArrayList<ArrayList<Point>>
     */
    public ArrayList<ArrayList<Point>> getPlayerMarble(){
        return this._playerMarble;
    }

    /**
     * Ajoute une liste de bille à la liste des billes des joueurs
     * @param playerMarble
     */
    public void addPlayerMarble(ArrayList<Point> playerMarble){
        this._playerMarble.add(playerMarble);
        int num = this._playerMarble.size() -1 ;
        for(Point p: playerMarble){
            setNumberInGrid(p.x, p.y, num);
        }
    }

    /**
     * Renvoi un objet AIEnvironnement qui est une copie de l'AIEnvironnement appelant cette méthode
     * @return AIEnvironnement
     */
    public AIEnvironnement copy(){
        AIEnvironnement copyEnv = new AIEnvironnement();
        //on copie la liste des joueurs dans le nouvel environnement
        for(int player: getPlayers()){
            copyEnv.addPlayer(player);
        }
        //On copie le plateau dans le nouvel environnement
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                copyEnv.setNumberInGrid(i,j, getGrid()[i][j]);
            }
        }
        //On copie le joueur courant
        copyEnv.setCurrentPlayer(getCurrentPlayer());

        //On copie l'IA de départ
        copyEnv.setIaPlayer(getIaPlayer());

        //On copie les points de départ
        for(Point p: getStartingPoint()){
            Point startingPoint = new Point(p.x, p.y);
            copyEnv.addStartingPoint(startingPoint);

        }
        //On copie les billes de chaque joueur
        for(ArrayList<Point> arrayPoint : getPlayerMarble()){
            ArrayList<Point> copyArrayPoint = new ArrayList<>();
            for(Point p: arrayPoint){
                copyArrayPoint.add(new Point(p.x, p.y));
            }
            copyEnv.addPlayerMarble(copyArrayPoint);
        }

        return copyEnv;

    }

    /**
     * Change la valeur de l'attribut _iaPlayer
     * @param iaPlayer
     */
    public void setIaPlayer(int iaPlayer){
        this._iaPlayer = iaPlayer;
    }

    /**
     * Renvoi la valeur du joueur ia
     * @return int
     */
    public int getIaPlayer(){
        return this._iaPlayer;
    }

    /**
     * Renvoi la liste de tous les coups possibles
     * @return ArrayList<ArrayListe<Point>>
     */
    public ArrayList<ArrayList<Point>> coupsPossibles(){
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        listMove.addAll(marblesMoves());
        listMove.addAll(tilesMoves());
        return listMove;
    }

    /**
     * Renvoi la liste des billes d'un joueur
     * @param player
     * @return ArrayListe<Point>
     */
    public ArrayList<Point> getOnePlayerMarble(int player){
        return getPlayerMarble().get(player);
    }

    /**
     * Ajoute un coup si il est possible
     * @param listMove
     * @param movableTiles
     * @param pointStudied
     * @param point
     */
    private void addMoveTiles(ArrayList<ArrayList<Point>> listMove, ArrayList<Point> movableTiles, Point pointStudied, Point point){
        if(!movableTiles.contains(pointStudied)){
            boolean hasMarble = false;
            for(ArrayList<Point> playerMarble: getPlayerMarble()){
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

    /**
     * Renvoi la liste de tous les déplacements de billes possibles
     * @return ArrayList<ArrayListe<Point>>
     */
    private ArrayList<ArrayList<Point>> tilesMoves() {
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        ArrayList<Point> movableTiles = new ArrayList<>();

        //Pour chaque marble on ajoute sa ligne et sa colonne
        for(Point point: getOnePlayerMarble(getCurrentPlayer())){
            int tmpTileX = point.x;
            int tmpTileY = point.y;

            Point pointStudied = new Point(tmpTileX, 0);
            //Tools.Direction.N

            /*if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: getPlayerMarble()){
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
            }*/
            addMoveTiles(listMove, movableTiles, pointStudied, point);

            pointStudied = new Point(tmpTileX, 4);
            //Tools.Direction.S
            addMoveTiles(listMove, movableTiles, pointStudied, point);
            /*if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: getPlayerMarble()){
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
            }*/
            pointStudied = new Point(0, tmpTileY);
            //Tools.Direction.O
            addMoveTiles(listMove, movableTiles, pointStudied, point);
            /*
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: getPlayerMarble()){
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
            }*/
            pointStudied = new Point(4, tmpTileY);
            //Tools.Direction.E
            addMoveTiles(listMove, movableTiles, pointStudied, point);
            /*
            if(!movableTiles.contains(pointStudied)){
                boolean hasMarble = false;
                for(ArrayList<Point> playerMarble: getPlayerMarble()){
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
            }*/
        }
        return listMove;
    }

    /**
     * Ajoute les points dans le move courant
     * @param move
     * @param pos
     * @param direction
     */
    private void addPointInMove(ArrayList<Point> move, Point pos, Point direction){
        move.add(pos);
        move.add(direction);
    }

    /**
     * Renvoi la liste de tous les déplacements de tuiles possibles
     * @return ArrayList<ArrayListe<Point>>
     */
    private ArrayList<ArrayList<Point>> marblesMoves(){
        ArrayList<ArrayList<Point>> listMove = new ArrayList<>();
        getOnePlayerMarble(getCurrentPlayer()).forEach((pos) -> {
            //this.playerStart != Tools.Direction.NO
            if(!(getOnePlayerStartingPoint(getCurrentPlayer()).x == 4 && getOnePlayerStartingPoint(getCurrentPlayer()).y == 4) && isTileFree(new Point(pos.x-1, pos.y-1))) {
                ArrayList<Point> move = new ArrayList<>();
                addPointInMove(move, pos, new Point(pos.x-1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.NE
            if(!(getOnePlayerStartingPoint(getCurrentPlayer()).x == 4 && getOnePlayerStartingPoint(getCurrentPlayer()).y == 0) && isTileFree(new Point(pos.x+1, pos.y-1))) {
                ArrayList<Point> move = new ArrayList<>();
                addPointInMove(move, pos, new Point(pos.x+1, pos.y-1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SE
            if(!(getOnePlayerStartingPoint(getCurrentPlayer()).x == 0 && getOnePlayerStartingPoint(getCurrentPlayer()).y == 0) && isTileFree(new Point(pos.x+1, pos.y+1))) {
                ArrayList<Point> move = new ArrayList<>();
                addPointInMove(move, pos, new Point(pos.x+1, pos.y+1));
                listMove.add(move);
            }
            //this.playerStart != Tools.Direction.SO
            if (!(getOnePlayerStartingPoint(getCurrentPlayer()).x == 0 && getOnePlayerStartingPoint(getCurrentPlayer()).y == 4) && isTileFree(new Point(pos.x-1, pos.y+1))) {
                ArrayList<Point> move = new ArrayList<>();
                addPointInMove(move, pos, new Point(pos.x-1, pos.y+1));
                listMove.add(move);
            }
        });
        return listMove;
    }

    /**
     * Retourne la taille du plateau de jeu
     * @return int
     */
    private int getGridLength(){
        return this._grid.length;
    }

    /**
     * Renvoi vrai si la case du plateau ne contient pas de bille
     * @param p
     * @return boolean
     */
    public boolean isTileFree(Point p){
        if (p.x<0 || p.y < 0 || p.x > getGridLength()-1 || p.y > getGridLength()-1) {
            return false;
        }
        return this._grid[p.x][p.y] == -1;
    }

    //Je ne vois pas comment factoriser cette fonction
    /**
     * Joue un déplacement de tuile
     * @param move
     */
    private void moveLine(ArrayList<Point> move){
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

    /**
     * Joue un déplacement de bille
     * @param move
     */
    private void moveMarble(ArrayList<Point> move){
        int marble = this._grid[move.get(0).x][move.get(0).y];
        setNumberInGrid(move.get(0).x, move.get(0).y, -1);
        //test finish marble on goal
        if(onGoal(move.get(1))){
            //remove marble from marble player of current player
            this._playerMarble.get(this._currentPlayer).remove(move.get(0));
        } else {
            setNumberInGrid(move.get(1).x, move.get(1).y, marble);
            for(Point point: getOnePlayerMarble(getCurrentPlayer())){
                if(point.x == move.get(0).x && point.y == move.get(0).y){
                    point.x = move.get(1).x;
                    point.y = move.get(1).y;
                }
            }
        }
    }

    public boolean playerWin(){
        if(getOnePlayerMarble(getCurrentPlayer()).size() == 2){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Joue un déplacement
     * @param move
     */
    public void perform(ArrayList<Point> move) {
        //move marble if first point not null
        if(move.get(0) != null){
            moveMarble(move);
        } else {
            moveLine(move);
        }
    }

    /**
     * Renvoi un point correspondant à l'addition de coordonnée de 2 points
     * @param a
     * @param b
     * @return Point
     */
    private Point add(Point a, Point b){
        return new Point(a.x+b.x,a.y+b.y);
    }

    /**
     * Change la valeur du joueur courant pour passer au joueur suivant
     */
    public void nextPlayer(){
        if(this._players.size() == this._currentPlayer + 1){
            setCurrentPlayer(0);
        } else {
            setCurrentPlayer(getCurrentPlayer()+1);
        }
    }

    /**
     * Retourne vrai si la bille se trouve sur le but du joueur courant
     * @param marble
     * @return boolean
     */
    public boolean onGoal(Point marble){
        Point goal = new Point(Math.abs(4-getStartingPoint().get(getCurrentPlayer()).x) , Math.abs(4-getStartingPoint().get(getCurrentPlayer()).y));
        if(goal.x == marble.x && goal.y == marble.y){
            return true;
        }
        return false;
    }

    /**
     * Renvoi vrai si la bille se trouve sur le but du joueur indiqué
     * @param marble
     * @param numPlayerMarble
     * @return boolean
     */
    public boolean onGoalPlayer(Point marble, int numPlayerMarble){
        Point goal = new Point(Math.abs(4-getStartingPoint().get(numPlayerMarble).x) , Math.abs(4-getStartingPoint().get(numPlayerMarble).y));
        if(goal.x == marble.x && goal.y == marble.y){
            return true;
        }
        return false;
    }

    /**
     * Renvoi le mouvement converti en Move pour le board
     * @param move
     * @param board
     * @return Move
     */
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

    /**
     * Renvoi la direction pour une bille (NE, SE, SE, SO)
     * @param pMarble
     * @param pDir
     * @return Tools.Direction
     */
    private Tools.Direction convertDirectionMarble(Point pMarble, Point pDir){
        int x = (pDir.x - pMarble.x);
        int y = (pDir.y - pMarble.y);
        if(x == -1){
            //case o
            if(y == 1){
                //case S
                return Tools.Direction.NE;
            } else {
                return Tools.Direction.NO;
            }
        } else {
            //case E
            if (y == 1){
                return Tools.Direction.SE;
            } else {
                return Tools.Direction.SO;
            }
        }
    }

    /**
     * Renvoi la direction pour une tuile (N,S,E,O)
     * @param pMarble
     * @param pDir
     * @return Tools.Direction
     */
    private Tools.Direction convertDirectionLine(Point pMarble, Point pDir){
        //1 1
        //1 4
        if(pMarble.x == pDir.x){
            if(pDir.y == 0){
                return Tools.Direction.O;
            } else {
                return Tools.Direction.E;
            }
        } else {
            if(pDir.x == 0){
                return Tools.Direction.N;
            } else {
                return Tools.Direction.S;
            }
        }
    }

    /**
     * Affiche le plateau actuel
     */
    public void printBoard(){
        for(int i = 0; i < getGridLength(); i++){
            for(int j = 0; j < this._grid[i].length; j++){
                System.out.print(this._grid[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

}
