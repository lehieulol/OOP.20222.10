package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntermediateColumn {
    private List<List<List<List<Integer>>>> data;

    public IntermediateColumn() {
        data = new ArrayList<>();
    }

    public void addStep(List<List<List<Integer>>> stepData) {
        data.add(stepData);
    }

    public List<List<List<List<Integer>>>> getData() {
        return data;
    }

    public boolean isUnchanged(List<List<List<Integer>>> stepData) {
        if (data.isEmpty())
            return false;

        int lastIndex = data.size() - 1;
        List<List<List<Integer>>> lastStepData = data.get(lastIndex);

        return stepData.equals(lastStepData);
    }

    public void generateIntermediateColumn(Map<Integer, List<String>> groupsData, int lengthOfBits) {
        TransformColumn transformColumn = new TransformColumn();
        boolean unchanged = false;

        while (!unchanged) {
            List<List<List<Integer>>> currentStep = new ArrayList<>();

            for (int i = 0; i < groupsData.size() - 1; i++) {
                List<List<Integer>> currentGroupData = new ArrayList<>();

                List<String> currentGroup = groupsData.get(i);
                if (currentGroup != null) {
                    for (int j = 0; j < currentGroup.size(); j++) {
                        currentGroupData.add(new ArrayList<>());
                    }
                }

                currentStep.add(currentGroupData);
            }

            List<List<String>> currentGroupData = new ArrayList<>();
            List<String> lastGroup = groupsData.get(groupsData.size() - 1);
            if (lastGroup != null) {
                for (int i = 0; i < lastGroup.size(); i++) {
                    currentGroupData.add(new ArrayList<>());
                }
            }

            int currentGroupIndex = 0;
            for (int i = 0; i < groupsData.size() - 1; i++) {
                List<String> currentGroup = groupsData.get(i);
                if (currentGroup != null) {
                    for (int j = 0; j < currentGroup.size(); j++) {
                        List<String> nextGroup = groupsData.get(i + 1);
                        if (nextGroup != null) {
                            for (int k = 0; k < nextGroup.size(); k++) {
                                List<Integer> a1 = transformColumn.convertToArrayList(currentGroup.get(j));
                                List<Integer> a2 = transformColumn.convertToArrayList(nextGroup.get(k));

                                List<Integer> result = transformColumn.compareMinTerms(a1, a2);
                                if (!result.isEmpty()) {
                                    if (i < currentStep.size()) {
                                        if (j < currentStep.get(i).size()) {
                                            currentStep.get(i).get(j).add(1);
                                        }
                                    }
                                    if (i + 1 < currentStep.size()) {
                                        if (k < currentStep.get(i + 1).size()) {
                                            currentStep.get(i + 1).get(k).add(1);
                                        }
                                    }
                                    if (currentGroupIndex < currentGroupData.size()) {
                                        currentGroupData.get(currentGroupIndex).add(transformColumn.convertToString(result));
                                    }
                                }
                            }
                        }
                        currentGroupIndex++;
                    }
                }
            }

            addStep(currentStep);
            unchanged = isUnchanged(currentStep);

            groupsData = mergeGroupsData(currentGroupData);

            // Display the intermediate column step-by-step
            displayIntermediateColumnStep(currentStep);
        }
    }

    public Map<Integer, List<String>> mergeGroupsData(List<List<String>> currentGroupData) {
        Map<Integer, List<String>> mergedGroupsData = new HashMap<>();
        for (int i = 0; i < currentGroupData.size(); i++) {
            List<String> group = currentGroupData.get(i);
            if (!group.isEmpty()) {
                mergedGroupsData.put(i + 1, group);
            }
        }
        return mergedGroupsData;
    }

    public void displayIntermediateColumnStep(List<List<List<Integer>>> stepData) {
        for (int i = 0; i < stepData.size(); i++) {
            List<List<Integer>> currentGroup = stepData.get(i);
            for (int j = 0; j < currentGroup.size(); j++) {
                List<Integer> currentTerm = currentGroup.get(j);
                System.out.println("Group " + (i + 1) + ": Term " + j + ": " + currentTerm);
            }
        }
        System.out.println();
    }
}
