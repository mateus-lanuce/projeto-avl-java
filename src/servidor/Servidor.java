package servidor;

import common.Veiculo;
import interfaces.Avl_Tree_interface;
import interfaces.Node_interface;

public class Servidor {

    Avl_Tree_interface<Veiculo> veiculo_tree = new Avl_Tree<>();

    public void insertVeiculo(Veiculo veiculo) {
        veiculo_tree.insert(veiculo.getRenavam().hashCode(), veiculo);
    }

    /**
     *
     * @param renavam
     * @return Veiculo || null
     */
    public Veiculo find(String renavam) {
        return veiculo_tree.find(renavam.hashCode()).getValue();
    }

    public Veiculo removeVeiculo(String renavam) {
        return veiculo_tree.remove(renavam.hashCode());
    }

    public boolean changeVeiculo(String renavam, Veiculo value) {
        return veiculo_tree.changeValue(renavam.hashCode(), value);
    }

    public int getQuantidadeVeiculo() {
        return veiculo_tree.getNodeQuantity();
    }

    public Node_interface<Veiculo> sendRoot() {
        return veiculo_tree.getRoot();
    }

}
