package hushset.node;

import java.math.BigInteger;
import java.util.ArrayList;

import static hushset.utilities.HashFuncUtils.DegreeMD5Hex;

public class Node {
    public double id;
    public ArrayList<Node> table;

	public Node(String id) {
        this.id = hash(id);
    }

	public void setTable(ArrayList<Node> table) {
		this.table = table;
	}

    public Node findSuccessor(String key) {
        double keyHash = DegreeMD5Hex(key);

        System.out.println(key + " (hash)=> " + keyHash);

        Node chosenNode = this;
        for (Node node : this.table) {
            if (keyHash > id && keyHash <= node.id) {
                chosenNode = node;
                break;
            }
        }
        return chosenNode;
    }

    private double hash(String id) {
        return DegreeMD5Hex(id);
    }
}
