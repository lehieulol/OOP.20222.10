package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TransformColumn {
    public int noOfNines(List<Integer> arr) {
        int count = 0;
        for (Integer i : arr) {
            if (i == 9) {
                count++;
            }
        }
        return count;
    }

    public List<Integer> compareMinTerms(List<Integer> a1, List<Integer> a2) {
        List<Integer> result = new ArrayList<>(a1);
        int count = noOfNines(a1);
        int position = 0;
        for (int i = 0; i < a1.size(); i++) {
            if (a1.get(i) != a2.get(i) && (a1.get(i) == 9 || a2.get(i) == 9)) {
                return Collections.emptyList();
            }
            if (a1.get(i) != a2.get(i) && (a1.get(i) == 0 || a2.get(i) == 0)) {
                count++;
                position = i;
            }
        }

        if (count == noOfNines(a1) + 1) {
            result.set(position, 9);
            for (int i = a1.size(); i < a2.size(); i++) {
                if (a2.get(i) != 9) {
                    result.add(a2.get(i));
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    public List<String> compare(Map<Integer, List<String>> groupsData, int current) {
        List<String> groupList = new ArrayList<>();
        List<String> currentGroup = groupsData.get(current);
        List<String> nextGroup = groupsData.get(current + 1);

        if (nextGroup == null) {
            return groupList; // Return an empty list if nextGroup is null
        }

        for (String a1 : currentGroup) {
            for (String a2 : nextGroup) {
                List<Integer> a1List = convertToArrayList(a1);
                List<Integer> a2List = convertToArrayList(a2);

                List<Integer> result = compareMinTerms(a1List, a2List);
                if (!result.isEmpty()) {
                    groupList.add(result.toString());
                }
            }
        }
        return groupList;
    }

    public List<Integer> convertToArrayList(String binString) {
        List<Integer> arrayList = new ArrayList<>();
        for (char c : binString.toCharArray()) {
            arrayList.add(Character.getNumericValue(c));
        }
        return arrayList;
    }

    public String convertToString(List<Integer> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : arrayList) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
}
