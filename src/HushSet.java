package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class HushSet {
    public ArrayList<LinkedList<Integer>> buckets;
    public int bucketSize = 10;
    int size = 0;
    static final double COLLISION_CHANCE = 0.3; // load factor = size / bucketSize
    public int id;
    ArrayList<HushSet> table;

    public HushSet() {
        this.id = this.hashCode() % bucketSize;

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

        HushSet chosenNode = findSuccessor(hash);

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
        HushSet hs1 = new HushSet();
        HushSet hs2 = new HushSet();
        HushSet hs3 = new HushSet();
        HushSet hs4 = new HushSet();
        HushSet hs5 = new HushSet();

        ArrayList<HushSet> sets = new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs4, hs5));

        hs1.setTable(new ArrayList<>(Arrays.asList(hs2, hs3, hs4, hs5)));
        hs2.setTable(new ArrayList<>(Arrays.asList(hs1, hs3, hs4, hs5)));
        hs3.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs4, hs5)));
        hs4.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs5)));
        hs5.setTable(new ArrayList<>(Arrays.asList(hs1, hs2, hs3, hs4)));

        hs1.insert(16);
        hs1.insert(21);
        hs1.insert(12);
        hs1.insert(129);
        hs1.insert(1098);
        hs1.insert(555);
        hs1.insert(257);

        System.out.println(hs1.contains(257));
    }
}
