package interfaces;

public interface Avl_Tree_interface<T> {

    Node_interface<T> getRoot();

    void setRoot(Node_interface<T> root);

    void order();

    void insert(Integer key, T value);

    boolean changeValue(Integer key, T value);
    int getNodeQuantity();

    int getHeightTree();
    T remove(Integer key);

    Node_interface<T> find(Integer key);
}
