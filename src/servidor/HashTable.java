package servidor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class HashTable<K, V> {

    /**
     * fator de balanceamento para decidir quando a tabela vai ser aumentada
     * utilizado 75% que é o mesmo que a implementação do hashmap do java usa
     * ou seja se a tabela tem 100 objetos ela vai ter pelo menos 75 buckets
     */
    private static final float loadFactor = 0.75F;
    private List<List<Node_hash<K, V>>> buckets;

    boolean createTextFile = true;

    private int count;

    public HashTable(int s) {
        this.buckets = new ArrayList<>(s);
        fill(this.buckets, s);
    }

    public V put(K key, V value) {
        throwIfNull(key);

        var entry = this.findOrCreateEntry(key);
        var oldValue = entry.getValue();
        entry.setValue(value);

        this.rehashIfNeeded();

        return oldValue;
    }

    private int bucketIndexFor(K key) {
        return Math.abs(key.hashCode() % this.buckets.size());
    }

    private Node_hash<K, V> findOrCreateEntry(K key) {
        var bucketIndex = this.bucketIndexFor(key);
        var bucket = this.buckets.get(bucketIndex);
        if (bucket == null) {
            bucket = new ArrayList<>();
            this.buckets.set(bucketIndex, bucket);
            writeLog("Inserção - novo Bucket adicionado com chave: " + bucketIndex);
        }

        for (var entry : bucket) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }

        var entry = new Node_hash<K, V>(key);
        bucket.add(entry);

        writeLog("Inserção - Valor adicionado com chave: " + key);

        this.count++;

        return entry;
    }

    private void rehashIfNeeded() {
        if (this.count > 0 && ((this.buckets.size() / (float) this.count) < loadFactor)) {
            var oldBuckets = this.buckets;
            this.count = 0;
            var capacity = oldBuckets.size() * 2;
            this.buckets = new ArrayList<>(capacity);
            fill(this.buckets, capacity);

            for (var bucket : oldBuckets) {
                if (bucket != null) {
                    for (var entry : bucket) {
                        this.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            writeLog("Rehash feito novo tamanho: " + capacity);
        }
    }

    static <E> void fill(List<E> items, int count) {
        for (int x = 0; x < count; x++) {
            items.add(null);
        }
    }

    static void throwIfNull(Object key) {
        if (key == null) {
            throw new NullPointerException("key must not be null");
        }
    }

    private Node_hash<K, V> findEntry(K key) {
        var bucket = this.findBucket(key);
        if (bucket == null || bucket.isEmpty()) {
            return null;
        }

        for (var entry : bucket) {
            if (key.equals(entry.getKey())) {
                return entry;
            }
        }

        return null;
    }

    private List<Node_hash<K, V>> findBucket(K key) {
        return this.buckets.get(this.bucketIndexFor(key));
    }

    public V get(K key) {
        throwIfNull(key);

        var entry = this.findEntry(key);

        if (entry != null) {
            return entry.getValue();
        }

        return null;
    }

    public boolean containsKey(K key) {
        throwIfNull(key);

        return this.findEntry(key) != null;
    }

    public V remove(K key) {
        throwIfNull(key);

        var bucket = this.findBucket(key);
        if (bucket == null || bucket.isEmpty()) {
            return null;
        }

        for (var it = bucket.iterator(); it.hasNext();) {
            var next = it.next();
            if (key.equals(next.getKey())) {
                it.remove();
                this.count--;
                writeLog("Remoção - Valor Removido com chave: " + next .getKey());
                return next.getValue();
            }
        }

        return null;
    }

    public int getCount() {
        return count;
    }

    // retornar um array com todos os valores da tabela
    public Object[] values() {
        Object[] values = new Object[this.count];
        int i = 0;
        for (List<Node_hash<K, V>> bucket : this.buckets) {
            if (bucket != null) {
                for (Node_hash<K, V> entry : bucket) {
                    values[i] = entry.getValue();
                    i++;
                }
            }
        }
        return values;
    }

    private void writeLog(String txt) {
        try {
            File arquivo = new File("output.txt");
            if (!arquivo.exists()) {
                //cria um arquivo (vazio)
                arquivo.createNewFile();
            } else if (createTextFile) {
                //ja existe um arquivo de log antigo então deve ser apagado
                arquivo.delete();
                arquivo.createNewFile();
                createTextFile = false;
            }

            //escreve no arquivo
            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(txt);
            bw.newLine();
            bw.close();
            fw.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
