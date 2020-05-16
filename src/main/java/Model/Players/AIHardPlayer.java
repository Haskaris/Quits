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
import java.util.concurrent.TimeUnit;

public class AIHardPlayer extends AI {

    public AIHardPlayer(String name, Color color, Board board) {
        super(name, color, board, 1);
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
        System.out.println(iaEnv.getOnePlayerStartingPoint(iaEnv.getIaPlayer()));
        calculBestMove(2, iaEnv, node);
        node.printTree();
        System.out.println(node.getNodeMove());
        System.out.println(node.getNodeValue());
        return iaEnv.convertMove(node.getNodeMove(), this._board);
    }

    public ArrayList<Point> Jouer2(AIEnvironnement iaEnv) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            System.out.println("Erreur d'attente de l'IA");
        }
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, iaEnv.getCurrentPlayer());

        calculBestMove(1, iaEnv, node);
        return node.getNodeMove();
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
}
