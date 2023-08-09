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

    public void getNodeQuantity() {
        int q = 1;
        System.out.println(getNodeQuantity(this.getRoot(), q));
    }

    private int getNodeQuantity(Node_interface<T> node, int quantity) {
        if(node != null) {
            this.getNodeQuantity(node.getLeft(), quantity++);
            this.getNodeQuantity(node.getRight(), quantity++);
        }

        return quantity;
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

    private Node_interface<T> rebalance(Node_interface<T> node) {
        // atualizar a altura do ancestral do nó inserido
        updateHeight(node);

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
    public T remove(Integer key) {
        return remove(this.getRoot(), key).getValue();
    }

    private Node_interface<T> remove(Node_interface<T> node, Integer key) {
        if (node == null) {
            return node;
        }

        if(node.compareTo(key) > 0) {
            node.setLeft(this.remove(node.getLeft(), key));
        } else if(node.compareTo(key) < 0) {
            node.setRight(this.remove(node.getRight(), key));
        } else {

            // node with only one child or no child
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            }
            else {

                // node with two children: Get the inorder
                // successor (smallest innode right subtree)
                Node_interface<T> temp = mostLeftChild(node.getRight());

                // Copy the inorder successor's data to this node
                node.setKey(temp.getKey());

                // Delete the inorder successor
                node.setRight(remove(node.getRight(), temp.getKey()));
            }
        }

        if (node != null) {
            rebalance(node);
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

        return this.height(node.getLeft()) - this.height(node.getRight());
    }

    void updateHeight(Node_interface<T> node) {
        node.setHeightNode(1 + this.highestValue(
                        this.height(node.getLeft()),
                        this.height(node.getRight())
                )
        );
    }

    private Node_interface<T> simpleLeftRotation(Node_interface<T> node) {
        Node_interface<T> nodeRight = node.getRight();
        Node_interface<T> nodeLeft = nodeRight.getLeft();

        // executar a rotação

        nodeRight.setLeft(node);
        node.setRight(nodeLeft);

        updateHeight(node);
        updateHeight(nodeRight);

        return nodeRight;

    }

    private Node_interface<T> simpleRightRotation(Node_interface<T> node) {

        Node_interface<T> left = node.getLeft();
        Node_interface<T> right = left.getRight();

        // executa rotação

        left.setRight(node);
        node.setLeft(right);

        updateHeight(node);
        updateHeight(left);

        return left;
    }

    /*
     * Implementar a remoção de acordo com o código da prática 4
     */

}
