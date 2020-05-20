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
        super(name, color, board, 6);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, iaEnv.getCurrentPlayer());

        calculBestMove(getMaxDepth(), iaEnv, node);
        Move m = iaEnv.convertMove(node.getNodeMove(), this._board);
        System.out.println("IADIFFICILE");
        return m;
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AIHardPlayer".getBytes());
        stream.write('\n');
        super.print(stream);
    }

    public ArrayList<Point> aiTrain(AIEnvironnement env) {
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, env.getCurrentPlayer());
        calculBestMove(getMaxDepth(), env, node);
        return node.getNodeMove();
    }
}
