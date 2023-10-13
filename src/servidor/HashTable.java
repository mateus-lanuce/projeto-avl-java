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

        // Atualize o fator de carga sempre que um novo elemento for adicionado
        writeLoadFactor();

        return oldValue;
    }

    private int bucketIndexFor(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % this.buckets.size());

        int i = 1;
        while (this.buckets.get(index) != null) {
            index = (index + i * i) % this.buckets.size();
            i++;
        }

        return index;
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
        // Verifica se a taxa de carga excede o limite e é necessário rehash
        if (this.count > 0 && ((float) this.count / this.buckets.size()) > loadFactor) {
            // Salva os buckets antigos
            var oldBuckets = this.buckets;
            // Zera a contagem de elementos
            this.count = 0;
            // Calcula o novo tamanho da tabela como o próximo número primo após a capacidade anterior
            int newCapacity = getNextPrime(oldBuckets.size());
            // Cria uma nova lista de buckets com o novo tamanho
            this.buckets = new ArrayList<>(newCapacity);
            // Preenche a nova lista de buckets com null
            fill(this.buckets, newCapacity);

            // Reinsere os elementos da tabela antiga na nova tabela
            for (var bucket : oldBuckets) {
                if (bucket != null) {
                    for (var entry : bucket) {
                        this.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            // Registra a operação de rehash no log
            writeLog("Rehash feito novo tamanho: " + newCapacity);
        }
    }

    private int getNextPrime(int n) {
        // Encontra o próximo número primo após n
        for (int i = n + 1; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false; // Números menores ou iguais a 1 não são primos
        }
        if (n <= 3) {
            return true; // 2 e 3 são primos
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false; // Divisível por 2 ou 3 não é primo
        }
        // Verifica se n é divisível por números da forma 6k ± 1
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false; // Não é primo se divisível por i ou i + 2
            }
        }
        return true; // Se não foi divisível por nenhum dos casos acima, é primo
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
                writeLoadFactor();
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

    private void writeLoadFactor() {
        float currentLoadFactor = (float) this.count / this.buckets.size();
        writeLog("Fator de Carga: " + currentLoadFactor);
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
