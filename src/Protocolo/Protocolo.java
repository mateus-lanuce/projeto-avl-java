package Protocolo;

import common.Veiculo;
import interfaces.Node_interface;
import servidor.Servidor;

public class Protocolo {

    private Servidor servidor = new Servidor();

    /**
     * cadastrar veiculo
     * @param veiculo
     */
    public void enviarMensagem(Veiculo veiculo) {
        servidor.insertVeiculo(veiculo);
    }

    /**
     * Alterar um veiculo de acordo com o seu renavam
     * @param renavam codigo do veiculo
     * @param veiculo veiculo
     * @return se o veiculo foi alterado ou nao
     */
    public boolean enviarMensagem(String renavam, Veiculo veiculo) {
        return servidor.changeVeiculo(renavam, veiculo);
    }

    /**
     * remover ou buscar veiculo
     * @param renavam
     * @param remover
     * @return
     */
    public Veiculo receberMensagem (String renavam, boolean remover) {
        if(remover) {
            return servidor.removeVeiculo(renavam);
        } else {
            return servidor.find(renavam);
        }
    }

    /**
     * pegar a quantidade de veiculos
     * @return
     */
    public int receberMensagem() {
        return servidor.getQuantidadeVeiculo();
    }

    /**
     * pegar a raiz da arvoré
     * @return
     */
    public Node_interface<Veiculo> receberMesagem() {
        return servidor.sendRoot();
    }

}
