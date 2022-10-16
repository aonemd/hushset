package hushset.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Set {
    public ArrayList<LinkedList<Integer>> buckets;
    public int bucketSize = 10;
    int size = 0;
    static final double COLLISION_CHANCE = 0.3; // load factor = size / bucketSize
    public int id;
    ArrayList<Set> table;

    public Set() {
        // this.id = this.hashCode() % bucketSize;
        this.id = getHash(this.hashCode(), bucketSize);

        buckets = new ArrayList<LinkedList<Integer>>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(i, new LinkedList<Integer>());
        }
        size = 0;
    }

    public void setTable(ArrayList<Set> table) {
        this.table = table;
    }

    public int size() {
        return size;
    }

    public boolean contains(int element) {
        int hash = getHash(element, bucketSize);

        Set chosenNode = findSuccessor(hash);

        LinkedList<Integer> curBucket = chosenNode.buckets.get(hash);

        if (curBucket == null) {
            return false;
        }

        return curBucket.contains(element);
    }

    public boolean insert(int element) {
        int hash = getHash(element, bucketSize);

        System.out.println(element + ": " + hash);
        Set chosenNode = findSuccessor(hash);
        System.out.println(chosenNode.id);
        System.out.println("++++++++++++++");

        return chosenNode.add(element);
    }

    public Set findSuccessor(int hash) {
        Set chosenNode = this;
        for (Set node : this.table) {
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
        return Integer.toString(element).chars().reduce(0, (ss, c) -> ss * 37 + c) % hashSize;
    }
}
