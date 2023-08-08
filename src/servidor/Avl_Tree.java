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

    private Node_interface<T> insert(Node_interface<T> node, Integer key, T value) {
        if(node == null) return new Node<T>(key, value);

        //insercao recursiva
        if(node.compareTo(key) > 0) {
            node.setLeft(this.insert(node.getLeft(), key, value));
        } else if(node.compareTo(key) < 0) {
            node.setRight(this.insert(node.getRight(), key, value));
        } else {
            return node;
        }

        // atualizar a altura do ancestral do nó inserido
        node.setHeightNode(1 + this.highestValue(
                    this.height(node.getLeft()),
                    this.height(node.getRight())
                )
        );

        // obter o fator de balanceamento
        int balanceFactor = this.getBalanceFactor(node);
        int leftBalanceFactor = this.getBalanceFactor(node.getLeft());
        int rightBalanceFactor = this.getBalanceFactor(node.getRight());

        if(balanceFactor > 1 && leftBalanceFactor >= 0)
            return this.simpleRightRotation(node);

        if(balanceFactor > 1 && leftBalanceFactor < 0) {
            node.setLeft(this.simpleLeftRotation(node.getLeft()));
            return this.simpleRightRotation(node);
        }

        if(balanceFactor < -1 && rightBalanceFactor <= 0)
            return this.simpleLeftRotation(node);

        if(balanceFactor < -1 && rightBalanceFactor > 0) {
            node.setRight(this.simpleRightRotation(node.getRight()));
            return this.simpleLeftRotation(node);
        }

        return node;
    }

    @Override
    public void remove(Integer key) {

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

        return this.height(node.getLeft()) - this.height(node.getRight());
    }

    private Node_interface<T> simpleLeftRotation(Node_interface<T> node) {
        Node_interface<T> nodeRight = node.getRight();
        Node_interface<T> nodeLeft = nodeRight.getLeft();

        // executar a rotação

        nodeRight.setLeft(node);
        node.setRight(nodeLeft);

        node.setHeightNode(1 + this.highestValue(
                this.height(node.getLeft()),
                this.height(node.getRight())
        ));
        nodeRight.setHeightNode(1 + this.highestValue(
                this.height(nodeRight.getLeft()),
                this.height((nodeRight.getRight()))
        ));

        return nodeRight;

    }

    private Node_interface<T> simpleRightRotation(Node_interface<T> node) {

        Node_interface<T> left = node.getLeft();
        Node_interface<T> right = left.getRight();

        // executa rotação

        left.setRight(node);
        node.setLeft(right);;

        node.setHeightNode( 1 + this.highestValue(height(node.getLeft()), height(node.getRight())));
        left.setHeightNode( 1 + this.highestValue(height(left.getLeft()), height(left.getRight())));

        return left;
    }

    /*
     * Implementar a remoção de acordo com o código da prática 4
     */

}
