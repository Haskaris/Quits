package Model.AI;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NeuronalNetwork {
    private int[] _layers; //layers list
    private float[][] _neurons; //neuron matrix
    private float[][][] _weights; //weight matrix
    private int _fitness; //network fitness

    public NeuronalNetwork(int[] layers) {
        this._layers = new int[layers.length];
        for (int i = 0; i < layers.length; i++) {
            this._layers[i] = layers[i];
        }
        neuronalInit();
        weightsInit();
    }

    //copy of previous neuronal network
    public NeuronalNetwork(NeuronalNetwork copyNetwork) {
        this._layers = new int[copyNetwork.getLayers().length];
        for (int i = 0; i < copyNetwork.getLayers().length; i++) {
            this._layers[i] = copyNetwork.getLayers()[i];
        }

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
}