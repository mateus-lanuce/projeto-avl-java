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
    public int getHeightTree() {
        return this.root.getHeightNode();
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

    public boolean changeValue(Integer key, T value) {
        Node_interface<T> node = find(key);

        if (node != null) {
            node.setValue(value);
            return true;
        } else {
            return false;
        }

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
        int balanceFactor = getBalanceFactor(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.getLeft()) <= 0) {
                // Rotate right
                node = simpleRightRotation(node);
            } else {
                // Rotate left-right
                node.setLeft(simpleLeftRotation(node.getLeft()));
                node = simpleRightRotation(node);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (getBalanceFactor(node.getRight()) >= 0) {
                // Rotate left
                node = simpleLeftRotation(node);
            } else {
                // Rotate right-left
                node.setRight(simpleRightRotation(node.getRight()));
                node = simpleLeftRotation(node);
            }
        }

        return node;
    }

    @Override
    public T remove(Integer key) {

        Node_interface<T> temp = remove(this.getRoot(), key);

        return temp == null ? null : temp.getValue();
    }

    private Node_interface<T> remove(Node_interface<T> node, Integer key) {
        node = deleteNode(node, key);

        // Node is null if the tree doesn't contain the key
        if (node == null) {
            return null;
        }

        updateHeight(node);

        return rebalance(node);
    }

    private Node_interface<T> deleteNode(Node_interface<T> node, int key) {
        // No node at current position --> go up the recursion
        if (node == null) {
            return null;
        }

        // Traverse the tree to the left or right depending on the key
        if (node.compareTo(key) > 0) {
            node.setLeft(remove(node.getLeft(), key));
        } else if (node.compareTo(key) < 0) {
            node.setRight(remove(node.getRight(), key));
        } else if (node.getLeft() == null && node.getRight() == null) {
            // At this point, "node" is the node to be deleted
            // Node has no children --> just delete it
            node = null;
        }

        // Node has only one child --> replace node by its single child
        else if (node.getLeft() == null) {
            node = node.getRight();
        } else if (node.getRight() == null) {
            node = node.getLeft();
        }

        // Node has two children
        else {
            deleteNodeWithTwoChildren(node);
        }

        return node;
    }

    private void deleteNodeWithTwoChildren(Node_interface<T> node) {
        // Find minimum node of right subtree ("inorder successor" of current node)
        Node_interface<T> inOrderSuccessor = mostLeftChild(node.getRight());

        // Copy inorder successor's data to current node
        node.setValue(inOrderSuccessor.getValue());
        node.setKey(inOrderSuccessor.getKey());

        // Delete inorder successor recursively
        node.setRight(remove(node.getRight(), inOrderSuccessor.getKey()));
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
        return node == null ? -1 : node.getHeightNode();
    }

    private Integer getBalanceFactor(Node_interface<T> node) {
        return height(node.getRight()) - height(node.getLeft());
    }

    void updateHeight(Node_interface<T> node) {
        int leftChildHeight = height(node.getLeft());
        int rightChildHeight = height(node.getRight());
        node.setHeightNode(Math.max(leftChildHeight, rightChildHeight) + 1);
    }

    private Node_interface<T> simpleLeftRotation(Node_interface<T> node) {
        Node_interface<T> rightChild = node.getRight();

        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;

    }

    private Node_interface<T> simpleRightRotation(Node_interface<T> node) {

        Node_interface<T> leftChild = node.getLeft();

        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;

    }

}
