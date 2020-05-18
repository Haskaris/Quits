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
    private final String _filepath = "Sauvegardes/AINeuronalNetwork";
    private int[] _layers; //layers list
    private float[][] _neurons; //neuron matrix
    private float[][][] _weights; //weight matrix
    //private int _fitness; //network fitness
    private float _fitness;         //network fitness

    public void writeGame() throws IOException{
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

    public void printLayers(OutputStream stream) throws IOException {
        //Print de chaque valeur du layer
        for(int i = 0; i < this._layers.length; i++){
            stream.write(String.valueOf(this._layers[i]).getBytes());
            stream.write(" ".getBytes());
        }
        stream.write('\n');
    }

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

    //Read a neuronal network in the file
    public NeuronalNetwork() throws IOException {
        readNeuronalNetwork();
    }

    public NeuronalNetwork(int[] layers) {
        this._layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++) {
            this._layers[i] = layers[i];
        }
        this._fitness = 0;
        neuronalInit();
        weightsInit();
    }

    //copy of previous neuronal network
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

    //Copie tout les poids du reseau donne en parametres
    private void CopyWeights(float[][][] copyWeights) {
        for (int i = 0; i < this._weights.length; i++) {
            for (int j = 0; j < this._weights[i].length; j++) {
                for (int k = 0; k < this._weights[i][j].length; k++) {
                    this._weights[i][j][k] = copyWeights[i][j][k];
                }
            }
        }
    }

    public float getFitness(){
        return this._fitness;
    }

    public void setFitness(float fitness){
        this._fitness = fitness;
    }

    public void neuronalInit() {
        ArrayList<float[]> neurons = new ArrayList<>();
        for (int i = 0; i < this._layers.length; i++) {
            neurons.add(new float[this._layers[i]]);
        }
        //test if it works
        this._neurons = neurons.toArray(new float[0][0]);
    }

    public void weightsInit() {
        ArrayList<float[][]> layersWeightList = new ArrayList<>(); //weights list which will later will converted into a weights 3D array

        int min = -1;
        int max = 1;
        //Iteration sur tout les neurones ayant des connexions entrantes
        for (int i = 1; i < this._layers.length; i++) {
            ArrayList<float[]> layerWeightsList = new ArrayList<>();

            int neuronsInPreviousLayer = this._layers[i - 1];

            //Iteration sur tout les neurones du layer actuel
            for (int j = 0; j < this._neurons[i].length; j++) {
                float[] neuronWeights = new float[neuronsInPreviousLayer];

                //Iteration sur tout les neurones du layer precedent, et set leurs poids entre -1 et 1
                for (int k = 0; k < neuronsInPreviousLayer; k++) {
                    neuronWeights[k] = (float) (Math.random() * (max - min + 1) + min);
                }
                layerWeightsList.add(neuronWeights);
            }
            layersWeightList.add(layerWeightsList.toArray(new float[0][0])); //Convertis les poids de cette couche en liste 2D et l'ajoute a la liste des poids
        }
        this._weights = layersWeightList.toArray(new float[0][0][0]); //convertis en tableau 3D

    }

    //FeedForward
    public float[] FeedForward(float[] inputs)
    {
        //Ajoute nos inputs dans la matrices des neurones d'entree
        for (int i = 0; i < inputs.length; i++)
        {
            getNeurons()[0][i] = inputs[i];
        }

        //Calculs
        //Iteration sur toute les couches, tout les neurones, puis toutes les connexions entrante et fait les calculs du feedforward
        for (int i = 1; i < getLayers().length; i++)
        {
            for (int j = 0; j < getNeurons()[i].length; j++)
            {
                float value = 0f;
                for (int k = 0; k < getNeurons()[i-1].length; k++)
                {
                    //System.out.println(getWeights()[i - 1][j][k]);
                    //System.out.println(getNeurons()[i - 1][k]);
                    value += getWeights()[i - 1][j][k] * getNeurons()[i - 1][k]; //sum off all weights connections of this neuron weight their values in previous layer
                }
                getNeurons()[i][j] = (float)Math.tanh(value); //Fonction d'activation : tangente hyperbolique
            }
        }
        return getNeurons()[getNeurons().length-1]; //retourne le resultat
    }

    //Mutation
    public void Mutate(float condition)
    {
        //Iteration sur tout les poids du reseau
        for (int i = 0; i < getWeights().length; i++)
        {
            for (int j = 0; j < getWeights()[i].length; j++)
            {
                for (int k = 0; k < getWeights()[i][j].length; k++)
                {

                    //Un pourcent de chance de faire muter notre poids en une valeure aleatoire entre -1 et 1
                    float weight = getWeights()[i][j][k];
                    float randomNumber = (float) (Math.random() * (100 + 1));
                    if (randomNumber <= condition)
                    {
                        float randomNumber2 = (float) (Math.random() * (1 - (-1) + 1) + (-1));
                        weight = randomNumber2;
                    }
                    getWeights()[i][j][k] = weight;
                }
            }
        }
    }

    public int[] getLayers() {
        return this._layers;
    }

    public float[][] getNeurons() {
        return this._neurons;
    }

    public float[][][] getWeights() {
        return this._weights;
    }

    //Compare le fitness de deux reseaux
    public int compareTo(NeuronalNetwork other)
    {
        if (other == null) return 1;

        if (getFitness() < other.getFitness())
            return 1;
        else if (getFitness() > other.getFitness())
            return -1;
        else
            return 0;
    }

    /*public float getFitness() {
        return this._fitness;
    }*/

    //Ajoute du fitness au reseau
    /*public void AddFitness(float fit)
    {
        this._fitness += fit;
    }*/

    //donne une valeur fixe de fitness au reseau
    /*public void SetFitness(float fit)
    {
        this._fitness = fit;
    }*/

    //Compare le fitness de deux reseaux
    /*public int CompareTo(NeuronalNetwork other)
    {
        if (other == null) return 1;

        if (getFitness() > other.getFitness())
            return 1;
        else if (getFitness() < other.getFitness())
            return -1;
        else
            return 0;
    }*/

    public void setWeight(float[][][] weight){
        this._weights = weight;
    }

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