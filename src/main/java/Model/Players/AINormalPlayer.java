package Model.Players;

import Model.AI.AI;
import Model.AI.Node;
import Model.Move;
import Model.Support.AIEnvironnement;
import Model.Support.Board;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class AINormalPlayer extends AI {

    public AINormalPlayer(String name, Color color, Board board) {
        super(name, color, board, 3);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, iaEnv.getCurrentPlayer());

        calculBestMove(getMaxDepth(), iaEnv, node);
        Move m = iaEnv.convertMove(node.getNodeMove(), this._board);
        System.out.println("IANORMAL");
        return m;
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AINormalPlayer".getBytes());
        stream.write('\n');
        super.print(stream);
    }
}
