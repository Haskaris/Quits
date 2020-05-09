package Model.AI;

import Model.AI.Node;
import Model.Move;
import Model.Players.Player;
import Model.Support.Board;
import Model.MoveCalculator;

import java.awt.*;
import java.util.List;
import java.util.ListIterator;

public abstract class AI extends Player {
    //mettre en privé
    public Board _board;
    //mettre en privé
    public int _max_depth;

    public AI(String name, Color color, Board board, int max_depth) {
        super(name, color);
        this._board = board;
        this._max_depth = max_depth;
    }

    public int eval_func(Board board) {
        return 15;
    }

    public int calculBestMove(int depth, Board board, Node node) {
        if (depth == 0) {
            return eval_func(board);
        }
        /*
        if (_max_depth == depth) {
            node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE);
        }*/
        System.out.println("Move calculator : ");
        System.out.println(board);
        MoveCalculator move_calculator = new MoveCalculator(board);
        List<Move> _move_list =  move_calculator.coupsPossibles();

        ListIterator<Move> it = _move_list.listIterator();
        while(it.hasNext()) {
            Move move = it.next();
            Board new_board = board.clone();
            move.Afficher();
            move.perform(new_board);

            Node new_node = new Node(-1, null, node, null, Node.Node_type.MIN_NODE);
            node.setNodeChild(new_node);

            int potential_value = calculBestMove(depth - 1, new_board, new_node);
            new_node.setNodeValue(potential_value);

            //if (pruning(depth, node, potential_value)) break;
            /*
            if (node.getNodeType() == Node.Node_type.MAX_NODE) {
                if (potential_value > node.getNodeValue()) {
                    node.setNodeValue(potential_value);
                    node.setNodeMove(move);
                }

            } else {
                if (potential_value < node.getNodeValue() || node.getNodeValue() == -1) {
                    node.setNodeValue(potential_value);
                    node.setNodeMove(move);
                }
            }*/


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
