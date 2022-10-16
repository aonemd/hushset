package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// import java.math.BigInteger;
// import java.nio.charset.StandardCharsets;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
// import org.apache.commons.codec.digest.DigestUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

class FingerTable {

}

class Node {
    int id;
    String host;
    int port;
    Node predecessor;
    Node successor;
    FingerTable finger;

    Node() {
    }
}

public class HushSet {
    public ArrayList<LinkedList<Integer>> buckets;
    public int bucketSize = 10;
    int size = 0;
    static final double COLLISION_CHANCE = 0.3; // load factor = size / bucketSize
    public int id;
    ArrayList<HushSet> table;

    public HushSet() {
        // this.id = this.hashCode() % bucketSize;
        this.id = getHash(this.hashCode(), bucketSize);

        buckets = new ArrayList<LinkedList<Integer>>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(i, new LinkedList<Integer>());
        }
        size = 0;
    }

    public void setTable(ArrayList<HushSet> table) {
        this.table = table;
    }

    public int size() {
        return size;
    }

    public boolean contains(int element) {
        int hash = getHash(element, bucketSize);

        HushSet chosenNode = findSuccessor(hash);

        LinkedList<Integer> curBucket = chosenNode.buckets.get(hash);

        if (curBucket == null) {
            return false;
        }

        return curBucket.contains(element);
    }

    public boolean insert(int element) {
        int hash = getHash(element, bucketSize);

        System.out.println(element + ": " + hash);
        HushSet chosenNode = findSuccessor(hash);
        System.out.println(chosenNode.id);
        System.out.println("++++++++++++++");

        return chosenNode.add(element);
    }

    public HushSet findSuccessor(int hash) {
        HushSet chosenNode = this;
        for (HushSet node : this.table) {
            if (hash > id && hash <= node.id) {
                chosenNode = node;
                break;
            }
        }
        return chosenNode;
    }

    public boolean add(int element) {
        int hash = getHash(element, bucketSize);

        LinkedList<Integer> curBucket = buckets.get(hash);

        if (curBucket.contains(element)) {
            return false;
        }

        curBucket.push(element);

        if ((float) size / bucketSize > COLLISION_CHANCE) {
            resize();
        }

        size++;

        return true;
    }

    public boolean remove(int element) {
        int hash = getHash(element, bucketSize);

        LinkedList<Integer> curBucket = buckets.get(hash);

        if (!curBucket.contains(element)) {
            return false;
        }

        return curBucket.remove((Integer) element);
    }

    public boolean clear() {
        return true;
    }

    void resize() {
        int newBucketSize = bucketSize * 2;
        ArrayList<LinkedList<Integer>> newBuckets = new ArrayList<>(newBucketSize);

        for (int i = 0; i < newBucketSize; i++) {
            newBuckets.add(i, new LinkedList<Integer>());
        }

        for (int i = 0; i < bucketSize; i++) {
            for (Integer element : buckets.get(i)) {
                int hash = getHash(element, newBucketSize);
                newBuckets.get(hash).push(element);
            }
        }

        bucketSize = newBucketSize;
        buckets = newBuckets;
    }

    public int getHash(int element, int hashSize) {
        // return element % hashSize;

        return Integer.toString(element).chars().reduce(0, (ss, c) -> ss * 37 + c) % hashSize;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // HushSet hs1 = new HushSet();
        // HushSet hs2 = new HushSet();
        // HushSet hs3 = new HushSet();
        // HushSet hs4 = new HushSet();
        // HushSet hs5 = new HushSet();
        //
        // ArrayList<HushSet> sets = new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs4,
        // hs5));
        //
        // hs1.setTable(new ArrayList<>(Arrays.asList(hs2, hs3, hs4, hs5)));
        // hs2.setTable(new ArrayList<>(Arrays.asList(hs1, hs3, hs4, hs5)));
        // hs3.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs4, hs5)));
        // hs4.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs5)));
        // hs5.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs4)));
        //
        // // hs1.insert(16);
        // // hs1.insert(21);
        // // hs1.insert(12);
        // // hs1.insert(129);
        // // hs1.insert(1098);
        // // hs1.insert(555);
        // // hs1.insert(257);
        //
        // for (HushSet set : sets) {
        // System.out.println(set.id);
        // System.out.println("---");
        // for (LinkedList<Integer> bb : set.buckets) {
        // System.out.println(bb.toString());
        // }
        // System.out.println("--------------------");
        // }
        //
        // System.out.println(hs1.contains(257));

        // System.out.println(hs.size());
        // System.out.println(hs.bucketSize);
        // for (LinkedList<Integer> bb : hs.buckets) {
        // System.out.println(bb.toString());
        // // for (Integer it : bb) {
        // // System.out.print(it + ":" + hs.getHash(it, hs.bucketSize)+ ", ");
        // // }
        // // if (!bb.isEmpty())
        // // System.out.println();
        // }

        // String input = "10";
        // MessageDigest md = MessageDigest.getInstance("MD5");
        // byte[] res = md.digest(input.getBytes(StandardCharsets.UTF_8));
        // System.out.println(res);
        //
        // // Convert byte array into signum representation
        // BigInteger number = new BigInteger(1, hash);
        //
        // // Convert message digest into hex value
        // StringBuilder hexString = new StringBuilder(number.toString(16));
        //
        // // Pad with leading zeros
        // while (hexString.length() < 64)
        // {
        // hexString.insert(0, '0');
        // }

        // String hash = "35454B055CC325EA1AF2126E27707052";
        // String password = "ILoveJava";
        // byte[] bytesOfMessage = password.getBytes("UTF-8");
        // MessageDigest md = MessageDigest.getInstance("MD5");
        // byte[] myHash = md.digest(bytesOfMessage);
        // md.update(password.getBytes());
        // byte[] digest = md.digest();
        // String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        // System.out.println(myHash);

    String stringToHash = "MyJavaCode";
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.update(stringToHash.getBytes());
    byte[] digiest = messageDigest.digest();
    String hashedOutput = DatatypeConverter.printHexBinary(digiest);
    System.out.println(hashedOutput);
    }
}
