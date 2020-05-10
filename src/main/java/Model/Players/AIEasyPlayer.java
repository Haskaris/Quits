package Model.Players;

import Model.AI.AI;
import Model.AI.Node;
import Model.Move;
import Model.Support.AIEnvironnement;
import Model.Support.Board;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AIEasyPlayer extends AI {

    public AIEasyPlayer(String name, Color color, Board board) {
        super(name, color, board, 2);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            System.out.println("Erreur d'attente de l'IA");
        }
        AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, iaEnv.getCurrentPlayer());

        iaEnv.printBoard();
        System.out.println(iaEnv.getPlayers());
        calculBestMove(2, iaEnv, node);
        //node.printTree();

        return iaEnv.convertMove(node.getNodeMove(), this._board);
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AIEasyPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }
}
