package com.example.fuzzy_multi_criteria_decision_making_system;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public final class Data {

    private static Data instance;
    public static Data getInstance() {
        if (instance == null)
            instance = new Data();
        return instance;
    }

    public final static String PESSIMISTIC = "pessimistic";

    public final static String OPTIMISTIC = "optimistic";
    public final static String GENERALIZED = "generalized";

    public ArrayList<String> getCalculateMethods() {
        ArrayList<String> result = new ArrayList<>();
        result.add(PESSIMISTIC);
        result.add(OPTIMISTIC);
        result.add(GENERALIZED);
        return result;
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

    public ArrayList<float[]> I;
    public ArrayList<Float> p;

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

    public ArrayList<SpinnerData> getSpinnerLinguisticData() {
        ArrayList<SpinnerData> result = new ArrayList<>();

        //simple less over
        for (int i = 0; i < Data.getInstance().getNumberLinguisticTerms(); i++) {
            LinguisticTerm linguisticTerm = Data.getInstance().getLinguisticTerm(i);
            result.add(new SpinnerData(SpinnerData.Type.Simple, i, linguisticTerm));
            result.add(new SpinnerData(SpinnerData.Type.Less, i, linguisticTerm));
            result.add(new SpinnerData(SpinnerData.Type.Over, i, linguisticTerm));
        }

        //within
        for (int i = 0; i < Data.getInstance().getNumberLinguisticTerms(); i++) {
            LinguisticTerm firstLinguisticTerm = Data.getInstance().getLinguisticTerm(i);

            for (int j = i + 1; j < Data.getInstance().getNumberLinguisticTerms(); j++) {
                LinguisticTerm secondLinguisticTerm = Data.getInstance().getLinguisticTerm(j);
                result.add(new SpinnerData(SpinnerData.Type.Within, i, firstLinguisticTerm, j, secondLinguisticTerm));
            }
        }

        return result;
    }

    public SpinnerData transformToInterval(SpinnerData spinnerData) {
        List<Pair<Integer, LinguisticTerm>> currentLinguisticTerms = spinnerData.getLinguisticTerms();

        switch (spinnerData.getType())  {
            case Simple:
                spinnerData.transformToInterval(currentLinguisticTerms);
                return spinnerData;
            case Less:
                if (currentLinguisticTerms.get(0).first == 0)
                {
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                } else {
                    int linguisticTermIndex = currentLinguisticTerms.get(0).first;
                    currentLinguisticTerms.clear();
                    for (int i = 0; i <= linguisticTermIndex; i++) {
                        currentLinguisticTerms.add(new Pair<>(i, linguisticTerms.get(i)));
                    }
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                }
            case Over:
                if (currentLinguisticTerms.get(0).first == numberLinguisticTerms - 1)
                {
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                } else {
                    int linguisticTermIndex = currentLinguisticTerms.get(0).first;
                    currentLinguisticTerms.clear();
                    for (int i = linguisticTermIndex; i < numberLinguisticTerms; i++) {
                        currentLinguisticTerms.add(new Pair<>(i, linguisticTerms.get(i)));
                    }
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                }
            case Within:
                int firstLinguisticTermIndex = currentLinguisticTerms.get(0).first;
                int secondLinguisticTermIndex = currentLinguisticTerms.get(1).first;

                if (firstLinguisticTermIndex + 1 == secondLinguisticTermIndex)
                {
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                } else
                {
                    currentLinguisticTerms.clear();
                    for (int i = firstLinguisticTermIndex; i <= secondLinguisticTermIndex; i++) {
                        currentLinguisticTerms.add(new Pair<>(i, linguisticTerms.get(i)));
                    }
                    spinnerData.transformToInterval(currentLinguisticTerms);
                    return spinnerData;
                }
        }

        spinnerData.transformToInterval(currentLinguisticTerms);
        return spinnerData;
    }

    public SpinnerData transformToTrapeze(SpinnerData spinnerData) {
        if (!spinnerData.transformedToInterval())
            return spinnerData;

        if (spinnerData.getType() == SpinnerData.Type.Simple) {
            LinguisticTerm linguisticTerm = spinnerData.getLinguisticTerms().get(0).second;
            float[] trapeze = { linguisticTerm.getLeft(), linguisticTerm.getMiddle(), linguisticTerm.getMiddle(), linguisticTerm.getRight() };
            spinnerData.transformToTrapez(trapeze);
            return spinnerData;
        } else {
            LinguisticTerm firstLinguisticTerm = spinnerData.getLinguisticTerms().get(0).second;
            LinguisticTerm lastLinguisticTerm = spinnerData.getLinguisticTerms().get(spinnerData.getLinguisticTerms().size()-1).second;
            float[] trapeze = { firstLinguisticTerm.getLeft(), firstLinguisticTerm.getMiddle(), lastLinguisticTerm.getMiddle(), lastLinguisticTerm.getRight() };
            spinnerData.transformToTrapez(trapeze);
            return spinnerData;
        }
    }

    public SpinnerData transformToIntervalEstimates(SpinnerData spinnerData) {
        if (!spinnerData.transformedToTrapeze())
            return spinnerData;

        float[] trapez = spinnerData.getTrapeze();
        float[] intervalEstimates = { alpha * (trapez[1] - trapez[0]) + trapez[0],  trapez[3] - alpha * (trapez[3] - trapez[2]) };
        spinnerData.transformToIntervalEstimates(intervalEstimates);
        return spinnerData;
    }

    private Data() {
        linguisticTerms = new ArrayList<>();
    }
}
