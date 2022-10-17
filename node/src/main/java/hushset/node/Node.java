package hushset.node;

import java.math.BigInteger;
import java.util.ArrayList;

import static hushset.utilities.HashFuncUtils.Sha1Int;

public class Node {
    public BigInteger id;
    public ArrayList<Node> table;

	public Node(String id) {
        this.id = hash(id);
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

        return chosenNode;
    }

    public void join(Node newNode) {
        ArrayList<Node> newTable = this.table;
        newTable.add(newNode);
        for (Node n : newTable) {
            n.setTable(newTable);
        }

        newNode.setTable(this.table);
    }

    private BigInteger hash(String id) {
        return Sha1Int(id);
    }
}
