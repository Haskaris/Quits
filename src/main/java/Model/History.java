package Model;

import Model.Support.Board;

public class History {
    Board board;
    Move past;
    Move future;

    public History(Board board){
        this.board = board;
    }

    public void doMove(Move c){
        if(c == null) return; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        c.perform(board);
        c.nextMove = past;
        past = c;
        future = null;
    }
    
    public void addToHistory(Move c){
        if(c == null) return; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        c.nextMove = past;
        past = c;
        future = null;
    }

    public void undo(){
        if(isEmptyPast())return;
        Move c = past.nextMove;
        past.nextMove = null;
        future = past;
        past = c;
        future.cancel(board);
    }

    public void redo(){
        if(isEmptyFuture())return;
        Move c = future.nextMove;
        future.nextMove = null;
        past = future;
        future = c;
        past.perform(board);
    }

    public boolean isEmptyPast(){
        return past == null;
    }
    
    public boolean isEmptyFuture(){
        return future == null;
    }
    
    public Move lastMove(){
        return past;
    }
}
