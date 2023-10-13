package common.huffman;

public class Huffman {

    /**
     * Número de bytes no arquivo descompactado
     */
    int count;

    public int[] getFreq() {
        return freq;
    }

    /**
     * i = byte e freq[i] = frequência desse byte
     */
    int[] freq = new int[256];

    /**
     * Após a geração do código Huffman, heap.getMin() é a raiz da árvore de Huffman
     */
    Heap heap;

    /**
     * Número de símbolos diferentes na string de entrada
     */
    int numberOfSymbols;

    /**
     * i = byte e symbolTable[i] = representação de string ótima do código de prefixo para o byte.
     */
    String[] symbolTable = new String[256];

    private String inputString;
    private String encodedString;

    /**
     * Construtor para criar uma instância Huffman com a string de entrada.
     */
    public Huffman(String inputString) {
        this.inputString = inputString;
        code(inputString);
    }

    /**
     * Construtor para criar uma instância Huffman com a string codificada.
     */
    public Huffman(String encodedString, int[] frequencies) {
        this.encodedString = encodedString;
        freq = frequencies;
        generateTree();
        generateCode(heap.getMin(), "");
    }

    /**
     * Define a string codificada em um objeto Huffman existente.
     *
     * @param encodedString A string codificada.
     */
    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }

    /**
     * Obtém a string codificada.
     *
     * @return A string codificada.
     */
    public String getEncodedString() {
        return encodedString;
    }

    /**
     * Comprime uma string de entrada usando a codificação Huffman.
     *
     * @param inputString A string de entrada a ser codificada em Huffman.
     */
    private void code(String inputString) {
        getFrequencies(inputString);
        generateTree();
        generateCode(heap.getMin(), "");

        // Cria a string codificada
        encodedString = encodeString(inputString);
    }

    /**
     * Calcula as frequências de caracteres diferentes na string de entrada.
     *
     * @param inputString A string de entrada
     */
    private void getFrequencies(String inputString) {
        for (char c : inputString.toCharArray()) {
            freq[c]++;
            count++;
        }
    }

    // O restante dos métodos (generateTree, createHeap, createTree, generateCode, writeFile, etc.) permanece inalterado.

    /**
     * Cria a árvore de prefixo ótima.
     */
    private void generateTree() {
        createHeap(freq);
        numberOfSymbols = heap.getSize();
        heap = createTree(heap);
    }

    /**
     * Cria um heap mínimo usando a tabela de frequência fornecida.
     * @param freq Tabela de frequência de caracteres
     */
    private void createHeap(int[] freq) {
        heap = new Heap(257);
        for(int i = 0; i < freq.length; ++i) {
            if(freq[i] != 0) {
                Node node = new Node(freq[i], i);
                node.setToLeaf();
                heap.insert(node);
            }
        }
    }

    /**
     * Algoritmo de Huffman
     * @param heap Heap mínimo
     * @return Árvore de Huffman
     */
    private Heap createTree(Heap heap) {
        int n = heap.getSize();
        for(int i = 0; i < (n-1); ++i) {
            Node z = new Node();
            z.setLeft(heap.delMin());
            z.setRight(heap.delMin());
            z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq());
            heap.insert(z);
        }
        return heap;
    }

    /**
     * Gera códigos de prefixo para todos os caracteres no arquivo.
     * @param node Raiz da árvore de Huffman
     * @param code forneça uma string vazia para calcular o código apropriado
     */
    private void generateCode(Node node, String code) {
        if(node != null) {
            if(node.isLeaf())
                symbolTable[node.getKey()] = code;
            else {
                generateCode(node.getLeft(), code + "0");
                generateCode(node.getRight(), code + "1");
            }
        }
    }

    /**
     * Exibe a string codificada usando a tabela de símbolos Huffman.
     */
    public void displayEncodedString() {
        StringBuilder encodedString = new StringBuilder();
        for (char c : inputString.toCharArray()) {
            encodedString.append(symbolTable[c]);
        }
    }

    /**
     * Codifica uma string usando a tabela de símbolos Huffman.
     *
     * @param str A string a ser codificada.
     * @return A string codificada.
     */
    private String encodeString(String str) {
        StringBuilder encoded = new StringBuilder();
        for (char c : str.toCharArray()) {
            encoded.append(symbolTable[c]);
        }
        return encoded.toString();
    }

    /**
     * Decodifica a string codificada usando a árvore de Huffman e exibe a string decodificada.
     */
    public void displayDecodedString() {
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = heap.getMin();
        for (int i = 0; i < encodedString.length(); i++) {
            if (currentNode.isLeaf()) {
                decodedString.append((char) currentNode.getKey());
                currentNode = heap.getMin();
            }
            char bit = encodedString.charAt(i);
            if (bit == '0') {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }
        // Adicione o último caractere, pois a codificação pode não terminar em uma folha.
        if (currentNode.isLeaf()) {
            decodedString.append((char) currentNode.getKey());
        }
        System.out.println("String Decodificada: " + decodedString.toString());
    }

    public String getDecodedString() {
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = heap.getMin();
        for (int i = 0; i < encodedString.length(); i++) {
            if (currentNode.isLeaf()) {
                decodedString.append((char) currentNode.getKey());
                currentNode = heap.getMin();
            }
            char bit = encodedString.charAt(i);
            if (bit == '0') {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }
        }
        // Adicione o último caractere, pois a codificação pode não terminar em uma folha.
        if (currentNode.isLeaf()) {
            decodedString.append((char) currentNode.getKey());
        }
        return decodedString.toString();
    }
}
