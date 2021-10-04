package com.example.fuzzy_multi_criteria_decision_making_system;

import androidx.annotation.NonNull;

public class SpinnerLinguisticData {
    public enum Type { Simple, Less, Over, Within }

    private Type type;
    public Type getType() { return type; }

    private LinguisticTerm firstLinguisticTerm;
    private LinguisticTerm secondLinguisticTerm;

    public SpinnerLinguisticData(Type type, LinguisticTerm firstLinguisticTerm) {
        this.type = type;
        this.firstLinguisticTerm = firstLinguisticTerm;
    }

    public SpinnerLinguisticData(Type type, LinguisticTerm firstLinguisticTerm, LinguisticTerm secondLinguisticTerm){
        this.type = type;
        this.firstLinguisticTerm = firstLinguisticTerm;
        this.secondLinguisticTerm = secondLinguisticTerm;
    }

    @NonNull
    @Override
    public String toString() {
        switch (type) {
            case Simple:
                return firstLinguisticTerm.getShortName();
            case Less:
                return "less " + firstLinguisticTerm.getShortName();
            case Over:
                return "over " + firstLinguisticTerm.getShortName();
            case Within:
                return "within " + firstLinguisticTerm.getShortName() + " and " + secondLinguisticTerm.getShortName();
            default:
                return "invalid";
        }
    }
}
