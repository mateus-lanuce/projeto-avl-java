package servidor;

import java.util.Map;
import java.util.Objects;

/**
 * interface do java utilizada para implementar o node de uma hashtable
 * no caso o Map no java ele é implementado em uma hashtable por isso pode ser usado
 */
public class Node_hash<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public Node_hash(K key, V value) {
        this(key);
        this.value = value;
    }

    public Node_hash(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can't be null");
        }
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node_hash)) return false;
        Node_hash<?, ?> entry = (Node_hash<?, ?>) o;
        return key.equals(entry.key) &&
                Objects.equals(value, entry.value);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        var oldValue = this.value;
        this.value = value;
        return oldValue;
    }


}
