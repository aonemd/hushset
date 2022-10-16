package hushset.node;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.commons.codec.digest.*;

public class Node {
    public double id;
    public ArrayList<Node> table;

    public Node(String id) {
        this.id = hash(id);
    }

    private Node findSuccessor(double hash) {
        Node chosenNode = this;
        for (Node node : this.table) {
            if (hash > id && hash <= node.id) {
                chosenNode = node;
                break;
            }
        }
        return chosenNode;
    }

    private double hash(String id) {
        String md5Hex = DigestUtils
                .md5Hex(id);

        BigInteger h = new BigInteger(md5Hex, 16);
        BigInteger modded = BigInteger.valueOf(1000000);

        return h.mod(modded).doubleValue() / modded.doubleValue() * (180 / Math.PI);
    }
}
