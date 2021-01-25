package com.ot.jvm.day04.iter;

import java.util.HashMap;
import java.util.Iterator;

public class ArrayMap<K, V> implements Iterable<K> {
    private K[] keys;
    private V[] values;
    int size;
    int index;

    public ArrayMap() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }

    public void put(K key, V val) {
        keys[index] = key;
        values[index] = val;
        index++;
        size++;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
//        return null;
    }


    public class KeyIterator implements Iterator<K> {
        private int ptr;

        public KeyIterator() {
            ptr = 0;
        }

        @Override
        public boolean hasNext() {
            return (ptr != size);
        }

        @Override
        public K next() {
            K returnItem = keys[ptr];
            ptr += 1;
            return returnItem;
        }
    }

    public static void main(String[] args) {
        ArrayMap<String, Integer> am = new ArrayMap<>();
        am.put("hello", 5);
        am.put("syrups", 10);
        ArrayMap.KeyIterator keyIterator = (ArrayMap.KeyIterator) am.new KeyIterator();
        while (keyIterator.hasNext()) {
            System.out.println(keyIterator.next());
        }

        am.forEach(s-> System.out.println(s));
    }
}
