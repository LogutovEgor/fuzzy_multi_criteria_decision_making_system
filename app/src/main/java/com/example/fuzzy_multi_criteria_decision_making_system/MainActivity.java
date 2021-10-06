package com.example.fuzzy_multi_criteria_decision_making_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;

    Spinner spinnerMethod;

    ArrayList<SpinnerData> spinnerLinguisticData;
    ArrayAdapter<SpinnerData> adapter;
    

    Spinner[][] spinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.table_layout);

        spinnerMethod = findViewById(R.id.spinnerMethod);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, Data.getInstance().getCalculateMethods());
        spinnerMethod.setAdapter(adapter);

        spinnerLinguisticData = Data.getInstance().getSpinnerLinguisticData();
        createTable();
    }

    private void createTable() {
        int numberAlternatives = Data.getInstance().getNumberAlternatives();
        int numberCriterias = Data.getInstance().getNumberCriterias();
        spinners = new Spinner[numberAlternatives][numberCriterias];
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerLinguisticData);
        for (int i = 0; i <= numberAlternatives; i++) {
            TableRow topTableRow = new TableRow(this);
            tableLayout.addView(topTableRow);
            for (int j = 0; j <= numberCriterias; j++) {

                if (i == 0) {

                    TextView textView = new TextView(this);
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                    layoutParams.width = 256;
                    layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                    textView.setLayoutParams(layoutParams);
                    topTableRow.addView(textView);
                    if (j != 0) {
                        textView.setText("C" + j);
                    }
                }
                else {
                    if (j == 0) {
                        TextView textView = new TextView(this);
                        textView.setTextSize(20);
                        textView.setGravity(Gravity.CENTER);
                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                        layoutParams.width = 256;
                        layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                        textView.setLayoutParams(layoutParams);
                        topTableRow.addView(textView);
                        textView.setText("A" + i);
                    } else {
                        spinners[i-1][j-1] = new Spinner(this);
                        spinners[i-1][j-1].setAdapter(adapter);
                        topTableRow.addView(spinners[i-1][j-1]);

                    }
                }
            }



        }
    }

    public void onButtonTransformToIntervalClick(View view) {
        for (SpinnerData spinnerData: spinnerLinguisticData) {
            Data.getInstance().transformToInterval(spinnerData);
        }
        adapter.notifyDataSetChanged();
    }

    public void onButtonTransformToTrapezeClick(View view) {
        for (SpinnerData spinnerData: spinnerLinguisticData) {
            Data.getInstance().transformToTrapeze(spinnerData);
        }
        adapter.notifyDataSetChanged();
    }

    public void onButtonCalculateClick(View view) {

    }
}