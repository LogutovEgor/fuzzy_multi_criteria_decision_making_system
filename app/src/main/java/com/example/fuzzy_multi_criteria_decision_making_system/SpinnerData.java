package com.example.fuzzy_multi_criteria_decision_making_system;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpinnerData {
    public enum Type { Simple, Less, Over, Within }

    private Type type;
    public Type getType() { return type; }

    private boolean transformedToInterval;
    public boolean transformedToInterval() { return transformedToInterval; }

    private boolean transformedToTrapeze;
    public boolean transformedToTrapeze() { return transformedToTrapeze; }

    private float[] trapeze;
    public float[] getTrapeze() { return trapeze; }

    private List<Pair<Integer, LinguisticTerm>> linguisticTerms;
    public List<Pair<Integer, LinguisticTerm>> getLinguisticTerms() { return linguisticTerms; }

    public SpinnerData(Type type, Integer index, LinguisticTerm linguisticTerm) {
        this.type = type;
        transformedToInterval = false;
        transformedToTrapeze = false;
        linguisticTerms = new ArrayList<>();
        linguisticTerms.add(new Pair<>(index, linguisticTerm));
    }

    public SpinnerData(Type type, Integer firstIndex, LinguisticTerm firstLinguisticTerm, Integer secondIndex, LinguisticTerm secondLinguisticTerm){
        this.type = type;
        linguisticTerms = new ArrayList<>();
        transformedToInterval = false;
        transformedToTrapeze = false;
        linguisticTerms.add(new Pair<>(firstIndex, firstLinguisticTerm));
        linguisticTerms.add(new Pair<>(secondIndex, secondLinguisticTerm));
    }

    public void transformToInterval(List<Pair<Integer, LinguisticTerm>> linguisticTerms) {
        this.linguisticTerms = linguisticTerms;
        this.transformedToInterval = true;
    }

    public void transformToTrapez(float[] trapeze) {
        this.trapeze = trapeze;
        this.transformedToTrapeze = true;
    }

    @NonNull
    @Override
    public String toString() {
        if (!transformedToInterval) {
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
        } else if (transformedToInterval && !transformedToTrapeze) {
            String result = "";
            for (Pair<Integer, LinguisticTerm> linguisticTerm: linguisticTerms) {
                result += " " + linguisticTerm.second.getShortName() + ";";
            }
            return result;
        } else if (transformedToInterval && transformedToTrapeze) {
            String result = "";
            for (float value: trapeze) {
                result += " " + value + ";";
            }
            return result;
        }
        return "invalid";
    }
}
