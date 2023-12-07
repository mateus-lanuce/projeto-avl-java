package servidor;

import common.Veiculo;
import common.huffman.Huffman;
import common.huffman.HuffmanData;

public class Servidor {

    HashTable<String, Veiculo> veiculo_tree = new HashTable<>(5);

    public void insertVeiculo(HuffmanData veiculoCompress) {

        Huffman descompress = new Huffman(veiculoCompress.getEncodedString(), veiculoCompress.getFrequencies());

        Veiculo veiculo = new Veiculo();
        veiculo.transformFromString(descompress.getDecodedString());

        veiculo_tree.put(veiculo.getRenavam(), veiculo);
    }

    /**
     *
     * @param renavam
     * @return Veiculo || null
     */
    public HuffmanData find(HuffmanData renavam) {

        Huffman descompress = new Huffman(renavam.getEncodedString(), renavam.getFrequencies());

        String renavamString = descompress.getDecodedString();

        Veiculo veiculo = veiculo_tree.get(renavamString);

        if (veiculo == null) {
            return null;
        } else {
            Huffman veiculoCompress = new Huffman(veiculo.toString());
            return new HuffmanData(veiculoCompress.getEncodedString(), veiculoCompress.getFreq());
        }
    }

    public HuffmanData removeVeiculo(HuffmanData renavam) {

        Huffman descompress = new Huffman(renavam.getEncodedString(), renavam.getFrequencies());

        String renavamString = descompress.getDecodedString();

        Veiculo veiculoRemovido = veiculo_tree.remove(renavamString);

        // comprimir a mensagem usando huffman
        Huffman veiculoCompress = new Huffman(veiculoRemovido.toString());

        // enviar o objeto huffmanData que contem a string comprimida e a frequencia de cada caracter

        return new HuffmanData(veiculoCompress.getEncodedString(), veiculoCompress.getFreq());
    }

    public boolean changeVeiculo(HuffmanData renavam, HuffmanData veiculoCompress) {

        Huffman descompressRenavam = new Huffman(renavam.getEncodedString(), renavam.getFrequencies());

        String renavamString = descompressRenavam.getDecodedString();

        Huffman descompress = new Huffman(veiculoCompress.getEncodedString(), veiculoCompress.getFrequencies());

        Veiculo value = new Veiculo();
        value.transformFromString(descompress.getDecodedString());

        if (veiculo_tree.get(renavamString) == null) {
            return false;
        } else {
            veiculo_tree.put(renavamString, value);
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
