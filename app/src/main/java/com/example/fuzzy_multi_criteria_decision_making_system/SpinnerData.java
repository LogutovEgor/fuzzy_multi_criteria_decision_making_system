package com.example.fuzzy_multi_criteria_decision_making_system;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpinnerData {
    public enum Type { Simple, Less, Over, Within }
    public enum TransformedState { None, TransformedToInterval, TransformedToTrapeze, TransformedToIntervalEstimates}

    private Type type;
    public Type getType() { return type; }

    private TransformedState transformedState;
    public void setTransformedState(TransformedState transformedState) { this.transformedState = transformedState; }

    public boolean transformedToInterval() { return transformedState == TransformedState.TransformedToInterval; }

    public boolean transformedToTrapeze() { return transformedState == TransformedState.TransformedToTrapeze; }

    public boolean transformedToIntervalEstimates() { return transformedState == TransformedState.TransformedToIntervalEstimates; }

    private float[] trapeze;
    public float[] getTrapeze() { return trapeze; }

    private float[] intervalEstimates;
    public float[] getIntervalEstimates() { return intervalEstimates; }

    private List<Pair<Integer, LinguisticTerm>> linguisticTerms;
    public List<Pair<Integer, LinguisticTerm>> getLinguisticTerms() { return linguisticTerms; }

    public SpinnerData(Type type, Integer index, LinguisticTerm linguisticTerm) {
        this.type = type;
        transformedState = TransformedState.None;
        linguisticTerms = new ArrayList<>();
        linguisticTerms.add(new Pair<>(index, linguisticTerm));
    }

    public SpinnerData(Type type, Integer firstIndex, LinguisticTerm firstLinguisticTerm, Integer secondIndex, LinguisticTerm secondLinguisticTerm){
        this.type = type;
        linguisticTerms = new ArrayList<>();
        transformedState = TransformedState.None;
        linguisticTerms.add(new Pair<>(firstIndex, firstLinguisticTerm));
        linguisticTerms.add(new Pair<>(secondIndex, secondLinguisticTerm));
    }

    public void transformToInterval(List<Pair<Integer, LinguisticTerm>> linguisticTerms) {
        this.linguisticTerms = linguisticTerms;
        transformedState = TransformedState.TransformedToInterval;
    }

    public void transformToTrapez(float[] trapeze) {
        this.trapeze = trapeze;
        transformedState = TransformedState.TransformedToTrapeze;
    }

    public void transformToIntervalEstimates(float[] intervalEstimates) {
        this.intervalEstimates = intervalEstimates;
        transformedState = TransformedState.TransformedToIntervalEstimates;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";
        switch (transformedState) {
            case None:
                switch (type) {
                    case Simple:
                        result = linguisticTerms.get(0).second.getShortName();
                        break;
                    case Less:
                        result = "less " + linguisticTerms.get(0).second.getShortName();
                        break;
                    case Over:
                        result = "over " + linguisticTerms.get(0).second.getShortName();
                        break;
                    case Within:
                        result = "within " + linguisticTerms.get(0).second.getShortName() + " and " + linguisticTerms.get(1).second.getShortName();
                        break;
                    default:
                        result = "invalid";
                        break;
                }
                break;
            case TransformedToInterval:
                for (Pair<Integer, LinguisticTerm> linguisticTerm: linguisticTerms) {
                    result += " " + linguisticTerm.second.getShortName() + ";";
                }
                break;
            case TransformedToTrapeze:
                for (float value: trapeze) {
                    result += " " + value + ";";
                }
                break;
            case TransformedToIntervalEstimates:
                for (float value: intervalEstimates) {
                    result += " " + value + ";";
                }
                break;
            default:
                result = "invalid";
                break;
        }
        return result;
    }
}
