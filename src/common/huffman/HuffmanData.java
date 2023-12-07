package common.huffman;

public class HuffmanData {
    private String encodedString;
    private int[] frequencies;

    public HuffmanData(String encodedString, int[] frequencies) {
        this.encodedString = encodedString;
        this.frequencies = frequencies;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public int[] getFrequencies() {
        return frequencies;
    }
}
