package Model.AI;

import Model.AI.Node;
import Model.Move;
import Model.Players.Player;
import Model.Support.AIEnvironnement;
import Model.Support.Board;
import Model.MoveCalculator;
import Model.Support.Marble;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class AI extends Player {
    //mettre en privé
    public Board _board;
    //mettre en privé
    public int _max_depth;
    public AIEnvironnement _env;

    public AI(String name, Color color, Board board, int max_depth) {
        super(name, color);
        this._board = board;
        this._max_depth = max_depth;
    }

    public int eval_func(Board board) {
        ArrayList<Marble> marbleList = board.getPlayers().get(board.currentPlayer).getMarbles();
        int somme = 0;
        for(Marble marble: marbleList){
            Point currentPoint = marble.getTile().getPosition();
            somme += (Math.sqrt(Math.pow(4-currentPoint.x,2)+Math.pow(4-currentPoint.y, 2)));
        }
        System.out.println(somme);
        return somme;
    }

    public int calculBestMove(int depth, Board board, Node node) {
        if (depth == 0) {
            return eval_func(board);
        }
        if (_max_depth == depth) {
            this._env = new AIEnvironnement(board);
            this._env.printAIEnvironnement();
            //node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE);
        }
        System.out.println("Profondeur : " + depth);
        //System.out.println("Move calculator : ");
        MoveCalculator move_calculator = new MoveCalculator(board);
        List<Move> _move_list =  move_calculator.coupsPossibles();
        System.out.println("Affichage coups possibles");
        for(Move m: _move_list){
            m.Afficher();
        }
        System.out.println("fin coups possibles");



        //ListIterator<Move> it = _move_list.listIterator();
        int i = 0;
        while(i < _move_list.size()){
        //for(Move move : _move_list){
            Move save_move = _move_list.get(i).copySpe();
            //Cope spe ne marche pas pour jouer le move
            Move move = _move_list.get(i).copy();
            move.Afficher();
            Board new_board = board.copy();

            move.perform(new_board);
            move.Afficher();

            Node new_node = new Node(-1, null, node, null, Node.Node_type.MIN_NODE);
            node.setNodeChild(new_node);

            int potential_value = calculBestMove(depth - 1, new_board, new_node);
            new_node.setNodeValue(potential_value);

            //if (pruning(depth, node, potential_value)) break;

            if (node.getNodeType() == Node.Node_type.MAX_NODE) {
                System.out.println("potential value " + potential_value + " current value : "+ node.getNodeValue()) ;
                if (potential_value < node.getNodeValue() || node.getNodeValue() == -1 ) {
                    node.setNodeValue(potential_value);
                    System.out.println("depth : " + depth + " valeur : " + potential_value + " noeud : ");
                    save_move.Afficher();
                    node.setNodeMove(save_move);
                }

            } else {
                if (potential_value > node.getNodeValue() || node.getNodeValue() == -1) {
                    node.setNodeValue(potential_value);
                    node.setNodeMove(save_move);
                }
            }
            i++;
        }
        System.out.println("Fin while");

        return node.getNodeValue();
    }

    public boolean pruning(int depth, Node node, int p_value) {

        boolean potential_test;
        int node_value = node.getNodeParent().getNodeValue();
        Node.Node_type pruning_type = node.getNodeType();

        if (pruning_type == Node.Node_type.MAX_NODE) {
            potential_test = node_value > p_value;
        } else {
            potential_test = node_value < p_value;
        }

        if (_max_depth - depth >= 2) {

            if (node_value != -1 && potential_test) {
                return true;
            }
        }
        return false;
    }
}
