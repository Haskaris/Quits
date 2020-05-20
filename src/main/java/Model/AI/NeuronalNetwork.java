package Model.AI;

import Global.Configuration;
import Model.Players.Player;
import Model.Support.Board;
import Model.Support.Marble;

import java.io.*;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;

public class NeuronalNetwork {
    /**
     * Chemin pour le fichier de sauvegarde du réseau de neuronne entrainé
     * */
    private final String _filepath = "Sauvegardes/AINeuronalNetwork";

    /***
     * Tableau contenant la taille des différentes couches du réseau de neuronnes
     * */
    private int[] _layers; //layers list

    /**
     * Matrice contenant les neuronnes
     * Sert au calcul du coup que doit jouer l'IA
     * */
    private float[][] _neurons; //neuron matrix

    /**
     * Matrice des poids pour chaque neuronne
     * */
    private float[][][] _weights; //weight matrix

    /**
     * Valeur attribué au réseau de neuronne pour savoir si
     * il est plus efficace qu'un autre réseau de neuronne
     * (plus la valeur est petite plus il est performant)
     * */
    private float _fitness;         //network fitness

    /**
     * Ecrit le réseau de neuronne dans le fichier de sauvegarde
     * */
    public void writeNN() throws IOException{
        File out = new File(this._filepath);
        OutputStream stream;
        try {
            out.getParentFile().mkdirs();
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(IOException e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie : " + this._filepath);
            return;
        }

        //On écrit le layers
        printLayers(stream);
        stream.write("fin".getBytes());
        stream.write('\n');

        //On écrit les neurons
        printNeurons(stream);
        stream.write("fin".getBytes());
        stream.write('\n');

        //On écrit les poids
        printWeigths(stream);
        stream.write("fin".getBytes());

        stream.write('\n');
        stream.flush();
        stream.close();
    }

    /**
     * Ecrit la matrice des poids du réseau de neuronne dans le
     * fichier de sauvegarde
     * */
    public void printWeigths(OutputStream stream) throws IOException {
        //Print chaque valeur des poids
        for(int i = 0; i < this._weights.length; i++){
            for(int n = 0; n < this._weights[i].length; n++) {
                for (int j = 0; j < this._weights[i][n].length; j++) {
                    stream.write(String.valueOf(this._weights[i][n][j]).getBytes());
                    stream.write(" ".getBytes());
                }
                stream.write('\n');
            }
            stream.write("fini".getBytes());
            stream.write('\n');
        }
    }

    /**
     * Ecrit la matrice des neuronnes du reseau dans le fichier
     * */
    public void printNeurons(OutputStream stream) throws IOException {
        //Print chaque valeur des Neurons
        for(int i = 0; i < this._neurons.length; i++){
            for(int j = 0; j < this._neurons[i].length; j++){
                stream.write(String.valueOf(this._neurons[i][j]).getBytes());
                stream.write(" ".getBytes());
            }
            stream.write('\n');
        }
    }

    /**
     * Ecrit le tableau contenant la taille des couches du réseau
     * */
    public void printLayers(OutputStream stream) throws IOException {
        //Print de chaque valeur du layer
        for(int i = 0; i < this._layers.length; i++){
            stream.write(String.valueOf(this._layers[i]).getBytes());
            stream.write(" ".getBytes());
        }
        stream.write('\n');
    }

    /**
     * Lis le réseau de neuronne dans le fichier de suavegarde
     * */
    public void readNeuronalNetwork() throws IOException {
        try (InputStream in_stream = new FileInputStream(this._filepath)) {
            //System.out.println("In read neuronal network");
            //On lit le layers
            String[] firstline = readLine(in_stream).split(" ");
            //System.out.println("In read neuronal network 2");
            //on initialise la variable des layers
            this._layers = new int[firstline.length];
            for(int i = 0; i < firstline.length; i++){
                this._layers[i] = Integer.parseInt(firstline[i]);
            }

            //System.out.println("In read neuronal network 3");
            //On lit les neurons
            String[] secondLine = readLine(in_stream).split(" ");
            String[] line = readLine(in_stream).split(" ");
            ArrayList<String[]> annexeTab = new ArrayList<>();
            while(!line[0].equals("fin")){
                annexeTab.add(line);
                line = readLine(in_stream).split(" ");
            }

            //System.out.println("In read neuronal network 4");
            ArrayList<float[]> neurons = new ArrayList<>();
            //On crée la matrice des neuronnes
            for(int i = 0; i < annexeTab.size(); i++){
                float[] tab = new float[annexeTab.get(i).length];
                for(int j = 0; j < annexeTab.get(i).length; j++){
                    tab[j] = Float.parseFloat(annexeTab.get(i)[j]);
                }
                neurons.add(tab);
            }
            this._neurons = neurons.toArray(new float[0][0]);


            //System.out.println("In read neuronal network 5");
            //On lit la matrice des poids
            ArrayList<ArrayList<String[]>> annexeTab2 = new ArrayList<>();
            line = readLine(in_stream).split(" ");
            while(!line[0].equals("fin")){
                ArrayList<String[]> annexeTab3 = new ArrayList<>();
                while(!line[0].equals("fini")){
                    annexeTab3.add(line);
                    line = readLine(in_stream).split(" ");
                }
                line = readLine(in_stream).split(" ");
                annexeTab2.add(annexeTab3);
            }

            //System.out.println("In read neuronal network 6");
            ArrayList<float[][]> layersWeightList = new ArrayList<>(); //weights list which will later will converted into a weights 3D array
            //On crée la matrice des poids
            for (int i = 0; i < annexeTab2.size(); i++) {
                ArrayList<float[]> layerWeightsList = new ArrayList<>();

                for(int j = 0; j < annexeTab2.get(i).size(); j++){
                    float[] neuronWeights = new float[annexeTab2.get(i).get(j).length];
                    for(int n = 0; n < annexeTab2.get(i).get(j).length; n++){
                        neuronWeights[n] = Float.parseFloat(annexeTab2.get(i).get(j)[n]);
                    }
                    layerWeightsList.add(neuronWeights);
                }
                layersWeightList.add(layerWeightsList.toArray(new float[0][0]));
            }
            this._weights = layersWeightList.toArray(new float[0][0][0]);
            //System.out.println("fin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction pour lire une ligne dans un fichier donné
     * @param stream
     * */
    public static String readLine(InputStream stream) throws IOException {
        String S = "";
        byte[] data = new byte [1];
        stream.read(data);
        while(data[0] != '\n' && data[0] != '\r' && data[0] != 0){
            S += (char)data[0];
            stream.read(data);
        }
        return S;
    }

    /**
     * Instanciation d'un réseau de neuronnes à partir
     * du fichier de sauvegarde
     * */
    public NeuronalNetwork() throws IOException {
        readNeuronalNetwork();
    }

    /**
     * Instanciation d'un réseau de neuronne
     * à partir d'un tableau de taille de couche du réseau
     * @param layers
     * */
    public NeuronalNetwork(int[] layers) {
        this._layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++) {
            this._layers[i] = layers[i];
        }
        this._fitness = 0;
        neuronalInit();
        weightsInit();
    }

    /**
     * Création d'une copie d'un réseau de neuronne
     * @param copyNetwork
     * */
    public NeuronalNetwork(NeuronalNetwork copyNetwork) {
        this._layers = new int[copyNetwork.getLayers().length];
        for (int i = 0; i < copyNetwork.getLayers().length; i++) {
            this._layers[i] = copyNetwork.getLayers()[i];
        }
        this._fitness = 0;
        neuronalInit();
        weightsInit();
        CopyWeights(copyNetwork.getWeights());
    }

    /**
     * Copie les poids passé en paramètre dans le réseau de neuronne
     * appelant
     * @param copyWeights
     * */
    private void CopyWeights(float[][][] copyWeights) {
        for (int i = 0; i < this._weights.length; i++) {
            for (int j = 0; j < this._weights[i].length; j++) {
                for (int k = 0; k < this._weights[i][j].length; k++) {
                    this._weights[i][j][k] = copyWeights[i][j][k];
                }
            }
        }
    }

    /**
     * Récupère la valeur du réseau de neuronne
     * @return float
     * */
    public float getFitness(){
        return this._fitness;
    }

    /**
     * Change la valeur du réseau de neuronne
     * @param fitness
     * */
    public void setFitness(float fitness){
        this._fitness = fitness;
    }

    /**
     * Initialise la matrice du réseau de neuronne
     * */
    public void neuronalInit() {
        ArrayList<float[]> neurons = new ArrayList<>();
        for (int i = 0; i < this._layers.length; i++) {
            neurons.add(new float[this._layers[i]]);
        }
        this._neurons = neurons.toArray(new float[0][0]);
    }

    /**
     * Initialise la matrice des poids du réseau de neuronne
     * avec chaque poids choisit aléatoirement entre -1 et 1
     * */
    public void weightsInit() {
        ArrayList<float[][]> layersWeightList = new ArrayList<>(); //Liste des poids que l'on va convertir en matrice

        int min = -1;
        int max = 1;
        for (int i = 1; i < this._layers.length; i++) {
            ArrayList<float[]> layerWeightsList = new ArrayList<>();

            int neuronsInPreviousLayer = this._layers[i - 1];

            for (int j = 0; j < this._neurons[i].length; j++) {
                float[] neuronWeights = new float[neuronsInPreviousLayer];

                for (int k = 0; k < neuronsInPreviousLayer; k++) {
                    neuronWeights[k] = (float) (Math.random() * (max - min + 1) + min);
                }
                layerWeightsList.add(neuronWeights);
            }
            layersWeightList.add(layerWeightsList.toArray(new float[0][0])); //Convertis les poids de cette couche en liste 2D et l'ajoute a la liste des poids
        }
        this._weights = layersWeightList.toArray(new float[0][0][0]); //convertis en tableau 3D

    }

    /**
     * Renvoi le résultat du réseau de neuronne pour une entrée donné
     * @param inputs
     * @return float[]
     * */
    public float[] FeedForward(float[] inputs) {
        //Ajoute nos entrees dans la matrices des neurones d'entree
        for (int i = 0; i < inputs.length; i++)
        {
            getNeurons()[0][i] = inputs[i];
        }

        //Calculs
        //Iteration sur toute les couches du réseau
        for (int i = 1; i < getLayers().length; i++) {
            for (int j = 0; j < getNeurons()[i].length; j++) {
                float value = 0f;
                for (int k = 0; k < getNeurons()[i-1].length; k++) {
                    //Somme des poids connecté et des valeurs des neuronnes de la couches précédente
                    value += getWeights()[i - 1][j][k] * getNeurons()[i - 1][k];
                }
                //Fonction d'activation d'un neuronne, on utilise la tangente hyperbolique
                getNeurons()[i][j] = (float)Math.tanh(value);
            }
        }
        //retourne le resultat
        return getNeurons()[getNeurons().length-1];
    }

    /**
     * Fait muter le réseau de neuronne en fonction de la condition
     * donnée, plus la condiiton est grande, plus il y aura de mutatioon
     * @param condition
     * */
    public void Mutate(float condition) {
        //Iteration sur tous les poids du réseau
        for (int i = 0; i < getWeights().length; i++) {
            for (int j = 0; j < getWeights()[i].length; j++) {
                for (int k = 0; k < getWeights()[i][j].length; k++) {
                    float weight = getWeights()[i][j][k];
                    //Nombre aléatoire pour savoir si on fait muter le réseau de neuronne
                    float randomNumber = (float) (Math.random() * (100 + 1));
                    //Si le nombre est inférieur à notre condition on change le poids pour une valeur aléatoire entre -1 et 1
                    if (randomNumber <= condition) {
                        float randomNumber2 = (float) (Math.random() * (1 - (-1) + 1) + (-1));
                        weight = randomNumber2;
                    }
                    getWeights()[i][j][k] = weight;
                }
            }
        }
    }

    /**
     * Récupére la liste contenant la taille
     * de chaque couche du réseau
     * @return int[]
     * */
    public int[] getLayers() {
        return this._layers;
    }

    /**
     * Récupére la matrice des neuronnes
     * du réseau
     * @return float[][]
     * */
    public float[][] getNeurons() {
        return this._neurons;
    }

    /**
     * Récupére la matrice des poids du réseau
     * @return float[][][]
     * */
    public float[][][] getWeights() {
        return this._weights;
    }

    /**
     * Compare l'efficacité du réseau de neuronne à un autre
     * réseau donné, retourne
     * 1 si notre réseau est plus efficace
     * 0 si les 2 réseaux sont autant efficace
     * -1 si notre réseau est moins efficace
     * @param other
     * @return int
     * */
    public int compareTo(NeuronalNetwork other) {
        if (other == null) return 1;

        if (getFitness() < other.getFitness())
            return 1;
        else if (getFitness() > other.getFitness())
            return -1;
        else
            return 0;
    }

    /**
     * Change la valeur de la matrice des poids
     * @param weight
     * */
    public void setWeight(float[][][] weight){
        this._weights = weight;
    }

    /**
     * Affiche le réseau de neuronne
     * */
    public void printNeuronalNetwork(){
        System.out.println("Layers : ");
        for(int f: this._layers){
            System.out.print(f + " ");
        }
        System.out.println("");
        System.out.println("Neurons : ");
        for(int i = 0; i < getNeurons().length; i++){
            for(int j = 0; j < getNeurons()[i].length; j++) {
                System.out.print(getNeurons()[i][j] + " ");
            }
        }
        System.out.println("");
        System.out.println("Weigth : ");
        for(int i = 0; i < getWeights().length; i++){
            for(int j = 0; j < getWeights()[i].length; j++) {
                for(int n = 0; n < getWeights()[i][j].length; n++) {
                    System.out.print(getWeights()[i][j][n] + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }
}