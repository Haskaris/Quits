package Model.AI;

import Global.Tools;
import Model.Players.AIEasyPlayer;
import Model.Players.AIHardPlayer;
import Model.Players.AIHardPlayer2;
import Model.Players.Player;
import Model.Support.AIEnvironnement;
import Model.Support.Board;
import Model.Support.Marble;


import java.awt.*;
import java.util.ArrayList;

/**
 * But de la classe gérer la création de réseaux de neuronnes et
 * les faire évoluer avec un algorithme génétique
 * */
public class NNManager {
    public Board _board;
    public ArrayList<AIHardPlayer2> _listAIPlayers = null; //liste des IA de notre génération
    //private boolean _isTraning = false;              //à voir si utile
    public int _populationSize = 30;			        //Nombre d'agent a faire spawner
    //public float timer = 15f;				        //Temps par generation
    private int _generationNumber = 0;		        //Numero de la generation actuelle

    private ArrayList<NeuronalNetwork> _nets;		//Liste de neural networks de generation x
    private ArrayList<NeuronalNetwork> _newNets;	    //Liste de neural networks de generation x+1

    private int[] _layers = new int[] { 100, 10, 10, 10, 1 }; //Dimension de nos réseaux de neurones


    //private float fit=0;					        //On calculera la moyenne de fitnes de la generation grace a cette variable

    public float evalAI(AIEnvironnement iaEnv) {
        //with float we can do 1/current number for other player and choose de good one
        //prendre en compte toutes les billes au cas ou elle gagne
        if(iaEnv.playerWin()){
            return 0;
        }
        float sum = 0;
        Point goal = new Point(Math.abs(4-iaEnv.getStartingPoint().get(iaEnv.getIaPlayer()).x) , Math.abs(4-iaEnv.getStartingPoint().get(iaEnv.getIaPlayer()).y));

        //ArrayList<Marble> marbleList = board.getPlayers().get(board.currentPlayer).getMarbles();
        //sum for the AI player
        for(Point currentPoint: iaEnv.getPlayerMarble().get(iaEnv.getIaPlayer())){
            sum += (Math.sqrt(Math.pow(goal.x-currentPoint.x,2)+Math.pow(goal.y-currentPoint.y, 2)));
        }
        //sum for other player
        for(int i = 0; i < iaEnv.getPlayerMarble().size(); i++){
            if(i == iaEnv.getIaPlayer()){
                continue;
            } else {
                goal.x = Math.abs(4-iaEnv.getStartingPoint().get(i).x);
                goal.y = Math.abs(4-iaEnv.getStartingPoint().get(i).y);
                for(Point currentPoint: iaEnv.getPlayerMarble().get(i)){
                    //on ajoute l'opposé car on veut que les autres joueur soient loin de gagner
                    sum += 1/((Math.sqrt(Math.pow(goal.x - currentPoint.x,2) + Math.pow(goal.y - currentPoint.y, 2))));
                }
            }
        }

        //System.out.println(sum);
        return sum;
    }

    //Instancie toutes nos IA
    private void createAIBodies()
    {
        if(this._listAIPlayers != null) {
            //Detruit toutes nos aneciennes instances
            for (int i = 0; i < this._listAIPlayers.size(); i++) {
                this._listAIPlayers.remove(i);
            }
        }
        //Crée une generation de d'IA
        this._listAIPlayers = new ArrayList<>();
        for (int i = 0; i < this._populationSize; i++)
        {
            AIHardPlayer2 ai = new AIHardPlayer2(Integer.toString(i), Color.BLUE, this._board);
            this._listAIPlayers.add(ai);
        }
    }

    // Initialise notre liste d'agent
    void initNeuralNetworks()
    {
        this._nets = new ArrayList<NeuronalNetwork>();

        for (int i = 0; i < this._populationSize; i++)
        {
            NeuronalNetwork net = new NeuronalNetwork(this._layers);
            net.Mutate(0.5f);
            this._nets.add(net);
        }
    }
    public NNManager(){
        this._board = null;
        initNeuralNetworks();
        createAIBodies();
        trainAI();
    }

    public void trainAI() {
        System.out.println("Start training ");
        //int nbTour = 0;
        while (this._generationNumber < 100) {
            ArrayList<NeuronalNetwork> winnerAI = new ArrayList<>();
            //Faire jouer les IA instanciées entre elles;
            //Vérifier qu'elle ne joue pas de coup invalide
            //..

            for (int i = 0; i < this._populationSize; i ++) {
                //On fait jouer 2 IA, celle qui gagne est ajouté à la liste winner IA on joue dans AIEnvironnement
                AIEnvironnement env = new AIEnvironnement();
                //On ajoute les 2 joueurs
                env.addPlayer(0);
                env.addPlayer(1);
                //On met le joueur courant à 1
                env.setCurrentPlayer(1);
                //On ajoute les points de départs des 2 joueurs
                /**
                 * Joueur 0 et 1
                 * - - 1 1 -
                 * - - - 1 1
                 * 0 - - - 1
                 * 0 0 - - -
                 * - 0 0 - -
                 * */
                env.addStartingPoint(new Point(4, 0));
                env.addStartingPoint(new Point(0, 4));
                //On ajoute les billes
                //joueur 0
                ArrayList<Point> j0Marble = new ArrayList<>();
                j0Marble.add(new Point(2, 0));
                j0Marble.add(new Point(3, 0));
                j0Marble.add(new Point(3, 1));
                j0Marble.add(new Point(4, 1));
                j0Marble.add(new Point(4, 2));
                env.addPlayerMarble(j0Marble);
                //joueur 1 qui est une IA facile
                ArrayList<Point> j1Marble = new ArrayList<>();
                j1Marble.add(new Point(0, 2));
                j1Marble.add(new Point(0, 3));
                j1Marble.add(new Point(1, 3));
                j1Marble.add(new Point(1, 4));
                j1Marble.add(new Point(2, 4));
                env.addPlayerMarble(j1Marble);
                AIEasyPlayer aiEasy = new AIEasyPlayer("default", Color.BLACK, null);
                //env.printBoard();
                //On joue la partie jusqu'à ce qu'on aie un gagnant
                //nbTour = 0;
                int numberOfMarble = 10;
                boolean aiTrainTurn = true;
                while (!env.playerWin()) {
                    //on change de joueur courant
                    env.nextPlayer();
                    if(aiTrainTurn) {
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
                                    if (p.y == i) {
                                        tInputs[currentMarble + k + 5] = 1;
                                    } else {
                                        tInputs[currentMarble + k + 5] = -1;
                                    }
                                }
                                currentMarble += 10;
                            }
                        }
                        result = this._nets.get(i).FeedForward(tInputs);
                        ArrayList<Point> move = AIHardPlayer2.Jouer3(env, result);
                        env.perform(move);
                        aiTrainTurn = false;
                    } else {
                        ArrayList<Point> move = aiEasy.aiTrain(env);
                        env.perform(move);
                        aiTrainTurn = true;
                    }

                }
                if(!aiTrainTurn){

                }
                //verifie si on n'est pas sorti à cause d'un coup interdit
                //System.out.println(nbTour);
                /*if (env.playerWin()) {
                    System.out.println("Un joueur à gagné");
                    winnerAI.add(this._nets.get(i + env.getCurrentPlayer()));
                }*/

            }

            //Garder les gagnantes
            //Instancie la liste de la generation suivante
            ArrayList<NeuronalNetwork> newNets = new ArrayList<>();

            //Recupere les IA gagnantes
            for (NeuronalNetwork n : winnerAI) {
                NeuronalNetwork net = new NeuronalNetwork(n);
                newNets.add(net);
            }

            //Recupere les IA gagnantes et les fait  muter
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(0.5f);
                newNets.add(net);
            }

            //Recupere les plus intelligente de nos IA et les fait plus muter
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(2f);
                newNets.add(net);
            }

            //Recupere les plus intelligentes de nos IA et leurs defonce le cerveau
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(10f);
                newNets.add(net);
            }

            //Changement d'agents entre les deux generation
            this._nets = newNets;
            //this._populationSize = newNets.size();
            this._generationNumber++;
            //System.out.println(this._generationNumber);
            //nbTour++;
        }
        System.out.println(this._generationNumber);

    }

    public void trainAI2() {
        System.out.println("Start training ");
        //int nbTour = 0;
        while (this._generationNumber < 100) {
            ArrayList<NeuronalNetwork> winnerAI = new ArrayList<>();
            //Faire jouer les IA instanciées entre elles;
            //Vérifier qu'elle ne joue pas de coup invalide
            //..

            for (int i = 0; i < this._populationSize; i ++) {
                //On fait jouer 2 IA, celle qui gagne est ajouté à la liste winner IA on joue dans AIEnvironnement
                AIEnvironnement env = new AIEnvironnement();
                //On ajoute les 2 joueurs
                env.addPlayer(0);
                env.addPlayer(1);
                //On met le joueur courant à 1
                env.setCurrentPlayer(1);
                //On ajoute les points de départs des 2 joueurs
                /**
                 * Joueur 0 et 1
                 * - - 1 1 -
                 * - - - 1 1
                 * 0 - - - 1
                 * 0 0 - - -
                 * - 0 0 - -
                 * */
                env.addStartingPoint(new Point(4, 0));
                env.addStartingPoint(new Point(0, 4));
                //On ajoute les billes
                //joueur 0
                ArrayList<Point> j0Marble = new ArrayList<>();
                j0Marble.add(new Point(2, 0));
                j0Marble.add(new Point(3, 0));
                j0Marble.add(new Point(3, 1));
                j0Marble.add(new Point(4, 1));
                j0Marble.add(new Point(4, 2));
                env.addPlayerMarble(j0Marble);
                //joueur 1 qui est une IA facile
                ArrayList<Point> j1Marble = new ArrayList<>();
                j1Marble.add(new Point(0, 2));
                j1Marble.add(new Point(0, 3));
                j1Marble.add(new Point(1, 3));
                j1Marble.add(new Point(1, 4));
                j1Marble.add(new Point(2, 4));
                env.addPlayerMarble(j1Marble);
                AIEasyPlayer aiHard = new AIEasyPlayer("default", Color.BLACK, null);
                //env.printBoard();
                //On joue la partie jusqu'à ce qu'on aie un gagnant
                //nbTour = 0;
                int numberOfMarble = 10;
                boolean aiTrainTurn = true;
                while (!env.playerWin()) {
                    //on change de joueur courant
                    env.nextPlayer();
                    if(aiTrainTurn) {
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
                                    if (p.y == i) {
                                        tInputs[currentMarble + k + 5] = 1;
                                    } else {
                                        tInputs[currentMarble + k + 5] = -1;
                                    }
                                }
                                currentMarble += 10;
                            }
                        }
                        result = this._nets.get(i + env.getCurrentPlayer()).FeedForward(tInputs);
                        ArrayList<Point> move = AIHardPlayer2.Jouer3(env, result);
                        //coup injouable on ajoute le joueur opposé à la liste des gagnants
                        if (move == null) {
                            //System.out.println("Coup invalide joué");
                            if (env.getCurrentPlayer() == 0) {
                                winnerAI.add(this._nets.get(i + 1));
                            } else {
                                winnerAI.add(this._nets.get(i));
                            }
                            break;
                        } else {
                            //System.out.println("Jouer le coup ");
                            //System.out.println(move);
                            env.perform(move);
                        }
                        //env.printBoard();
                        //nbTour++;
                        int newnumberOfMarble = 0;
                        for (ArrayList<Point> ap : env.getPlayerMarble()) {
                            newnumberOfMarble += ap.size();
                        }
                        if (numberOfMarble != newnumberOfMarble) {
                            newnumberOfMarble = numberOfMarble;
                            System.out.println(numberOfMarble);
                        }
                        aiTrainTurn = false;
                    } else {

                        aiTrainTurn = true;
                    }

                }
                //verifie si on n'est pas sorti à cause d'un coup interdit
                //System.out.println(nbTour);
                /*if (env.playerWin()) {
                    System.out.println("Un joueur à gagné");
                    winnerAI.add(this._nets.get(i + env.getCurrentPlayer()));
                }*/

            }

            //Garder les gagnantes
            //Instancie la liste de la generation suivante
            ArrayList<NeuronalNetwork> newNets = new ArrayList<>();

            //Recupere les IA gagnantes
            for (NeuronalNetwork n : winnerAI) {
                NeuronalNetwork net = new NeuronalNetwork(n);
                newNets.add(net);
            }

            //Recupere les IA gagnantes et les fait  muter
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(0.5f);
                newNets.add(net);
            }

            //Recupere les plus intelligente de nos IA et les fait plus muter
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(2f);
                newNets.add(net);
            }

            //Recupere les plus intelligentes de nos IA et leurs defonce le cerveau
            for(int j = 0; j < 5; j++){
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(10f);
                newNets.add(net);
            }

            //Changement d'agents entre les deux generation
            this._nets = newNets;
            //this._populationSize = newNets.size();
            this._generationNumber++;
            //System.out.println(this._generationNumber);
            //nbTour++;
        }
        System.out.println(this._generationNumber);

    }
}
