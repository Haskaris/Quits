package Model.Players;

import Global.Tools;
import Global.Tools.PlayerStatus;
import Model.Move;
import Model.MoveCalculator;
import Model.Support.Board;
import Model.Support.Marble;
import Model.Support.Tile;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;

public class HumanPlayer extends Player {

    private PlayerStatus status;

    public HumanPlayer(String name, Color color) {
        super(name, color);
        status = PlayerStatus.MarbleSelection;

    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {

        return coups_possibles.get(0);
    }

    /**
     * S'imprime dans la sortie stream
     *
     * @param stream
     * @throws IOException
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("HumanPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }

    public void Jouer(Board board, int column, int line) {
        Tile[][] grid = board.getGrid();

        if ((column >= 0 && column <= 4 && line >= 0 && line <= 4) && grid[column][line].hasMarble()
                && color.equals(grid[column][line].getMarble().getColor())) {
            //The players needs to select a marble

            board.resetAvailableTiles();
            board.allPotentialShifts.clear();
            board.availableTiles[column][line] = 1;
            board.selectedMarble = grid[column][line].getMarble();
            //List<Move> possibleMoves = new MoveCalculator(this).possibleMoves();
            List<Move> possibleMovesWithSource = new MoveCalculator(board).possibleMovesWithSource(column, line);
            for (Move m : possibleMovesWithSource) {
                m.Display();
                try {
                    Point pos = m.getPosition();
                    if (column == pos.x && line == pos.y) {//If the selected marble is the source of the available move seleted

                        Point dir = m.getCoordinatesDirection();

                        int x = pos.x + dir.x;
                        int y = pos.y + dir.y;
                        board.availableTiles[x][y] = 2;
                    }
                } catch (Exception e) {
                    //Here we handle the tile shifting
                    m.Display();
                    board.allPotentialShifts.add(m);

                }

            }
            board.diplayAvailableTiles();
            this.setStatus(Tools.PlayerStatus.ActionSelection);
        } else if (this.getStatus() == Tools.PlayerStatus.ActionSelection) {
            //The player selects a good move, else they are put back to MarbleSelection status
            if (!(column >= 0 && column <= 4 && line >= 0 && line <= 4)) {
                //We clicked on an arrow so we shift the rows or columns
                Tools.Direction d = Tools.Direction.NODIR;
                Point anchorSource = null;
                if (column == -1 && line >= 0 && line <= 4) {
                    //Shifting towards East
                    d = Tools.Direction.E;
                    anchorSource = new Point(0, line);

                } else if (column == 5 && line >= 0 && line <= 4) {
                    //Shifting towards West
                    d = Tools.Direction.W;
                    anchorSource = new Point(4, line);
                } else if (line == -1 && column >= 0 && column <= 4) {
                    //Shifting towards South
                    d = Tools.Direction.S;
                    anchorSource = new Point(column, 0);
                } else if (line == 5 && column >= 0 && column <= 4) {
                    //Shifting towards North
                    d = Tools.Direction.N;
                    anchorSource = new Point(column, 4);
                }
                board.moveLine(anchorSource, d);

                board.resetAvailableTiles();
                board.allPotentialShifts.clear();
                board.history.addToHistory(new Move(grid[anchorSource.x][anchorSource.y].getMarble(), d, this));
                this.setStatus(Tools.PlayerStatus.MarbleSelection);
                board.nextPlayer();
            } else {
                if (board.availableTiles[column][line] == 2) {
                    //That's a good action, we can move the marble to the new position
                    Point pos = board.selectedMarble.getPosition();
                    grid[column][line].addMarble(board.selectedMarble);
                    grid[pos.x][pos.y].removeMarble();
                    board.resetAvailableTiles();
                    board.allPotentialShifts.clear();
                    this.setStatus(Tools.PlayerStatus.MarbleSelection);

                    board.history.addToHistory(
                            new Move(board.selectedMarble,
                                    Tools.PointToDir(Tools.PointToPointDiff(pos, new Point(column, line))),
                                    this)
                    );

                    board.nextPlayer();
                } else {
                    //That's not a good action, we get back to MarbleSelection, but we don't change players
                    this.setStatus(Tools.PlayerStatus.MarbleSelection);
                    board.resetAvailableTiles();
                    board.allPotentialShifts.clear();
                }
            }
        }
    }

}
