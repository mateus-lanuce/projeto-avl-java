package servidor;

import common.Veiculo;
import interfaces.Avl_Tree_interface;
import interfaces.Node_interface;

public class Servidor {

    HashTable<String, Veiculo> veiculo_tree = new HashTable<>(16);

    public void insertVeiculo(Veiculo veiculo) {
        veiculo_tree.put(veiculo.getRenavam(), veiculo);
    }

    /**
     *
     * @param renavam
     * @return Veiculo || null
     */
    public Veiculo find(String renavam) {
        return veiculo_tree.get(renavam);
    }

    public Veiculo removeVeiculo(String renavam) {
        return veiculo_tree.remove(renavam);
    }

    public boolean changeVeiculo(String renavam, Veiculo value) {

        if (veiculo_tree.get(renavam) == null) {
            return false;
        } else {
            veiculo_tree.put(renavam, value);
            return true;
        }

    }

    public int getQuantidadeVeiculo() {
        return veiculo_tree.getCount();
    }

    public Object[] sendRoot() {
        return veiculo_tree.values();
    }

}
