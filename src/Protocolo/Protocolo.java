package Protocolo;

import common.Veiculo;
import common.huffman.Huffman;
import common.huffman.HuffmanData;
import interfaces.Node_interface;
import servidor.Servidor;

public class Protocolo {

    private Servidor servidor = new Servidor();

    /**
     * cadastrar veiculo
     * @param veiculo
     */
    public void enviarMensagem(Veiculo veiculo) {

        // comprimir a mensagem usando huffman

        Huffman huffman = new Huffman(veiculo.toString());

        // enviar o objeto huffmanData que contem a string comprimida e a frequencia de cada caracter
        HuffmanData huffmanData = new HuffmanData(huffman.getEncodedString(), huffman.getFreq());

        servidor.insertVeiculo(huffmanData);
    }

    /**
     * Alterar um veiculo de acordo com o seu renavam
     * @param renavam codigo do veiculo
     * @param veiculo veiculo
     * @return se o veiculo foi alterado ou nao
     */
    public boolean enviarMensagem(String renavam, Veiculo veiculo) {

        // comprimir a mensagem usando huffman
        Huffman renavamCompress = new Huffman(renavam);

        // enviar o objeto huffmanData que contem a string comprimida e a frequencia de cada caracter
        HuffmanData huffmanDataRenavam = new HuffmanData(renavamCompress.getEncodedString(), renavamCompress.getFreq());

        Huffman veiculoCompress = new Huffman(veiculo.toString());

        // enviar o objeto huffmanData que contem a string comprimida e a frequencia de cada caracter
        HuffmanData huffmanData = new HuffmanData(veiculoCompress.getEncodedString(), veiculoCompress.getFreq());

        return servidor.changeVeiculo(huffmanDataRenavam, huffmanData);
    }

    /**
     * remover ou buscar veiculo
     * @param renavam
     * @param remover
     * @return
     */
    public Veiculo receberMensagem (String renavam, boolean remover) {

        // comprimir a mensagem usando huffman
        Huffman renavamCompress = new Huffman(renavam);

        // enviar o objeto huffmanData que contem a string comprimida e a frequencia de cada caracter
        HuffmanData huffmanData = new HuffmanData(renavamCompress.getEncodedString(), renavamCompress.getFreq());

        if(remover) {

            // remover veiculo
            HuffmanData huffmanDataVeiculo = servidor.removeVeiculo(huffmanData);

            if(huffmanDataVeiculo == null) {
                return null;
            }

            // descomprimir a mensagem usando huffman
            Huffman veiculoDescompress = new Huffman(huffmanDataVeiculo.getEncodedString(), huffmanDataVeiculo.getFrequencies());

            // criar um objeto veiculo com a string descomprimida
            Veiculo veiculo = new Veiculo();
            veiculo.transformFromString(veiculoDescompress.getDecodedString());

            return veiculo;
        } else {

            //descomprimir mensagem para enviar ao cliente

            HuffmanData huffmanDataVeiculo = servidor.find(huffmanData);

            if(huffmanDataVeiculo == null) {
                return null;
            }

            // descomprimir a mensagem usando huffman
            Huffman veiculoDescompress = new Huffman(huffmanDataVeiculo.getEncodedString(), huffmanDataVeiculo.getFrequencies());

            // criar um objeto veiculo com a string descomprimida
            Veiculo veiculo = new Veiculo();
            veiculo.transformFromString(veiculoDescompress.getDecodedString());

            return veiculo;
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
    public Object[] receberMesagem() {
        return servidor.sendRoot();
    }

}
