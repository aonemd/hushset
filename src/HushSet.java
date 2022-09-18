package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class HushSet {
    public ArrayList<LinkedList<Integer>> buckets;
    public int bucketSize = 10;
    int size = 0;
    static final double COLLISION_CHANCE = 0.3; // load factor = size / bucketSize

    public HushSet() {
        buckets = new ArrayList<LinkedList<Integer>>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(i, new LinkedList<Integer>());
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(int element) {
        int hash = getHash(element, bucketSize);

        LinkedList<Integer> curBucket = buckets.get(hash);

        if (curBucket == null) {
            return false;
        }

        return curBucket.contains(element);
    }

    public boolean insert(int element) {
        return this.add(element);
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


    public static void main(String[] args) {
        HushSet hs = new HushSet();
        hs.insert(11);
        hs.insert(21);
        hs.insert(12);
        // hs.insert(13);
        // hs.insert(14);

        // System.out.println(hs.getHash(11, 10));

        System.out.println(hs.size());
        System.out.println(hs.bucketSize);
        for (LinkedList<Integer> bb : hs.buckets) {
            System.out.println(bb.toString());
            // for (Integer it : bb) {
            //     System.out.print(it + ":" + hs.getHash(it, hs.bucketSize)+ ", ");
            // }
            // if (!bb.isEmpty())
            //     System.out.println();
        }
    }
}
