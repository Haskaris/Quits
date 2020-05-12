package Model.Support;

import Controleur.Mediator;
import Global.Configuration;
import Global.Tools;
import Global.Tools.Direction;
import Model.MoveCalculator;
import Model.Move;
import Model.History;
import Model.Players.HumanPlayer;
import Model.ReaderWriter;
import Model.Players.Player;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private Tile[][] grid;
    private ArrayList<Player> players;
    public int currentPlayer;
    private History history;
    private Mediator mediator;
    private Tools.GameMode gameMode;
    public ArrayList<Move> allPotentialShifts;
    public Marble selectedMarble;
    public int[][] availableTiles;
    private int marbleEndObjectiv = 2;

    /**
     * Initialise un plateau Taille fixe pour le moment
     */
    public Board() {
        players = new ArrayList<>();

        grid = new Tile[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Tile(i, j);
            }
        }
        allPotentialShifts = new ArrayList<>();

        availableTiles = new int[5][5];
        resetAvailableTiles();

        history = new History(this);
        currentPlayer = 0;
    }

    /**
     * Initialise le jeu en fonction du mode de jeu
     */
    public void initFromGameMode() {
        switch (gameMode) {
            case TwoPlayersFiveBalls:
                this.players.get(0).setStartPoint(Tools.Direction.SW);
                this.grid[0][2].addMarble(this.players.get(0).addMarble());
                this.grid[2][4].addMarble(this.players.get(0).addMarble());
                this.players.get(1).setStartPoint(Tools.Direction.NE);
                this.grid[2][0].addMarble(this.players.get(1).addMarble());
                this.grid[4][2].addMarble(this.players.get(1).addMarble());
                break;
            case TwoPlayersThreeBalls:
                this.players.get(0).setStartPoint(Tools.Direction.SW);
                this.players.get(1).setStartPoint(Tools.Direction.NE);
                break;
            case FourPlayersFiveBalls:
                this.players.get(0).setStartPoint(Tools.Direction.SW);
                this.players.get(1).setStartPoint(Tools.Direction.NE);
                this.players.get(2).setStartPoint(Tools.Direction.NW);
                this.players.get(3).setStartPoint(Tools.Direction.SE);
                break;
        }
        for (Player p : this.players) {
            init3Marbles(p);
        }
    }

    /**
     * Positionne les 3 billes récurentes des joueurs en fonction de leur point
     * de départ. LE POINT DE DÉPART DOIT ÊTRE INITIALISÉ
     *
     * @param p Player
     */
    private void init3Marbles(Player p) {
        switch (p.getStartPoint()) {
            case SW:
                this.grid[0][3].addMarble(p.addMarble());
                this.grid[1][3].addMarble(p.addMarble());
                this.grid[1][4].addMarble(p.addMarble());
                break;
            case NE:
                this.grid[3][0].addMarble(p.addMarble());
                this.grid[3][1].addMarble(p.addMarble());
                this.grid[4][1].addMarble(p.addMarble());
                break;
            case NW:
                this.grid[1][0].addMarble(p.addMarble());
                this.grid[0][1].addMarble(p.addMarble());
                this.grid[1][1].addMarble(p.addMarble());
                break;
            case SE:
                this.grid[3][3].addMarble(p.addMarble());
                this.grid[4][3].addMarble(p.addMarble());
                this.grid[3][4].addMarble(p.addMarble());
                break;
        }
    }

    public void setGameMode(Tools.GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setMediator(Mediator m) {
        this.mediator = m;
    }

    public void playTurn(int column, int line) {
        if (getCurrentPlayer().getClass().equals(HumanPlayer.class)) {
            HumanPlayer player = (HumanPlayer) getCurrentPlayer();

            player.Jouer(this, column, line);

        } else {
            List<Move> possibleMoves = new MoveCalculator(this).possibleMoves();
            Move move = getCurrentPlayer().Jouer(possibleMoves);
            getHistory().doMove(move);
            nextPlayer();
        }
        this.mediator.getGraphicInterface().update();
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur
     * suivant
     */
    public boolean endTurn() {
        boolean isEnded = false;
        
        switch(getCurrentPlayer().getStartPoint()) {
            case SW:
                if (this.grid[4][0].hasMarble() && 
                        this.grid[4][0].getMarbleColor().equals(this.getCurrentPlayer().color)) {
                    this.getCurrentPlayer().removeMarble(this.grid[4][0].removeMarble());
                    System.out.println("DEL");
                }
                break;
            case NW:
                if (this.grid[4][4].hasMarble() && 
                        this.grid[4][4].getMarbleColor().equals(this.getCurrentPlayer().color)) {
                    this.getCurrentPlayer().removeMarble(this.grid[4][4].removeMarble());
                    System.out.println("DEL");
                }
                break;
            case NE:
                if (this.grid[0][4].hasMarble() && 
                        this.grid[0][4].getMarbleColor().equals(this.getCurrentPlayer().color)) {
                    this.getCurrentPlayer().removeMarble(this.grid[0][4].removeMarble());
                    System.out.println("DEL");
                }
                break;
            case SE:
                if (this.grid[0][0].hasMarble() && 
                        this.grid[0][0].getMarbleColor().equals(this.getCurrentPlayer().color)) {
                    this.getCurrentPlayer().removeMarble(this.grid[0][0].removeMarble());
                    System.out.println("DEL");
                }
                break;
        }
        
        isEnded = getCurrentPlayer().getMarbles().size() == marbleEndObjectiv;

        if (isEnded) {
            this.mediator.endGame();
            return false;
        }

        nextPlayer();
        return true;
    }

    /**
     * Deplace la marble précisée, dans la direction précisé.
     *
     * @param marble
     * @param direction
     */
    public void moveMarble(Marble marble, Tools.Direction direction) {
        Point startPoint = marble.getTile().getPosition();
        Point finishPoint = Tools.getNextPoint(startPoint, direction);
        grid[startPoint.x][startPoint.y].removeMarble();
        this.grid[finishPoint.x][finishPoint.y].addMarble(marble);
    }

    /**
     * DEPRECATED Mets à jours les positions des tuiles
     */
    private void updatePosition() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j].updatePosition(i, j);
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * Deplace la line précisée, dans le sens précisé.
     *
     * @param line
     * @param direction
     */
    public void moveLine(Point line, Tools.Direction direction) {
        Tile tmp = null;
        //Factoriser
        switch (direction) {
            case N:
                tmp = grid[line.x][0];
                for (int i = 0; i < grid.length - 1; i++) {
                    grid[line.x][i] = grid[line.x][i + 1];
                }
                grid[line.x][grid.length - 1] = tmp;
                break;
            case S:
                tmp = grid[line.x][grid.length - 1];
                for (int i = grid.length - 1; i > 0; i--) {
                    grid[line.x][i] = grid[line.x][i - 1];
                }
                grid[line.x][0] = tmp;
                break;
            case E:
                tmp = grid[grid.length - 1][line.y];
                for (int i = grid.length - 1; i > 0; i--) {
                    grid[i][line.y] = grid[i - 1][line.y];
                }
                grid[0][line.y] = tmp;
                break;
            case W:
                tmp = grid[0][line.y];
                for (int i = 0; i < grid.length - 1; i++) {
                    grid[i][line.y] = grid[i + 1][line.y];
                }
                grid[grid.length - 1][line.y] = tmp;
                break;
        }
        updatePosition();
    }

    /**
     * Place une bille aux coordonnées X Y
     *
     * @param b
     * @param x
     * @param y
     */
    public void placeMarbleOn(Marble b, int x, int y) {
        grid[x][y].addMarble(b);
    }

    /**
     * Retourne l'objet du joueur courant
     *
     * @return Player
     */
    public Player getCurrentPlayer() {
        return getPlayer(currentPlayer);
    }

    /**
     * Retourne la grille
     *
     * @return Tile[][]
     */

    //Plus simple si on utilise un arrayList non?
    /**
     * Ajoute un joueur à la liste des joueurs
     *
     * @param player
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Retourne le joueur à l'index désiré
     *
     * @param index Index du joueur souhaité
     * @return Joueur à l'index indiqué
     */
    public Player getPlayer(int index) {
        return this.players.get(index);
    }

    /**
     * Retourne la liste des joueurs
     *
     * @return Liste des joueurs du plateau
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * S'imprime dans la sortie stream
     *
     * @param stream
     * @throws IOException
     */
    public void print(OutputStream stream) throws IOException {
        //Je print le mode de jeu
        stream.write(this.gameMode.name().getBytes());
        
        stream.write(" ".getBytes());
        
        //Je print le joueur courant
        stream.write(String.valueOf(this.currentPlayer).getBytes());
        
        stream.write('\n');
        //Je print la couleur des tuiles
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.grid[i][j].print(stream);
            }
            stream.write('\n');
        }
    }

    /**
     * Charge le plateau à partir de l'entrée stream
     * 
     * @param in_stream
     * @throws IOException 
     */
    public void load(InputStream in_stream) throws IOException {
        String[] paramLine = ReaderWriter.readLine(in_stream).split(" ");
        this.gameMode = Tools.GameMode.valueOf(paramLine[0]);
        this.currentPlayer = Integer.parseInt(paramLine[1]);
        
        for (int i = 0; i < 5; i++) {
            String indexLine = ReaderWriter.readLine(in_stream);
            for (int j = 0; j < 5; j++) {
                int indexOfColor = Character.getNumericValue(indexLine.charAt(j));
                this.grid[i][j].setIndexOfColor(indexOfColor);
            }
        }
    }

    public void resetAvailableTiles() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                availableTiles[i][j] = 0;
            }
        }
    }

    /**
     * Move forward in the player list
     */
    public void nextPlayer() {
        currentPlayer++;
        if (currentPlayer >= players.size()) {
            currentPlayer = 0;
        }
    }
    
    /**
     * Move backward in the player list
     */
    public void previousPlayer() {
        currentPlayer--;
        if (currentPlayer < 0) {
            currentPlayer = players.size() - 1;
        }
    }

    /**
     * @return the history
     */
    public History getHistory() {
        return history;
    }

    public void reset() {
        //J'enlève toutes les billes du plateau
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if (this.grid[i][j].hasMarble()) {
                    this.grid[i][j].removeMarble();
                }
            }
        }
        
        //Je remets le joueur courant au début
        this.currentPlayer = 0;
    }
}
