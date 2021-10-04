package com.example.fuzzy_multi_criteria_decision_making_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TableLayout tableLayout;

    ArrayList<SpinnerLinguisticData> spinnerLinguisticData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.table_layout);

        spinnerLinguisticData = Data.getInstance().getSpinnerLinguisticData();
        createTable();
    }

    private void createTable() {
        int numberAlternatives = Data.getInstance().getNumberAlternatives();
        int numberCriterias = Data.getInstance().getNumberCriterias();

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
                        Spinner spinner = new Spinner(this);
                        //spinner.setGravity(Gravity.START);
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        ArrayAdapter<SpinnerLinguisticData> adapter = new ArrayAdapter(this,
                                android.R.layout.simple_spinner_dropdown_item, spinnerLinguisticData);
// Specify the layout to use when the list of choices appears
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                        spinner.setAdapter(adapter);
                        topTableRow.addView(spinner);

                    }
                }
            }



        }
    }
}