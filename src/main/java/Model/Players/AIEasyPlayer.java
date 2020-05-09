package Model.Players;

import Model.AI.AI;
import Model.AI.Node;
import Model.Move;
import Model.Support.Board;

import java.awt.Color;
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
        /*try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            System.out.println("Erreur d'attente de l'IA");
        }*/
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE);
        calculBestMove(2, _board, node);

        System.out.println(node.getNodeValue());
        System.out.println("move");
        node.getNodeMove().Afficher();
        System.out.println("fin move");
        return coups_possibles.get(new Random().nextInt(coups_possibles.size()));
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
