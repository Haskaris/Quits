package Model;

import Model.Support.Board;

/**
 * Classe permettant la gestion de l'historique
 * @author Mathis
 */
public class History {
    private final Board board;
    private Move past;
    private Move future;

    /**
     * Constructeur
     * @param board 
     */
    public History(Board board){
        this.board = board;
    }

    /**
     * Effectue le coup c et l'ajoute à l'historique
     * @param c 
     */
    public void doMove(Move c){
        if(c == null)
            return; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        c.perform(board);
        this.addToHistory(c);
    }
    
    /**
     * Ajoute un coup à l'historique
     * @param m
     */
    public void addToHistory(Move m) {
        //Le prochaine mouvement dans la liste chainée est mis à jour
        if (!isEmptyPast()) {
            this.past.nextMove = m;
            m.lastMove = this.past;
        }
        
        //Le dernier mouvement effectué est m
        this.past = m;
        
        //Future est null car on écrase le futur
        this.future = null;
    }

    /**
     * Permet d'anuller la dernière action
     */
    public void undo(){
        if(isEmptyPast())
            return;
        
        //J'annule le dernier mouvement
        this.past.cancel(this.board);
        
        //Le dernier mouvement devient donc mon nouveau futur
        this.future = past;
        
        //Le dernier mouvement avant ce mouvement, devient le nouveau passé
        this.past = this.past.lastMove;
        
        this.board.previousPlayer();
    }

    /**
     * Permet de refaire une action annulée
     */
    public void redo(){
        if(isEmptyFuture())
            return;
        
        //Je refais le dernier mouvement
        this.future.perform(board);
        
        //Le prochain mouvement est donc mon nouveau dernier mouvement
        this.past = this.future;
        
        //Le future mouvement est donc 
        this.future = this.future.nextMove;
        
        this.board.nextPlayer();
    }

    public boolean isEmptyPast(){
        return this.past == null;
    }
    
    public boolean isEmptyFuture(){
        return this.future == null;
    }
    
    /**
     * Retourne le dernier coup, null sinon
     * @return 
     */
    public Move lastMove(){
        return past;
    }
    
    
}
