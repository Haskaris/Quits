package Model.Players;

import Global.Tools;
import Model.AI.AI;
import Model.AI.NeuronalNetwork;
import Model.Move;
import Model.MoveCalculator;
import Model.Support.AIEnvironnement;
import Model.Support.Board;
import Model.Support.Marble;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AIHardPlayer2 extends AI {

    //for the result of neuronal network
    public float[] _result;
    public boolean _impossibleMove = false;

    public AIHardPlayer2(String name, Color color, Board board) {
        super(name, color, board, 2);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) throws IOException {
        //boolean impossibleMove = false;
        //AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        NeuronalNetwork nt = new NeuronalNetwork();

        AIEnvironnement env  = new AIEnvironnement(this._board);

        //joueur nnnetwork
        float[] result;
        //on crée les entrées
        float[] tInputs = new float[100];
        //On ajoute chaque bille du plateau dans les inputs
        int currentMarble = 0;
        for (ArrayList<Point> arrayMarble : env.getPlayerMarble()) {
            for (Point p : arrayMarble) {
                for (int k = 0; k < 5; k++) {
                    if (p.x == k) {
                        tInputs[currentMarble + k] = 1;
                    } else {
                        tInputs[currentMarble + k] = -1;
                    }
                    if (p.y == k) {
                        tInputs[currentMarble + k + 5] = 1;
                    } else {
                        tInputs[currentMarble + k + 5] = -1;
                    }
                }
                currentMarble += 10;
            }
        }
        result = nt.FeedForward(tInputs);
        ArrayList<Point> move = AIHardPlayer2.Jouer3(env, result);

        //env.perform(move);
        Move m = env.convertMove(move, this._board);

        return m;
    }

    public void setResult(float[] result){
        this._result = result;
    }

    static public ArrayList<Point> Jouer2(AIEnvironnement env, float[] result) {
        //boolean impossibleMove = false;
        //AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        ArrayList<Point> convertMove = null;
        if(result.length != 18 ){
            System.out.println("Problème taille renvoyée incorrecte");
        } else {
            int x = 0;
            float max_x = -1;
            //on prend la valeur de x
            for (int i = 0; i < 5; i++) {
                if (result[i] > max_x) {
                    x = i;
                    max_x = result[i];
                }
            }
            float max_y = -1;
            int y = 0;
            //on prend la valeur de y
            for (int i = 0; i < 5; i++) {
                if (result[i + 5] > max_y) {
                    y = i;
                    max_y = result[i + 5];
                }
            }
            Tools.Direction dir = Tools.Direction.N;
            float max_dir = -1;
            boolean isMarble = true;
            //on prend la direction
            for (int i = 0; i < 8; i++) {
                if (result[i + 10] > max_dir) {
                    if (i >= 4) {
                        isMarble = false;
                    }
                    dir = getDirectionByIndex(i);
                    max_dir = result[i + 10];
                }
            }
            Point goodMarble = null;
            for (Point m : env.getOnePlayerMarble(env.getCurrentPlayer())) {
                if (m.x == x && m.y == y) {
                    goodMarble = m;
                    break;
                }
            }
            if(isMarble){
                if(goodMarble != null) {
                    convertMove = new ArrayList<>();
                    convertMove.add(new Point(x, y));

                    switch(dir){
                        case NE: convertMove.add(new Point(x+1, y-1)); break;
                        case NW: convertMove.add(new Point(x-1, y-1)); break;
                        case SE: convertMove.add(new Point(x+1, y+1)); break;
                        case SW: convertMove.add(new Point(x-1, y+1)); break;
                    }
                    if(!env.coupsPossibles().contains(convertMove)){
                        convertMove = null;
                    }
                } else {
                    convertMove = null;
                    //this._impossibleMove = true;
                }
            } else {
                if(goodMarble != null) {
                    convertMove = new ArrayList<>();
                    convertMove.add(null);
                    convertMove.add(new Point(x, y) );
                    switch(dir){
                        case N: convertMove.add(new Point(x, 0)); break;
                        case S: convertMove.add(new Point(x, 4)); break;
                        case E: convertMove.add(new Point(4, y)); break;
                        case W: convertMove.add(new Point(0, y)); break;
                    }
                    if(!env.coupsPossibles().contains(convertMove)){
                        convertMove = null;
                    }
                } else {
                    convertMove = null;
                   // this._impossibleMove = true;
                }
            }
            //convertMove.Afficher();
        }
        return convertMove;
    }

    static public ArrayList<Point> Jouer3(AIEnvironnement env, float[] result) {
        //boolean impossibleMove = false;
        //AIEnvironnement iaEnv  = new AIEnvironnement(this._board);
        ArrayList<Point> convertMove = null;
        if(result.length != 1 ){
            System.out.println("Problème taille renvoyée incorrecte");
        } else {
            ArrayList<ArrayList<Point>> coupPossible = env.coupsPossibles();
            double interval = 2.000/coupPossible.size();
            int moveToPlay = 0;
            double startInterval = -1 + interval;
            while(startInterval < result[0]) {
                startInterval += interval;
                moveToPlay++;
            }
            convertMove = coupPossible.get(moveToPlay);
        }
        return convertMove;
    }

    public boolean getImpossibleMove(){
        return this._impossibleMove;
    }

    static public Tools.Direction getDirectionByIndex(int index){
        switch(index){
            case 0:
                return Tools.Direction.NE;
            case 1:
                return Tools.Direction.NW;
            case 2:
                return Tools.Direction.SE;
            case 3:
                return Tools.Direction.SW;
            case 4:
                return Tools.Direction.N;
            case 5:
                return Tools.Direction.S;
            case 6:
                return Tools.Direction.E;
            case 7:
                return Tools.Direction.W;
        }
        return Tools.Direction.NE;
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
