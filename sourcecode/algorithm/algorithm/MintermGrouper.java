package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MintermGrouper {
    public static Map<Integer, List<String>> groupMintermsByOccurrences(List<MinTerm> minterms) {
        Map<Integer, List<String>> groupsData = new HashMap<>();

        for (MinTerm minterm : minterms) {
            int occurrences = countOnesOccurrences(minterm);
            groupsData.putIfAbsent(occurrences, new ArrayList<>());
            groupsData.get(occurrences).add(minterm.convertMinterms());
        }

        return groupsData;
    }

    private static int countOnesOccurrences(MinTerm minterm) {
        int count = 0;
        for (char c : minterm.convertMinterms().toCharArray()) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }
    public static void main(String[] args){
        List<MinTerm> minTerm = new ArrayList<>();
        MinTerm a = new MinTerm("7",3);
        MinTerm b = new MinTerm("1",3);
        MinTerm c = new MinTerm("3",3);
        MinTerm d = new MinTerm("4",3);
        minTerm.add(a);
        minTerm.add(b);
        minTerm.add(c);
        minTerm.add(d);
        System.out.println(minTerm);
        System.out.println(groupMintermsByOccurrences(minTerm));
    }
}
