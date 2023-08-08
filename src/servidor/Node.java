package servidor;

import interfaces.Node_interface;

public class Node<T> implements Node_interface<T> {

    Integer key;
    T value;
    Integer HeightNode;
    Node_interface<T> left, right;

    public Node(Integer key, T value) {
        this.setKey(key);
        this.setValue(value);
        this.setHeightNode(0);
        this.setLeft(null);
        this.setRight(null);
    }

    @Override
    public int getHeightNode() {
        return this.HeightNode;
    }

    @Override
    public void setHeightNode(int height) {
        this.HeightNode = height;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Node_interface<T> getLeft() {
        return this.left;
    }

    @Override
    public void setLeft(Node_interface<T> left) {
        this.left = left;
    }

    @Override
    public Node_interface<T> getRight() {
        return this.right;
    }

    @Override
    public void setRight(Node_interface<T> right) {
        this.right = right;
    }

    @Override
    public int compareTo(Integer o) {
        return this.getKey().compareTo(o);

    }
}
