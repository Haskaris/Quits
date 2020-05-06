package Model.Support;

import Global.Configuration;
import Global.Tools;
import Model.MoveCalculator;
import Model.Move;
import Model.History;
import Model.ReaderWriter;
import Model.Players.Player;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Board {
    private Tile[][] grid;
    public Player[] players;    //Le faire avec un arrayList est peut être mieux ?
    public int currentPlayer;
    public History history;
    
    private int maxPlayer = 0;

    /**
     * Initialise un plateau
     * Taille fixe pour le moment
     */
    public Board(){
        players = new Player[4];

        grid = new Tile[5][5];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grid[i][j] = new Tile(i, j);

        history = new History(this);
        currentPlayer = 0;
    }

    /**
     * Joue les tours de la partie. S'arrete à la fin
     * Plante l'ihm
     */
    public void playGame(){
        while(endRound()){
            List<Move> possiblesMoves = new MoveCalculator(this).coupsPossibles();
            Move coup = currentPlayer().Jouer(possiblesMoves);
            history.doMove(coup);
            //LecteurRedacteur.AffichePartie(this);
        }
    }

    /**
     * Clot un tour. Verifie les conditions de victoire et passe au joueur suivant
     */
    private boolean endRound(){
        boolean isEnded = currentPlayer().getMarbles().isEmpty();

        if(isEnded){
            System.out.println("Joueur " + currentPlayer + " a gagné");
            return false;
        }

        currentPlayer ++;
        //Ne marche pas car nos joueurs sont ajouté dynamiquement
        if(currentPlayer>=(Integer)Configuration.read("Joueurs"))
            currentPlayer =0;
        return true;
    }

    /**
     * Deplace la marble précisée, dans la direction précisé.
     * @param marble
     * @param direction
     */
    public void moveMarble(Marble marble, Tools.Direction direction){
        Point startPoint = marble.getTile().getPosition();
        Point finishPoint = Tools.getNextPoint(startPoint, direction);
        grid[startPoint.x][startPoint.y].removeMarble();
        grid[finishPoint.x][finishPoint.y].addMarble(marble);
    }
    
    /**
     * DEPRECATED
     * Mets à jours les positions des tuiles
     */
    private void updatePosition() {
        for(int i = 0; i <  5; i++) {
            for(int j = 0; j < 5; j++) {
                grid[i][j].updatePosition(i, j);
            }
        }
    }
    
    /**
     * Deplace la line précisée, dans le sens précisé.
     * @param line
     * @param direction
     */
    public void moveLine(Point line, Tools.Direction direction){
        Tile tmp = null;
        //Factoriser
        switch(direction) {
            case N:
                tmp = grid[0][line.y];
                for (int i = 0; i < grid.length - 1; i++) {
                    grid[i][line.y] = grid[i+1][line.y] ;
                }
                grid[grid.length-1][line.y] = tmp;
                break;
            case E:
                tmp = grid[line.x][grid.length - 1];
                for (int i = grid.length-1; i>0; i--) {
                    grid[line.x][i] = grid[line.x][i-1];
                }
                grid[line.x][0] = tmp;
                break;
            case S:
                tmp = grid[grid.length-1][line.y];
                for (int i = grid.length-1; i>0; i--) {
                    grid[i][line.y] = grid[i-1][line.y];
                }
                grid[0][line.y] = tmp;
                break;
            case O:
                tmp = grid[line.x][0];
                for (int i = 0; i < grid.length - 1; i++) {
                    grid[line.x][i] = grid[line.x][i+1] ;
                }
                grid[line.x][grid.length - 1] = tmp;
                break;
        }
        updatePosition();
    }

    /**
     * Place une bille aux coordonnées X Y
     * @param b
     * @param x
     * @param y
     */
    public void placeMarbleOn(Marble b, int x, int y){
        grid[x][y].addMarble(b);
    }

    /**
     * Retourne l'objet du joueur courant
     * @return Player
     */
    public Player currentPlayer(){
        return getPlayer(currentPlayer);
    }

    /**
     * Retourne la grille
     * @return Tile[][]
     */
    public Tile[][] getGrid(){
        return grid;
    }

    //Plus simple si on utilise un arrayList non?
    /**
     * Ajoute un joueur à la liste des joueurs
     * @param player 
     */
    public void addPlayer(Player player) {
        this.players[maxPlayer++] = player;
    }

    /**
     * Retourne le joueur à l'index désiré
     * @param index
     * @return Player
     */
    public Player getPlayer(int index) {
        return this.players[index];
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    public void print(OutputStream stream) throws IOException {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                this.grid[i][j].print(stream);
            }
            stream.write('\n');
        }
    }

    public void load(InputStream in_stream) throws IOException  {
        String[] metadonees = ReaderWriter.readLine(in_stream).split("\n");
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                int indexOfColor = Character.getNumericValue(metadonees[i].charAt(j));
                this.grid[i][j].setIndexOfColor(indexOfColor);
            }
        }
    }
}
