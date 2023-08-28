package hushset.node;

import java.math.BigInteger;
import java.util.ArrayList;

import static hushset.utilities.HashFuncUtils.Sha1Int;

public class Node {
    public BigInteger id;
    public Node successor;
    public Node predecessor;
    public ArrayList<Node> table;

	public Node(String id) {
        this.id = hash(id);

        this.successor   = null;
        this.predecessor = null;
    }

	public void setTable(ArrayList<Node> table) {
		this.table = table;
	}

    public Node findSuccessor(String key) {
        BigInteger keyHash = hash(key);


        Node chosenNode = this;
        for (Node node : this.table) {
            // System.out.println("n: " + node.id);
            // keyHash > id && keyHash <= node.id
            // if (keyHash.compareTo(id) == 1 && keyHash.compareTo(node.id) <= 0) {
            // TODO: Replace with the proper CHORD routing mechanism ^
            if (keyHash.compareTo(node.id) <= 0) {
                chosenNode = node;
                break;
            }
        }
        System.out.println(key + " (hash = " + keyHash + ")=> " + chosenNode.id);

        return this;
    }

    public Node findSuccessorNode(BigInteger newId) {
        if (this.successor == null) {
            return this;
        }

        if (newId.compareTo(this.id) == 1 && newId.compareTo(this.successor.id) <= 0 ) {
            return this.successor;
        } else {
            return this.successor.findSuccessorNode(newId);
        }
    }

    public void join(Node newNode) {
        Node successorNode = this.findSuccessorNode(newNode.id);
        newNode.successor = successorNode;
        successorNode.predecessor = newNode;

        // if (this.successor == null && this.predecessor == null) {
        //     // it's the genesis node
        //     this.successor = newNode;
        //     newNode.predecessor = this;
        // } else {
        //     // look for predecessor and successor of newNode
        //     //
        //     // newNode.id compare with this.id
        //     if (newNode.id.compareTo(this.id) > 1) {
        //
        //     }
        // }

        // ArrayList<Node> newTable = this.table;
        // newTable.add(newNode);
        // for (Node n : newTable) {
        //     n.setTable(newTable);
        // }
        //
        // newNode.setTable(this.table);
    }

    public String toString()  {
        return this.id.toString();
    }

    private BigInteger hash(String id) {
        return Sha1Int(id);
    }
}
