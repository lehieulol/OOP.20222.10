package algorithm.algorithm;
import java.util.ArrayList;
public class MinTerm {
    private int lengthOfBits;
    protected ArrayList<Integer> minTerms = new ArrayList<Integer>();

    private final String NameOfMinterms;

    public MinTerm(String nameOfMinterms, int lengthOfBits) {
        this.NameOfMinterms = nameOfMinterms;
        this.lengthOfBits = lengthOfBits;
        //set attribute minTerms as an arraylist
        for (int i = 0; i < lengthOfBits; i++) {
            int bit = Character.getNumericValue(convertMinterms().charAt(i));
            this.minTerms.add(bit);
        }
    }


    public String getNameOfMinterms() {
        return this.NameOfMinterms;
    }

    public ArrayList<Integer> getMinTerms() {
        return minTerms;
    }

    public int getLengthOfBits() {
        return lengthOfBits;
    }

    public String convertMinterms(){
        int decimalValue = Integer.parseInt(this.NameOfMinterms);
        String binaryValue = Integer.toBinaryString(decimalValue);

        // Pad with leading zeros to ensure the binary representation has the correct number of bits
        while (binaryValue.length() < this.lengthOfBits) {
            binaryValue = "0" + binaryValue;
        }
        return binaryValue;
    }

    public static void main(String[] args){
        MinTerm a = new MinTerm("0",5);
        System.out.println(a.convertMinterms());
        System.out.println(a.NameOfMinterms);
        System.out.println(a.getMinTerms());
        System.out.println(a.getLengthOfBits());
    }
}
