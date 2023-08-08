package interfaces;

public interface Node_interface<T> extends Comparable<Integer> {
    int getHeightNode();
    void setHeightNode(int height);

    Integer getKey();

    void setKey(Integer key);

    T getValue();
    void setValue(T value);

    Node_interface<T> getLeft();
    void setLeft(Node_interface<T> esq);
    Node_interface<T> getRight();
    void setRight(Node_interface<T> dir);
}
