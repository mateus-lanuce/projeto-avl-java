package servidor;

import common.Veiculo;
import interfaces.Avl_Tree_interface;

public class Servidor {

    Avl_Tree_interface<Veiculo> veiculo_tree = new Avl_Tree<>();

    public void insertVeiculo(Veiculo veiculo) {
        veiculo_tree.insert(veiculo.getRenavam().hashCode(), veiculo);

    }

    public Veiculo removeVeiculo(int key) {
        return veiculo_tree.remove(key);
    }

    public void changeVeiculo(int key, Veiculo value) {
        veiculo_tree.changeValue(key, value);
    }



}
