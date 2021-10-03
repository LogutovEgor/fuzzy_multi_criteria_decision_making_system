package com.example.fuzzy_multi_criteria_decision_making_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    EditText numberAlternatives;
    EditText numberCriterias;
    EditText numberLinguisticTerms;
    EditText alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        numberAlternatives = findViewById(R.id.editTextNumberAlternatives);
        numberCriterias = findViewById(R.id.editTextNumberCriterias);
        numberLinguisticTerms = findViewById(R.id.editTextNumberLinguisticTerms);
        alpha = findViewById(R.id.editTextAlpha);
    }

    private int getNumberFrom(EditText editText) throws NumberFormatException {
        String textValue = editText.getText().toString();
        return Integer.parseInt(textValue);
    }

    private int getNumberAlternatives() throws NumberFormatException  {
        return getNumberFrom(numberAlternatives);
    }

    private int getNumberCriterias() throws NumberFormatException  {
        return getNumberFrom(numberCriterias);
    }

    private int getNumberLinguisticTerms() throws NumberFormatException {
        return getNumberFrom(numberLinguisticTerms);
    }

    private float getAlpha() {
        String textValue = alpha.getText().toString();
        return Float.parseFloat(textValue);
    }

    private boolean validateNumberAlternative(int value) {
        return value > 1;
    }

    private boolean validateNumberCriterias(int value) {
        return value > 1;
    }

    private boolean validateNumberLinguisticTerms(int value) {
        return value > 1;
    }

    private boolean validateAlpha(float value) { return value >= 0f && value <= 1f; }

    public void onButtonOkClick(View view) {
        int numberAlternatives = 0;
        try {
            numberAlternatives = getNumberAlternatives();
            if (!validateNumberAlternative(numberAlternatives)) {
                throw new Exception("Incorrect number of alternatives (<= 1).");
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        int numberCriterias = 0;
        try {
            numberCriterias = getNumberCriterias();
            if (!validateNumberCriterias(numberCriterias)) {
                throw new Exception("Incorrect number of criterias (<= 1).");
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        int numberLinguisticTerms = 0;
        try {
            numberLinguisticTerms = getNumberLinguisticTerms();
            if (!validateNumberLinguisticTerms(numberLinguisticTerms)) {
                throw new Exception("Incorrect number of linguistic terms (<= 1).");
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        float alpha = 0;
        try {
            alpha = getAlpha();
            if (!validateAlpha(alpha)) {
                throw new Exception("Incorrect alpha (0 <= alpha <= 1).");
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Data.getInstance().setNumberAlternatives(numberAlternatives);
        Data.getInstance().setNumberCriterias(numberCriterias);
        Data.getInstance().setNumberLinguisticTerms(numberLinguisticTerms);
        Data.getInstance().setAlpha(alpha);

        Data.getInstance().createLinguisticTerms();

        Intent intent = new Intent(this, LinguisticTermsActivity.class);
        startActivity(intent);
    }
}