import java.util.*;

public class OwnHashMap<K, V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float MAX_LOAD = 0.75f;

    private int size = 0;
    private float loaded = 0;
    private Node<K, V>[] baskets;

    public void put(K key, V value) {
        if (baskets == null) {
            baskets = new Node[INITIAL_CAPACITY];

        }
        if (loaded >= MAX_LOAD) {
            resize();
        }
        putNode(key, value, baskets);
    }

    public V get(K key) {
        try {
            return getNode(key).getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public V remove(K key) {
        int i = (baskets.length - 1) & hash(key);
        Node<K, V> node = baskets[i];
        if (node.getKey().equals(key)) {
            baskets[i] = null;
            return node.getValue();
        } else {
            Node<K, V> previosNode = node;
            while (node.getNext() != null) {
                node = node.getNext();
                if (node.getKey().equals(key)) {
                    previosNode.setNext(node.getNext());
                    node.setNext(null);
                    return node.value;
                }
            }
        }
        return null;
    }

    public Set<K> getKeySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < baskets.length - 1; i++) {
            if (baskets[i] != null) {
                keys.add(baskets[i].getKey());
                Node<K, V> node = baskets[i];
                while (baskets[i].getNext() != null) {
                    node = node.getNext();
                    keys.add(node.getKey());
                }
            }
        }
        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>(size);
        for (int i = 0; i < baskets.length - 1; i++) {
            if (baskets[i] != null) {
                values.add(baskets[i].getValue());
                Node<K, V> node = baskets[i];
                while (baskets[i].getNext() != null) {
                    node = node.getNext();
                    values.add(node.getValue());
                }
            }
        }
        return  values;
    }

    public void putAll(OwnHashMap<K, V> map) {
        for (K key : map.getKeySet()) {
            if (key != null) {
                put(key, map.get(key));
            }
        }
    }

    public boolean containsKey(K key) {
        try {
            return getNode(key) == null;
        } catch (NullPointerException e) {
            return true;
        }
    }

    public boolean containsValue(K key) {
        try {
            return getNode(key).getValue() == null;
        } catch (NullPointerException e) {
            return true;
        }
    }

    public void clear() {
        baskets = null;
        size = 0;
        loaded = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(K key) {
        return (key == null) ? 0 : (key.hashCode() ^ (key.hashCode() >>> 16));
    }

    private Node<K, V> getNode(K key) {
        Node<K, V> node = baskets[(baskets.length - 1) & hash(key)];
        if (node.getKey().equals(key)) {
            return node;
        } else {
            while (node.getNext() != null) {
                node = node.getNext();
                if (node.getKey().equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    private void resize() {
        loaded = 0;
        size = 0;
        Node<K, V>[] resizeBaskets = new Node[baskets.length * 2];
        for (int n = 0; n < baskets.length; n++) {
            if (baskets[n] != null) {
                Node<K, V> node = baskets[n];
                if (node.getNext() == null) {
                    putNode(node.getKey(), node.getValue(), resizeBaskets);
                } else {
                    while (node.getNext() != null) {
                        putNode(node.getKey(), node.getValue(), resizeBaskets);
                        node = node.getNext();
                    }
                }
            }
        }
        baskets = resizeBaskets;
    }

    private void putNode(K key, V value, Node<K, V>[] basket) {
        int i = (basket.length - 1) & hash(key);
        Node<K, V> node = new Node<>(key, value, hash(key), null);
        if (basket[i] == null) {
            basket[i] = node;
            loaded += (float) 1 / (basket.length - 1);
        } else {
            if (key.equals(basket[i].getKey())) {
                basket[i].setValue(value);
            } else {
                Node<K, V> basketNode = basket[i];
                while (basketNode.getNext() != null) {
                    basketNode = basketNode.next;
                    if (hash(key) == basket[i].getHash()) {
                        basket[i].setValue(value);
                        return;
                    }
                }
                basketNode.setNext(node);
            }
        }
        size++;
    }

    private static class Node<K, V> {

        private Node<K, V> next;
        private final K key;
        private V value;
        private final int hash;

        Node(K key, V value, int hash, Node<K, V> next) {
            this.next = next;
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        private int getHash() {
            return this.hash;
        }

        private Node<K, V> getNext() {

            return next;
        }

        private void setNext(Node<K, V> next) {
            this.next = next;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }

    }
}
