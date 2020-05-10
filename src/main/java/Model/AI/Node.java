package Model.AI;

import Model.Move;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Node {

    public enum Node_type {
        MAX_NODE,
        MIN_NODE
    }

    int _node_value;
    int _name;
    ArrayList<Point> _move;
    Node _parent;
    Node _child;
    Node_type _node_type;
    int _namePlayer;
    ArrayList<Node> _childList;

    /**
     * Permet de créer un noeud de l'arbre
     * @param node_value, move, parent, node_type
     */
    public Node(int node_value, ArrayList<Point> move, Node parent, Node child, Node_type node_type, int name, int namePlayer) {
        this._node_value = node_value;
        this._move = move;
        this._parent = parent;
        this._child = child;
        this._node_type = node_type;
        this._name = name;
        this._namePlayer = namePlayer;
        this._childList = new ArrayList<>();
    }
    public void addNodeChild(Node child){
        this._childList.add(child);
    }

    public int getNodeValue() {
        return _node_value;
    }

    public void setNodeValue(int value) {
        this._node_value = value;
    }

    public ArrayList<Point> getNodeMove() {
        return _move;
    }

    public void setNodeMove(ArrayList<Point> move) {
        this._move = move;
    }

    public Node getNodeParent() {
        return _parent;
    }

    public Node getNodeChild() {
        return _child;
    }

    public void setNodeChild(Node child) {
        this._child = child;
    }

    public Node_type getNodeType() {
        return _node_type;
    }

    public void printTree(){
        System.out.println("Name " + this._name + " player name " + this._namePlayer + " move " + this._move + " valeur " + this._node_value + " type "+ this._node_type);
        recPrintTree(1, this._childList);
    }

    public void recPrintTree(int recProfondeur, ArrayList<Node> childList){
        for(Node n:  childList){
            for(int i = 0; i < recProfondeur; i++){
                System.out.print("\t");
            }
            System.out.print("Name " + n._name + " player name " + n._namePlayer + " move " + n._move + " valeur " + n._node_value + " type " + n._node_type);
            System.out.println("");
            recPrintTree(recProfondeur+1, n._childList);

        }
    }
}
