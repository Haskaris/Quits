package Model.Players;

import Model.AI.AI;
import Model.AI.Node;
import Model.Move;
import Model.Support.AIEnvironnement;
import Model.Support.Board;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

public class AIHardPlayer extends AI {

    public AIHardPlayer(String name, Color color, Board board) {
        super(name, color, board, 5);
    }
    @Override
    public Move Jouer(List<Move> coups_possibles) {
        return coups_possibles.get(0);
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AIHardPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }

    public ArrayList<Point> aiTrain(AIEnvironnement env) {
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, env.getCurrentPlayer());
        //iaEnv.printBoard();
        calculBestMove(getMaxDepth(), env, node);
        //System.out.println(env.getCurrentPlayer());
        //System.out.println(env.getStartingPoint());
        //System.out.println(node.getNodeMove());
        return node.getNodeMove();
    }
}
