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
        AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        Node node = new Node(-1, null, null, null, Node.Node_type.MAX_NODE, 0, iaEnv.getCurrentPlayer());

        //iaEnv.printBoard();
        calculBestMove(getMaxDepth(), iaEnv, node);/*
        System.out.println(iaEnv.getCurrentPlayer());
        System.out.println(iaEnv.getStartingPoint());*/
        Move m = iaEnv.convertMove(node.getNodeMove(), this._board);
        /*System.out.println(m.getDirection());
        System.out.println(m.getPosition());*/
        return m;
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
