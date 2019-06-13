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

    public V get(K key) {
        Node<K, V> node = baskets[(baskets.length - 1) & hash(key)];
        if (node.getKey().equals(key)) {
            return node.getValue();
        } else {
            while (node.getNext() != null) {
                node = node.getNext();
                if (node.getKey().equals(key)) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    private final int hash(K key) {
        return (key == null) ? 0 : (key.hashCode() ^ (key.hashCode() >>> 16));
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
