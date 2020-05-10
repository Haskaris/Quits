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

    public int eval_func(AIEnvironnement iaEnv) {
        int sum = 0;
        Point goal = new Point(Math.abs(4-iaEnv.getStartingPoint().get(iaEnv.getCurrentPlayer()).x) , Math.abs(4-iaEnv.getStartingPoint().get(iaEnv.getCurrentPlayer()).y));

        //ArrayList<Marble> marbleList = board.getPlayers().get(board.currentPlayer).getMarbles();
        for(Point currentPoint: iaEnv.getPlayerMarble().get(iaEnv.getCurrentPlayer())){
            sum += (Math.sqrt(Math.pow(goal.x-currentPoint.x,2)+Math.pow(goal.y-currentPoint.y, 2)));
        }
        //System.out.println(sum);
        return sum;
    }

    public int calculBestMove(int depth, AIEnvironnement iaEnv, Node node) {
        if (depth == 0) {
            return eval_func(iaEnv);
        }

        ArrayList<ArrayList<Point>> listMove = iaEnv.coupsPossibles();
        int name = node._name;
        for(ArrayList<Point> move : listMove){
            name++;
            AIEnvironnement new_env = iaEnv.copy();

            new_env.perform(move);

            Node new_node = new Node(-1, null, node, null, Node.Node_type.MIN_NODE, name);
            node.setNodeChild(new_node);
            node.addNodeChild(new_node);


            int potential_value = calculBestMove(depth - 1, new_env, new_node);
            new_node.setNodeValue(potential_value);


            /*if (pruning(depth, node, potential_value)) {
                System.out.println("On élague");
                break;
            }*/

            if (node.getNodeType() == Node.Node_type.MAX_NODE) {
                if (potential_value < node.getNodeValue() || node.getNodeValue() == -1 ) {
                    node.setNodeValue(potential_value);
                    node.setNodeMove(move);
                    //System.out.println("Noeud max profondeur " + depth + " nom noeud " + node._name + " move final : " + move + " valeur " + potential_value);
                }


            } else {
                if (potential_value > node.getNodeValue() || node.getNodeValue() == -1) {
                    node.setNodeValue(potential_value);
                    node.setNodeMove(move);
                    //System.out.println("Noeud min profondeur " + depth + " nom noeud " + node._name + " move final : " + move + " valeur " + potential_value);
                }
            }
        }
        //System.out.println("Fin while");

        return node.getNodeValue();
    }

    public boolean pruning(int depth, Node node, int p_value) {
        // Enlève le nullpointer exception
        if (node.getNodeParent() == null) {
            return false;
        }

        boolean potential_test;

        int node_value = node.getNodeParent().getNodeValue();
        Node.Node_type pruning_type = node.getNodeType();

        if (pruning_type == Node.Node_type.MAX_NODE) {
            potential_test = node_value < p_value;
        } else {
            potential_test = node_value > p_value;
        }

        if (_max_depth - depth >= 2) {

            if (node_value != -1 && potential_test) {
                return true;
            }
        }
        return false;
    }
}
