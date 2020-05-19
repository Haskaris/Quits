package Model.AI;

import Model.Players.AIEasyPlayer;
import Model.Players.AIHardPlayer;
import Model.Players.AINeuronalNetwork;
import Model.Support.AIEnvironnement;
import Model.Support.Board;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * But de la classe gérer la création de réseaux de neuronnes et
 * les faire évoluer avec un algorithme génétique
 * */
public class NNManager {
    /**
     * Sauvegarde du plateau actuel
     */
    public Board _board;

    /**
     * Liste des IA de notre génération
     */
    public ArrayList<AINeuronalNetwork> _listAIPlayers = null;

    /**
     * Nombre d'IA par génération
     */
    public int _populationSize = 30;

    /**
     * Numéro de génération actuelle
     */
    private int _generationNumber = 0;

    /**
     * Contient la liste des réseaux de neuronne de la génération actuelle
     */
    private ArrayList<NeuronalNetwork> _nets;

    /**
     * Instance d'une IA facile, normale, ou difficile selon contre qui on fait jouer notre réseau de neuronne
     */
    public AIEasyPlayer _aiEasy;

    /**
     * Instance d'une IA difficile contre qui on fait jouer notre réseau de neuronne
     */
    public AIHardPlayer _aiHard;

    /**
     * Dimension de nos réseaux de neuronnes
     */
    private int[] _layers = new int[]{100, 10, 10, 10, 1};

    /**
     * Boolean nus indiquant si un de nos réseaux de neuronnes à gagné
     */
    private boolean _IATrainWin = false;

    /**
     * Sauvagarde du réseau de neuronne qui a gagné
     */
    private NeuronalNetwork _AIWin = null;

    /**
     * Boolean pour savoir si notre IA a gagné contre l'IA facile
     */
    public boolean _iaWin = false;

    /**
     * Fonction nous permettant d'évaluer d'efficacité de notre réseau de neuronne
     *
     * @param iaEnv
     * @return float
     */
    public float evalAI(AIEnvironnement iaEnv) {
        //with float we can do 1/current number for other player and choose de good one
        //prendre en compte toutes les billes au cas ou elle gagne
        if (iaEnv.playerWin()) {
            return 0;
        }
        float sum = 0;
        Point goal = new Point(Math.abs(4 - iaEnv.getStartingPoint().get(iaEnv.getIaPlayer()).x), Math.abs(4 - iaEnv.getStartingPoint().get(iaEnv.getIaPlayer()).y));

        //sum for the AI player
        for (Point currentPoint : iaEnv.getPlayerMarble().get(iaEnv.getIaPlayer())) {
            sum += (Math.sqrt(Math.pow(goal.x - currentPoint.x, 2) + Math.pow(goal.y - currentPoint.y, 2)));
        }
        //sum for other player
        for (int i = 0; i < iaEnv.getPlayerMarble().size(); i++) {
            if (i == iaEnv.getIaPlayer()) {
                continue;
            } else {
                goal.x = Math.abs(4 - iaEnv.getStartingPoint().get(i).x);
                goal.y = Math.abs(4 - iaEnv.getStartingPoint().get(i).y);
                for (Point currentPoint : iaEnv.getPlayerMarble().get(i)) {
                    //on ajoute l'opposé car on veut que les autres joueur soient loin de gagner
                    sum += 1 / ((Math.sqrt(Math.pow(goal.x - currentPoint.x, 2) + Math.pow(goal.y - currentPoint.y, 2))));
                }
            }
        }

        return sum;
    }

    /**
     * Instancie notre génération d'IA
     */
    private void createAIBodies() {
        if (this._listAIPlayers != null) {
            //Detruit toutes nos anciennes instances
            for (int i = 0; i < this._listAIPlayers.size(); i++) {
                this._listAIPlayers.remove(i);
            }
        }
        //Crée une generation d'IA
        this._listAIPlayers = new ArrayList<>();
        for (int i = 0; i < this._populationSize; i++) {
            AINeuronalNetwork ai = new AINeuronalNetwork(Integer.toString(i), Color.BLUE, this._board);
            this._listAIPlayers.add(ai);
        }
    }

    /**
     * Instancie notre génération de réseau de neuronne
     */
    public void initNeuralNetworks() {
        this._nets = new ArrayList<>();

        for (int i = 0; i < this._populationSize; i++) {
            NeuronalNetwork net = new NeuronalNetwork(this._layers);
            net.Mutate(0.5f);
            this._nets.add(net);
        }
    }

    /**
     * Création du manager
     */
    public NNManager() throws IOException {
        this._board = null;
        this._aiEasy = new AIEasyPlayer("default", Color.BLACK, null);
        this._aiHard = new AIHardPlayer("default", Color.BLACK, null);
        initNeuralNetworks();
        createAIBodies();
        trainAI2();
    }

    /**
     * Fait jouer notre réseau de neuronne (nt) contre l'IA facile
     * et retourne l'efficacité de notre réseau
     *
     * @param nt
     * @return float
     */
    public float playAgainstIA(NeuronalNetwork nt) {
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
        //On met le joueur IA à 1
        env.setIaPlayer(1);
        //On ajoute les billes
        //joueur 0
        ArrayList<Point> j0Marble = new ArrayList<>();
        j0Marble.add(new Point(2, 0));
        j0Marble.add(new Point(3, 0));
        j0Marble.add(new Point(3, 1));
        j0Marble.add(new Point(4, 1));
        j0Marble.add(new Point(4, 2));
        env.addPlayerMarble(j0Marble);
        //joueur 1 qui est le réseau de neuronne sauvegardé
        ArrayList<Point> j1Marble = new ArrayList<>();
        j1Marble.add(new Point(0, 2));
        j1Marble.add(new Point(0, 3));
        j1Marble.add(new Point(1, 3));
        j1Marble.add(new Point(1, 4));
        j1Marble.add(new Point(2, 4));
        env.addPlayerMarble(j1Marble);
        //env.printBoard();
        //On joue la partie jusqu'à ce qu'on aie un gagnant
        int nbTour = 0;
        //int numberOfMarble = 10;
        boolean aiTrainTurn = true;
        while (!env.playerWin() && nbTour < 100) {
            //on change de joueur courant
            env.nextPlayer();
            if (aiTrainTurn) {
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
                ArrayList<Point> move = AINeuronalNetwork.Jouer3(env, result);
                env.perform(move);
                aiTrainTurn = false;
            } else {
                ArrayList<Point> move = this._aiHard.aiTrain(env);
                env.perform(move);
                aiTrainTurn = true;
                //System.out.println(env.getOnePlayerMarble(env.getCurrentPlayer()).size());
            }
            //env.printBoard();
            nbTour++;
        }
        if (!aiTrainTurn && nbTour < 100) {
            System.out.println("Le reseau de neuronnes à gagné");
            this._iaWin = true;
        }
        nt.setFitness(evalAI(env));
        return nt.getFitness();
    }

    /**
     * Fonction d'entrainement de notre IA contre l'IA sauvagardée
     */
    public void trainAI() throws IOException {
        NeuronalNetwork nt = new NeuronalNetwork();
        System.out.println("Start training ");
        while (this._generationNumber < 1000000 && !this._IATrainWin) {
            //Liste contenant tous nos réseaux trié du plus au moins performant
            ArrayList<NeuronalNetwork> winnerAI = new ArrayList<>();

            //On ajoute un réseau de neuronnes qui sera le pire possible
            NeuronalNetwork netBad = new NeuronalNetwork(this._layers);
            netBad.Mutate(0.5f);
            netBad.setFitness(10000);
            winnerAI.add(netBad);

            //Faire jouer les IA instanciées entre elles;
            for (int i = 0; i < this._populationSize; i++) {

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

                //On met le joueur IA à 1
                env.setIaPlayer(1);

                //On ajoute les billes
                //joueur 0
                ArrayList<Point> j0Marble = new ArrayList<>();
                j0Marble.add(new Point(2, 0));
                j0Marble.add(new Point(3, 0));
                j0Marble.add(new Point(3, 1));
                j0Marble.add(new Point(4, 1));
                j0Marble.add(new Point(4, 2));
                env.addPlayerMarble(j0Marble);
                //joueur 1 qui est le réseau de neuronne sauvegardé
                ArrayList<Point> j1Marble = new ArrayList<>();
                j1Marble.add(new Point(0, 2));
                j1Marble.add(new Point(0, 3));
                j1Marble.add(new Point(1, 3));
                j1Marble.add(new Point(1, 4));
                j1Marble.add(new Point(2, 4));
                env.addPlayerMarble(j1Marble);


                //On joue la partie jusqu'à ce qu'on aie un gagnant avec un nombre de tour maximum
                int nbTour = 0;
                //Nous permet de savoir quelle IA à gagné
                boolean aiTrainTurn = true;
                while (!env.playerWin() && nbTour < 100) {
                    //on change de joueur courant
                    env.nextPlayer();
                    if (aiTrainTurn) {
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
                        result = this._nets.get(i).FeedForward(tInputs);
                        ArrayList<Point> move = AINeuronalNetwork.Jouer3(env, result);
                        env.perform(move);
                        aiTrainTurn = false;
                    } else {
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
                        //On fait jouer l'IA sauvegardée
                        result = nt.FeedForward(tInputs);
                        ArrayList<Point> move = AINeuronalNetwork.Jouer3(env, result);
                        env.perform(move);
                        aiTrainTurn = true;
                    }
                    nbTour++;
                }
                //On fait jouer notre IA contre l'IA facile aussi
                float val = playAgainstIA(this._nets.get(i));

                if (!aiTrainTurn && nbTour < 100 && this._iaWin) {
                    System.out.println("Un reseau de neuronnes à gagné");
                    this._IATrainWin = true;
                    this._AIWin = new NeuronalNetwork(this._nets.get(i));
                }

                this._nets.get(i).setFitness(evalAI(env) + val);

                //on ajoute l'IA dans une liste ordonnée
                for (int j = 0; j < winnerAI.size(); j++) {
                    if (this._nets.get(i).compareTo(winnerAI.get(j)) != -1) {
                        winnerAI.add(j, this._nets.get(i));
                        break;
                    }
                }
            }

            //Garder les gagnantes
            //Instancie la liste de la generation suivante
            ArrayList<NeuronalNetwork> newNets = new ArrayList<>();

            //Recupere les IA gagnantes
            for (int j = 0; j < 15; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                newNets.add(net);
            }

            //Recupere les IA gagnantes et les fait  muter
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(0.5f);
                newNets.add(net);
            }

            //Recupere les plus intelligente de nos IA et les fait plus muter
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(2f);
                newNets.add(net);
            }

            //Recupere les plus intelligentes de nos IA et leurs defonce le cerveau
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(10f);
                newNets.add(net);
            }

            //Changement d'agents entre les deux generation
            this._nets = newNets;
            this._generationNumber++;
            System.out.println(this._generationNumber);

        }
        if (this._IATrainWin) {
            System.out.println("Un réseau de neuronne à gagné");
            this._AIWin.writeNN();
        } else {
            System.out.println("Aucun réseau de neuronne n'a gagné");
        }

    }

    /**
     * Entraine notre IA contre une IA jouant avec l'arbre des possibilités
     */
    public void trainAI2() throws IOException {
        NeuronalNetwork nt = new NeuronalNetwork();
        System.out.println("Start training ");
        while (this._generationNumber < 1000000 && !this._IATrainWin) {
            //Liste contenant tous nos réseaux trié du plus au moins performant
            ArrayList<NeuronalNetwork> winnerAI = new ArrayList<>();

            //On ajoute un réseau de neuronnes qui sera le pire possible
            NeuronalNetwork netBad = new NeuronalNetwork(this._layers);
            netBad.Mutate(0.5f);
            netBad.setFitness(10000);
            winnerAI.add(netBad);

            //Faire jouer les IA instanciées entre elles;
            for (int i = 0; i < this._populationSize; i++) {

                float result = playAgainstIA(this._nets.get(i));
                if (this._iaWin) {
                    System.out.println("Un reseau de neuronnes à gagné");
                    this._IATrainWin = true;
                    this._AIWin = new NeuronalNetwork(this._nets.get(i));
                }
                this._nets.get(i).setFitness(result);

                //on ajoute l'IA dans une liste ordonnée
                for (int j = 0; j < winnerAI.size(); j++) {
                    if (this._nets.get(i).compareTo(winnerAI.get(j)) != -1) {
                        winnerAI.add(j, this._nets.get(i));
                        break;
                    }
                }
            }

            //Garder les gagnantes
            //Instancie la liste de la generation suivante
            ArrayList<NeuronalNetwork> newNets = new ArrayList<>();

            //Recupere les IA gagnantes
            for (int j = 0; j < 15; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                newNets.add(net);
            }

            //Recupere les IA gagnantes et les fait  muter
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(0.5f);
                newNets.add(net);
            }

            //Recupere les plus intelligente de nos IA et les fait plus muter
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(2f);
                newNets.add(net);
            }

            //Recupere les plus intelligentes de nos IA et leurs defonce le cerveau
            for (int j = 0; j < 5; j++) {
                NeuronalNetwork net = new NeuronalNetwork(winnerAI.get(j));
                net.Mutate(10f);
                newNets.add(net);
            }

            //Changement d'agents entre les deux generation
            this._nets = newNets;
            this._generationNumber++;
            System.out.println(this._generationNumber);

        }
        if (this._IATrainWin) {
            System.out.println("Un réseau de neuronne à gagné");
            this._AIWin.writeNN();
        } else {
            System.out.println("Aucun réseau de neuronne n'a gagné");
        }
    }
}
