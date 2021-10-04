package com.example.fuzzy_multi_criteria_decision_making_system;

import java.util.ArrayList;

public final class Data {

    private static Data instance;
    public static Data getInstance() {
        if (instance == null)
            instance = new Data();
        return instance;
    }

    private int numberAlternatives;
    public int getNumberAlternatives() { return numberAlternatives; }
    public void setNumberAlternatives(int value) { numberAlternatives = value; }

    private int numberCriterias;
    public int getNumberCriterias() { return numberCriterias; }
    public void setNumberCriterias(int value) { numberCriterias = value; }

    private int numberLinguisticTerms;
    public int getNumberLinguisticTerms() { return numberLinguisticTerms; }
    public void setNumberLinguisticTerms(int value) { numberLinguisticTerms = value; }

    private float alpha;
    public float getAlpha() { return alpha; }
    public void setAlpha(float value) { alpha = value; }

    private ArrayList<LinguisticTerm> linguisticTerms;

    public void createLinguisticTerms() {
        linguisticTerms = new ArrayList<>();
        float min = 0f;
        float max = 100f;
        float step = max / (numberLinguisticTerms - 1);
        for (int i = 0; i < numberLinguisticTerms; i++) {
            String name = "LinguisticTerm " + i;
            String shortName = "LT " + i;

            float left;
            float middle;
            float right;

            if (i == 0)
            {
                left = min;
                middle = min;
                right = step;
            }
            else if (i == numberLinguisticTerms - 1)
            {
                left = max - step;
                middle = max;
                right = max;
            } else {
                left = (i - 1) * step;
                middle = i * step;
                right = (i + 1) * step;
            }

            linguisticTerms.add(new LinguisticTerm(name, shortName, left, middle, right));
        }

    }

    public LinguisticTerm getLinguisticTerm(int index) {
        return linguisticTerms.get(index);
    }

    public void setLinguisticTerm(int index, LinguisticTerm linguisticTerm) {
        linguisticTerms.remove(index);
        linguisticTerms.add(index, linguisticTerm);
    }

    public void normalizeLinguisticTerms() {
        float minValue = Float.MAX_VALUE;
        for (LinguisticTerm linguisticTerm: linguisticTerms) {
            if (linguisticTerm.getLeft() <= minValue)
                minValue = linguisticTerm.getLeft();
            if (linguisticTerm.getMiddle() <= minValue)
                minValue = linguisticTerm.getMiddle();
            if (linguisticTerm.getRight() <= minValue)
                minValue = linguisticTerm.getRight();
        }

        float maxValue = minValue;
        for (LinguisticTerm linguisticTerm: linguisticTerms) {
            if (linguisticTerm.getLeft() >= maxValue)
                maxValue = linguisticTerm.getLeft();
            if (linguisticTerm.getMiddle() >= maxValue)
                maxValue = linguisticTerm.getMiddle();
            if (linguisticTerm.getRight() >= maxValue)
                maxValue = linguisticTerm.getRight();
        }

        for (LinguisticTerm linguisticTerm: linguisticTerms) {
            float leftValue = (linguisticTerm.getLeft() - minValue)/(maxValue - minValue);
            float middleValue = (linguisticTerm.getMiddle() - minValue)/(maxValue - minValue);
            float rightValue = (linguisticTerm.getRight() - minValue)/(maxValue - minValue);

            linguisticTerm.setLeft(leftValue);
            linguisticTerm.setMiddle(middleValue);
            linguisticTerm.setRight(rightValue);
        }
    }


    private Data() {
        linguisticTerms = new ArrayList<>();
    }
}
