package interfaces;

public interface Avl_Tree_interface<T> {

    Node_interface<T> getRoot();

    void setRoot(Node_interface<T> root);

    void order();

    void insert(Integer key, T value);
    void remove(Integer key);
}
