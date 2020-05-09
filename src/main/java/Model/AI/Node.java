package Model.AI;

import Model.Move;

import java.io.IOException;
import java.util.ArrayList;

public class Node {

    public enum Node_type {
        MAX_NODE,
        MIN_NODE
    }

    int _node_value;
    Move _move;
    Node _parent;
    Node _child;
    Node_type _node_type;

    /**
     * Permet de créer un noeud de l'arbre
     * @param node_value, move, parent, node_type
     */
    public Node(int node_value, Move move, Node parent, Node child, Node_type node_type) {
        this._node_value = node_value;
        this._move = move;
        this._parent = parent;
        this._child = child;
        this._node_type = node_type;
    }

    public int getNodeValue() {
        return _node_value;
    }

    public void setNodeValue(int value) {
        this._node_value = value;
    }

    public Move getNodeMove() {
        return _move;
    }

    public void setNodeMove(Move move) {
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
}
