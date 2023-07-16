package algorithm;
import java.util.ArrayList;
public class MinTerm {
    private int lengthOfBits;
    protected ArrayList<Integer> minTerms = new ArrayList<Integer>();
    public final int minterm;
    public final int noOfOnes;
    public final String NameOfMinterms;
    

    public MinTerm(String nameOfMinterms, int lengthOfBits) {
        this.NameOfMinterms = nameOfMinterms;
        this.lengthOfBits = lengthOfBits;
        //set attribute minTerms as an arraylist
        for (int i = 0; i < lengthOfBits; i++) {
            int bit = Character.getNumericValue(convertMinterms().charAt(i));
            this.minTerms.add(bit);
        }
        this.minterm = Integer.parseInt(this.NameOfMinterms);
        this.minTerms.add(minterm);
        this.noOfOnes = this.noOfOnes(this.minTerms);
    }
    
    public MinTerm(int minterm, int lengthOfBits) {
        this.minterm = minterm;
        this.lengthOfBits = lengthOfBits;
        //set attribute minTerms as an arraylist
        this.NameOfMinterms = Integer.toString(this.minterm);
        for (int i = 0; i < lengthOfBits; i++) {
            int bit = Character.getNumericValue(convertMinterms().charAt(i));
            this.minTerms.add(bit);
        }
        this.minTerms.add(minterm);
        this.noOfOnes = this.noOfOnes(this.minTerms);
    }
    
    
    public int noOfOnes(ArrayList<Integer> num) {
		int count = 0;
		for (int i=0; i<lengthOfBits; i++) {
			if (num.get(i) == 1) {
				count ++;
			}
		}
		return count;
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
        MinTerm a = new MinTerm("14",4);
        System.out.println(a.convertMinterms());
        System.out.println(a.NameOfMinterms);
        System.out.println(a.getMinTerms());
        System.out.println(a.getLengthOfBits());
        System.out.println(a.noOfOnes);
    }
}