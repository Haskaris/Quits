package Model;

import Global.Tools;
import Global.Tools.Direction;
import Model.Support.Board;
import Model.Support.Marble;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Classe permettant la gestion de l'historique
 *
 * @author Mathis
 */
public class History {

    private final Board board;

    private ArrayList<Move> historyMoves;
    private int indexHistory;

    /**
     * Constructeur
     *
     * @param board
     */
    public History(Board board) {
        this.board = board;
        historyMoves = new ArrayList<>();
        indexHistory = 0;
    }

    /**
     * Effectue le coup c et l'ajoute à l'historique
     *
     * @param c
     */
    public void doMove(Move c) {
        if (c == null) {
            return; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        }
        c.perform(board);
        this.addToHistory(c);
    }

    /**
     * Ajoute un coup à l'historique
     *
     * @param m
     */
    public void addToHistory(Move m) {

        //On commence par supprimer les mouvements suivants
        for (int i = historyMoves.size() - 1; i > indexHistory; i--) {
            historyMoves.remove(i);
        }

        //Ensuite on ajoute le nouveau mouvement à la fin de notre historique
        historyMoves.add(m);
        indexHistory++;

    }

    /**
     * Permet d'annuler la dernière action
     */
    public void undo() {
        if (isEmptyPast()) {
            return;
        }

        //J'annule le dernier mouvement, mais rien n'est supprimé pour pouvoir redo derrière si besoin
        indexHistory--;
        historyMoves.get(indexHistory).cancel(this.board);

        this.board.previousPlayer();
    }

    /**
     * Permet de refaire une action annulée
     */
    public void redo() {
        if (isEmptyFuture()) {
            return;
        }

        //Je refais le dernier mouvement
        historyMoves.get(indexHistory).perform(board);
        indexHistory++;

        this.board.nextPlayer();
    }

    public boolean isEmptyPast() {
        return indexHistory == 0;
    }

    public boolean isEmptyFuture() {
        return indexHistory == historyMoves.size();
    }

    /**
     * Retourne le dernier coup, null sinon
     *
     * @return
     */
    public Move lastMove() {
        if (isEmptyPast()) {
            return null;
        }
        return historyMoves.get(indexHistory - 1);
    }

    /////////////////////////////////  IO  /////////////////////////////////////
    public void print(OutputStream stream) throws IOException {
        // 2,3 NE;3,2 SW;0,4 W;2,4 S
        // 2
        for (int i = 0; i < historyMoves.size(); i++) {
            if (i != 0) {
                stream.write(';');
            }
            historyMoves.get(i).print(stream);

        }
        stream.write('\n');
        stream.write(String.valueOf(indexHistory).getBytes());
    }

    public void load(InputStream in_stream) throws IOException {
        // 2,3 NE;3,2 SW;0,4 W;2,4 S
        // 2
        String[] paramLine = ReaderWriter.readLine(in_stream).split(";");
        for (String s : paramLine) {
            historyMoves.add(Move.load(s));
        }
        indexHistory = Integer.valueOf(ReaderWriter.readLine(in_stream));

    }

    public void clear() {
        historyMoves.clear();
        indexHistory = 0;
    }

    public void display() {
        System.out.println("\nHISTORY :");
        for (int i = 0; i < historyMoves.size(); i++) {
            if(i == indexHistory) System.out.println(" <[]> ");
            historyMoves.get(i).display();
        }
    }

}
