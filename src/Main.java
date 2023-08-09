import interfaces.Avl_Tree_interface;
import servidor.Avl_Tree;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Avl_Tree_interface<String> avl = new Avl_Tree<>();

        avl.insert(21, "21");
        avl.insert(24, "24");
        avl.insert(18, "18");
        avl.insert(19, "19");
        avl.insert(15, "15");

        avl.order();

        System.out.println(avl.find(15).getValue());

        avl.changeValue(19, "cleber");
        avl.remove(18);
        avl.order();

        System.out.println(avl.getNodeQuantity());


    }
}