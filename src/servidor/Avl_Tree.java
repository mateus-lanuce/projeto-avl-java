package servidor;

import interfaces.Avl_Tree_interface;
import interfaces.Node_interface;

public class Avl_Tree<T> implements Avl_Tree_interface<T> {

    Node_interface<T> root;

    public Avl_Tree() {
        this.setRoot(null);
    }

    @Override
    public Node_interface<T> getRoot() {
        return this.root;
    }

    @Override
    public void setRoot(Node_interface<T> root) {
        this.root = root;
    }

    @Override
    public void order() {
        this.order(this.getRoot());
    }

    private void order(Node_interface<T> node) {
        if(node != null) {
            this.order(node.getLeft());
            System.out.println(node.getKey() + ": " + node.getValue());
            this.order(node.getRight());
        }
    }

    @Override
    public void insert(Integer key, T value) {
        this.root = this.insert(getRoot(), key, value);
    }


    @Override
    public Node_interface<T> find(Integer key) {
        //percorrer os nós recursivamente partindo da raiz
        return find(this.getRoot(), key);
    }

    public void changeValue(Integer key, T value) {
        Node_interface<T> node = find(key);
        node.setValue(value);
    }

    /**
     * Função que conta e retorna a quantidade de nós em uma árvore binária
     */
    public int getNodeQuantity() {
        return quantidade_nos(this.getRoot());
    }


    private int quantidade_nos(Node_interface<T> root){
        if(root == null)
            return 0;
        else
            return 1 + quantidade_nos(root.getLeft()) + quantidade_nos(root.getRight());
    }

    private Node_interface<T> find(Node_interface<T> node, Integer key) {
        if (node == null) {
            System.out.println("Nó não encontrado");
            return null;
        }

        if(node.compareTo(key) == 0) {
            return node;
        } else {
            // pesquisar na subarvore esquerda
            if (node.compareTo(key) > 0) {
               return find(node.getLeft(), key);
            } else if (node.compareTo(key) < 0){
                //pesquisar na direita
                return find(node.getRight(), key);
            }
        }

        return node;
    }



    private Node_interface<T> insert(Node_interface<T> node, Integer key, T value) {
        if(node == null) return new Node<T>(key, value);

        //insercao recursiva
        if(node.compareTo(key) > 0) {
            node.setLeft(this.insert(node.getLeft(), key, value));
        } else if(node.compareTo(key) < 0) {
            node.setRight(this.insert(node.getRight(), key, value));
        } else {
            throw new RuntimeException("Chave duplicada!");
        }

        return rebalance(node);
    }

    private Node_interface<T> rebalance(Node_interface<T> z) {
        // atualizar a altura do ancestral do nó inserido
        updateHeight(z);

        // obter o fator de balanceamento
        int fBalance = getBalanceFactor(z);

        if (fBalance > 1) {

            if(height(z.getRight().getRight()) > height(z.getRight().getLeft())) {
                z = simpleLeftRotation(z);
            } else {
                z.setRight(simpleRightRotation(z.getRight()));
                z = simpleLeftRotation(z);
            }

        } else if(fBalance < 1) {

            if(height(z.getLeft().getLeft()) > height(z.getLeft().getRight())) {
                z = simpleRightRotation(z);
            } else {
                z.setLeft(simpleLeftRotation(z.getLeft()));
                z = simpleRightRotation(z);
            }

        }

        return z;

    }

    @Override
    public T remove(Integer key) {
        return remove(this.getRoot(), key).getValue();
    }

    private Node_interface<T> remove(Node_interface<T> node, Integer key) {
        if (node == null) {
            return node;
        } else if (node.compareTo(key) > 0) {
            node.setLeft(remove(node.getLeft(), key));
        } else if (node.compareTo(key) < 0) {
            node.setRight(remove(node.getRight(), key));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            } else {
                Node_interface<T> mostLeftChild = mostLeftChild(node.getRight());
                node.setKey(mostLeftChild.getKey());
                node.setValue(mostLeftChild.getValue());
                node.setRight(remove(node.getRight(), node.getKey()));
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    Node_interface<T> mostLeftChild(Node_interface<T> node)
    {
        Node_interface<T> current = node;

        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();

        return current;
    }

    private Integer height(Node_interface<T> node) {
        if (node == null) return -1;

        return node.getHeightNode();
    }

    private Integer highestValue(Integer a, Integer b) {
        return (a > b) ? a : b;
    }

    private Integer getBalanceFactor(Node_interface<T> node) {
        if(node == null) return 0;

        return this.height(node.getRight()) - this.height(node.getLeft());
    }

    void updateHeight(Node_interface<T> node) {
        node.setHeightNode(1 + this.highestValue(
                        this.height(node.getLeft()),
                        this.height(node.getRight())
                )
        );
    }

    private Node_interface<T> simpleLeftRotation(Node_interface<T> y) {
        Node_interface<T> x = y.getRight();
        Node_interface<T> z = x.getLeft();

        // executar a rotação

        x.setLeft(y);
        y.setRight(z);

        updateHeight(y);
        updateHeight(x);

        return x;

    }

    private Node_interface<T> simpleRightRotation(Node_interface<T> y) {

        Node_interface<T> x = y.getLeft();
        Node_interface<T> z = x.getRight();

        // executa rotação

        x.setRight(y);
        y.setLeft(z);

        updateHeight(y);
        updateHeight(x);

        return x;

    }

    /*
     * Implementar a remoção de acordo com o código da prática 4
     */

}
