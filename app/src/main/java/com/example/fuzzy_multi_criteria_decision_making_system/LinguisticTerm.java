package com.example.fuzzy_multi_criteria_decision_making_system;

public class LinguisticTerm {
    private String name;
    public String getName() { return name; }
    public void setName(String value) throws IllegalArgumentException {
        if (value == null || value.length() == 0)
            throw new IllegalArgumentException("LinguisticTerm name value null or empty.");
        name = value;
    }

    private String shortName;
    public String getShortName() { return shortName; }
    public void setShortName(String value) throws IllegalArgumentException {
        if (value == null || value.length() == 0)
            throw new IllegalArgumentException("LinguisticTerm short name value null or empty.");
        shortName = value;
    }

    private float left;
    public float getLeft() { return left; }
    public void setLeft(float value) throws IllegalArgumentException {
        if (value > middle)
            throw new IllegalArgumentException("Left value > middle value.");
        left = value;
    }

    private float middle;
    public float getMiddle() { return middle; }
    public void setMiddle(float value) throws IllegalArgumentException {
        if (value < left)
            throw new IllegalArgumentException("Middle value < left value.");
        else if (value > right)
            throw new IllegalArgumentException("Middle value > right value.");
        middle = value;
    }

    private float right;
    public float getRight() { return right; }
    public void setRight(float value) throws IllegalArgumentException {
        if (value < middle)
            throw new IllegalArgumentException("Right value < middle value.");
        right = value;
    }

    public LinguisticTerm() {
        name = "LinguisticTerm";
        shortName = "LT";
        left = 0;
        middle = 1;
        right = 2;
    }

    public LinguisticTerm(String name, String shortName, float left, float middle, float right) {
        this.name = name;
        this.shortName = shortName;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
}
