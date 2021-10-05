package com.example.fuzzy_multi_criteria_decision_making_system;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpinnerLinguisticData {
    public enum Type { Simple, Less, Over, Within }

    private Type type;
    public Type getType() { return type; }

    private boolean transformedToIntervals;

    private List<Pair<Integer, LinguisticTerm>> linguisticTerms;

    public SpinnerLinguisticData(Type type, Integer index, LinguisticTerm linguisticTerm) {
        this.type = type;
        transformedToIntervals = false;
        linguisticTerms = new ArrayList<>();
        linguisticTerms.add(new Pair<>(index, linguisticTerm));
    }

    public SpinnerLinguisticData(Type type, Integer firstIndex, LinguisticTerm firstLinguisticTerm, Integer secondIndex, LinguisticTerm secondLinguisticTerm){
        this.type = type;
        linguisticTerms = new ArrayList<>();
        transformedToIntervals = false;
        linguisticTerms.add(new Pair<>(firstIndex, firstLinguisticTerm));
        linguisticTerms.add(new Pair<>(secondIndex, secondLinguisticTerm));
    }

    @NonNull
    @Override
    public String toString() {
        switch (type) {
            case Simple:
                return linguisticTerms.get(0).second.getShortName();
            case Less:
                return "less " + linguisticTerms.get(0).second.getShortName();
            case Over:
                return "over " + linguisticTerms.get(0).second.getShortName();
            case Within:
                return "within " + linguisticTerms.get(0).second.getShortName() + " and " + linguisticTerms.get(1).second.getShortName();
            default:
                return "invalid";
        }
    }
}
