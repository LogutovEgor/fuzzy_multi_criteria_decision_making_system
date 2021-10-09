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
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;

    Spinner spinnerMethod;

    ArrayList<SpinnerData> spinnerLinguisticData;
    ArrayAdapter<SpinnerData> adapter;
    

    Spinner[][] spinners;
    TableRow[] tableRows;

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
        tableRows = new TableRow[numberAlternatives + 1];
        spinners = new Spinner[numberAlternatives][numberCriterias];
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerLinguisticData);
        for (int i = 0; i <= numberAlternatives; i++) {
            tableRows[i] = new TableRow(this);
            tableLayout.addView(tableRows[i]);
            for (int j = 0; j <= numberCriterias; j++) {

                if (i == 0) {

                    TextView textView = new TextView(this);
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                    layoutParams.width = 256;
                    layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                    textView.setLayoutParams(layoutParams);
                    tableRows[i].addView(textView);
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
                        tableRows[i].addView(textView);
                        textView.setText("A" + i);
                    } else {
                        spinners[i-1][j-1] = new Spinner(this);
                        spinners[i-1][j-1].setAdapter(adapter);
                        tableRows[i].addView(spinners[i-1][j-1]);

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
        for (SpinnerData spinnerData: spinnerLinguisticData) {
            Data.getInstance().transformToIntervalEstimates(spinnerData);
        }
        adapter.notifyDataSetChanged();

        String method = spinnerMethod.getSelectedItem().toString();
        if (method.equals(Data.PESSIMISTIC)) {
            Data.getInstance().I = new ArrayList<>();
            Data.getInstance().p = new ArrayList<>();
            float maxP = Float.MIN_VALUE;
            for (int i = 0; i < spinners.length; i++)
            {
                float[] minI = { Float.MAX_VALUE, Float.MAX_VALUE};

                for (int j = 0; j < spinners[i].length; j++) {
                    SpinnerData spinnerData = (SpinnerData) spinners[i][j].getSelectedItem();
                    float[] intervalEstimates = spinnerData.getIntervalEstimates();
                    if (intervalEstimates[0] <= minI[0])
                        minI[0] = intervalEstimates[0];
                    if (intervalEstimates[1] <= minI[1])
                        minI[1] = intervalEstimates[1];
                }

                float op1 = (1f - minI[0]) / (minI[1] - minI[0] + 1f);
                if (op1 <= 0)
                    op1 = 0;

                float op2 = 1 - op1;
                if (op2 <= 0)
                    op2 = 0;

                Data.getInstance().I.add(minI);
                Data.getInstance().p.add(op2);
                if (op2 >= maxP)
                    maxP = op2;
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Minimum pessimistic");
                } else {
                    textView.setText(Data.getInstance().I.get(i - 1)[0] + "; " + Data.getInstance().I.get(i - 1)[1]);
                }
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Probability pessimistic");
                } else {
                    textView.setText(String.valueOf(Data.getInstance().p.get(i - 1)));
                }
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Result pessimistic");
                } else {
                    if (Data.getInstance().p.get(i - 1) == maxP)
                        textView.setText(String.valueOf(Data.getInstance().p.get(i - 1)));
                    else
                        textView.setText("-");
                }
            }



        }

        if (method.equals(Data.OPTIMISTIC)) {
            Data.getInstance().I = new ArrayList<>();
            Data.getInstance().p = new ArrayList<>();
            float maxP = Float.MIN_VALUE;
            for (int i = 0; i < spinners.length; i++)
            {
                float[] maxI = { Float.MIN_VALUE, Float.MIN_VALUE};

                for (int j = 0; j < spinners[i].length; j++) {
                    SpinnerData spinnerData = (SpinnerData) spinners[i][j].getSelectedItem();
                    float[] intervalEstimates = spinnerData.getIntervalEstimates();
                    if (intervalEstimates[0] >= maxI[0])
                        maxI[0] = intervalEstimates[0];
                    if (intervalEstimates[1] >= maxI[1])
                        maxI[1] = intervalEstimates[1];
                }

                float op1 = (1f - maxI[0]) / (maxI[1] - maxI[0] + 1f);
                if (op1 <= 0)
                    op1 = 0;

                float op2 = 1 - op1;
                if (op2 <= 0)
                    op2 = 0;

                Data.getInstance().I.add(maxI);
                Data.getInstance().p.add(op2);
                if (op2 >= maxP)
                    maxP = op2;
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Minimum optimistic");
                } else {
                    textView.setText(Data.getInstance().I.get(i - 1)[0] + "; " + Data.getInstance().I.get(i - 1)[1]);
                }
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Probability optimistic");
                } else {
                    textView.setText(String.valueOf(Data.getInstance().p.get(i - 1)));
                }
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Result optimistic");
                } else {
                    if (Data.getInstance().p.get(i - 1) == maxP)
                        textView.setText(String.valueOf(Data.getInstance().p.get(i - 1)));
                    else
                        textView.setText("-");
                }
            }
        }

        if (method.equals(Data.GENERALIZED)) {
            for (SpinnerData spinnerData: spinnerLinguisticData) {
                spinnerData.setTransformedState(SpinnerData.TransformedState.TransformedToTrapeze);
            }
            adapter.notifyDataSetChanged();

            ArrayList<float[]> GS = new ArrayList<>();
            for (int i = 0; i < spinners.length; i++)
            {
                float[] GSi = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE };
                for (int j = 0; j < spinners[i].length; j++) {
                    SpinnerData spinnerData = (SpinnerData) spinners[i][j].getSelectedItem();
                    float[] trapeze = spinnerData.getTrapeze();
                    if (trapeze[0] <= GSi[0])
                        GSi[0] = trapeze[0];

                    if (trapeze[1] <= GSi[1])
                        GSi[1] = trapeze[1];

                    if (trapeze[2] >= GSi[2])
                        GSi[2] = trapeze[2];

                    if (trapeze[3] >= GSi[3])
                        GSi[3] = trapeze[3];
                }
                GS.add(GSi);
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("GS");
                } else {
                    float[] GSi = GS.get(i - 1);
                    textView.setText(GSi[0] + "; " + GSi[1] + "; " + GSi[2] + "; " + GSi[3] + ";");
                }
            }

            ArrayList<float[]> I = new ArrayList<>();
            for (float[] GSi: GS) {
                float[] intervalEstimates = { Data.getInstance().getAlpha() * (GSi[1] - GSi[0]) + GSi[0],  GSi[3] - Data.getInstance().getAlpha() * (GSi[3] - GSi[2]) };
                I.add(intervalEstimates);
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Fuzzy intervals");
                } else {
                    textView.setText(I.get(i - 1)[0] + "; " + I.get(i - 1)[1]);
                }
            }

            float maxP = Float.MIN_VALUE;
            ArrayList<Float> p = new ArrayList<>();
            for (float[] Ii: I) {
                float op1 = (1f - Ii[0]) / (Ii[1] - Ii[0] + 1f);
                if (op1 <= 0)
                    op1 = 0;

                float op2 = 1 - op1;
                if (op2 <= 0)
                    op2 = 0;

                p.add(op2);
                if (op2 >= maxP)
                    maxP = op2;
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Probability generalized");
                } else {
                    textView.setText(String.valueOf(p.get(i - 1)));
                }
            }

            for (int i = 0; i <= Data.getInstance().getNumberAlternatives(); i++) {
                TextView textView = new TextView(this);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
                layoutParams.width = 256;
                layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(layoutParams);
                tableRows[i].addView(textView);
                if (i == 0) {
                    textView.setText("Result generalized");
                } else {
                    if (p.get(i - 1) == maxP)
                        textView.setText(String.valueOf(p.get(i - 1)));
                    else
                        textView.setText("-");
                }
            }


        }
    }
}