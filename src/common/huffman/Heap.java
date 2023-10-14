package common.huffman;

public class Heap {
        private Node[] heap;  // Array para armazenar os elementos do heap
        private int size = 0; // Tamanho atual do heap

        public int getSize() {
                return size;
        }

        public Heap(int i) {
                heap = new Node[i]; // Inicializa o array do heap com o tamanho especificado
        }

        // Insere um nó no heap
        public void insert(Node k) {
                size++;
                int i = size;
                while (i > 1 && heap[parent(i)].getFreq() > k.getFreq()) {
                        heap[i] = heap[parent(i)];
                        i = parent(i);
                }
                heap[i] = k;
        }

        // Obtém o nó com o valor mínimo no heap
        public Node getMin() {
                if (size != 0)
                        return heap[1];
                return null;
        }

        // Remove e retorna o nó com o valor mínimo no heap
        public Node delMin() {
                if (size != 0) {
                        Node min = heap[1];
                        heap[1] = heap[size];
                        size--;
                        heapify(1);
                        return min;
                }
                return null;
        }

        /**
         * Mantém a propriedade do Heap Mínimo: A[PARENT(i)] <= A[i]
         * @param i Raiz da árvore a ser heapificada
         */
        public void heapify(int i) {
                int l = left(i);
                int r = right(i);
                int smallest;

                if (r <= size) {
                        if (heap[l].getFreq() < heap[r].getFreq())
                                smallest = l;
                        else
                                smallest = r;

                        if (heap[i].getFreq() > heap[smallest].getFreq()) {
                                swap(i, smallest);
                                heapify(smallest);
                        }
                } else if (l == size && heap[i].getFreq() > heap[l].getFreq()) {
                        swap(i, l);
                }
        }

        // Troca dois elementos no heap
        private void swap(int i, int l) {
                Node tmp = heap[i];
                heap[i] = heap[l];
                heap[l] = tmp;
        }

        // Retorna o índice do pai de um elemento no heap
        public int parent(int i) {
                return i / 2;
        }

        // Retorna o índice do filho esquerdo de um elemento no heap
        public int left(int i) {
                return 2 * i;
        }

        // Retorna o índice do filho direito de um elemento no heap
        public int right(int i) {
                return 2 * i + 1;
        }
}
