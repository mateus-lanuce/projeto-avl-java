package interfaces;

public interface Avl_Tree_interface<T> {

    Node_interface<T> getRoot();

    void setRoot(Node_interface<T> root);

    void order();

    void insert(Integer key, T value);

    void changeValue(Integer key, T value);
    void getNodeQuantity();
    T remove(Integer key);

    Node_interface<T> find(Integer key);
}
