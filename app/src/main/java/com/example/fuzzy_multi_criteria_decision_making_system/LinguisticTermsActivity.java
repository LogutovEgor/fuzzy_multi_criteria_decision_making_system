package com.example.fuzzy_multi_criteria_decision_making_system;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.CartesianSeriesType;
import com.anychart.enums.MarkerType;
import com.anychart.enums.ScaleTypes;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.anychart.scales.Linear;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class LinguisticTermsActivity extends AppCompatActivity {

    EditText name;
    EditText shortName;
    EditText left;
    EditText middle;
    EditText right;
    LineChart lineChart;
    TextView pagination;
    Button prev;
    Button next;

    int currentIndex;
    LinguisticTerm currentLinguisticTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linguistic_terms);

        name = findViewById(R.id.editTextName);
        shortName = findViewById(R.id.editTextShortName);
        left = findViewById(R.id.editTextLeft);
        middle = findViewById(R.id.editTextMiddle);
        right = findViewById(R.id.editTextRight);
        lineChart = findViewById(R.id.line_chart);
        pagination = findViewById(R.id.textViewPagination);
        prev = findViewById(R.id.buttonPrev);
        next = findViewById(R.id.buttonNext);

        currentIndex = 0;
        currentLinguisticTerm = Data.getInstance().getLinguisticTerm(currentIndex);

        updateInfo();

        setupChart();
        updateChart();
    }

    private void updateInfo() {
        name.setText(currentLinguisticTerm.getName());
        shortName.setText(currentLinguisticTerm.getShortName());
        left.setText(String.valueOf(currentLinguisticTerm.getLeft()));
        middle.setText(String.valueOf(currentLinguisticTerm.getMiddle()));
        right.setText(String.valueOf(currentLinguisticTerm.getRight()));

        pagination.setText(String.valueOf(currentIndex + 1) + "/" + Data.getInstance().getNumberLinguisticTerms());

        if (currentIndex == 0) {
            prev.setEnabled(false);
            next.setEnabled(true);
        } else if (currentIndex == Data.getInstance().getNumberLinguisticTerms() - 1)
        {
            prev.setEnabled(true);
            next.setEnabled(false);
        } else
        {
            prev.setEnabled(true);
            next.setEnabled(true);
        }
    }

    private void setupChart() {
        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);

        // set an alternative background color
        lineChart.setBackgroundColor(Color.LTGRAY);

        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

//        XAxis xAxis = lineChart.getXAxis();
//        //xAxis.setTypeface(tfLight);
//        xAxis.setTextSize(11f);
//        xAxis.setTextColor(Color.WHITE);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
//
//        YAxis leftAxis = lineChart.getAxisLeft();
//        //leftAxis.setTypeface(tfLight);
//        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//        //leftAxis.setAxisMaximum(200f);
//        //leftAxis.setAxisMinimum(0f);
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularityEnabled(true);
//
//        YAxis rightAxis = lineChart.getAxisRight();
//        //rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);
//        //rightAxis.setAxisMaximum(900);
//        //rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);
    }

    private void updateChart() {
        lineChart.clear();
        List<ILineDataSet> lineDataSets = new ArrayList<>();
        for (int i = 0; i < Data.getInstance().getNumberLinguisticTerms(); i++) {
            LinguisticTerm linguisticTerm = Data.getInstance().getLinguisticTerm(i);

            ArrayList<Entry> values = new ArrayList<>();
            values.add(new Entry(linguisticTerm.getLeft(), 0f));
            values.add(new Entry(linguisticTerm.getMiddle(), 1f));
            values.add(new Entry(linguisticTerm.getRight(), 0f));

            LineDataSet lineDataSet = new LineDataSet(values, linguisticTerm.getShortName());

            if (currentIndex == i)
                lineDataSet.setColor(ColorTemplate.getHoloBlue());

            lineDataSets.add(lineDataSet);
        }

        LineData lineData = new LineData(lineDataSets);
        lineChart.setData(lineData);
    }

    public void onPrevButtonClick(View view) {
        if (currentIndex == 0)
            return;
        currentIndex--;
        currentLinguisticTerm = Data.getInstance().getLinguisticTerm(currentIndex);
        updateInfo();
        updateChart();
    }

    public void onNextButtonClick(View view) {
        if (currentIndex == Data.getInstance().getNumberLinguisticTerms() - 1)
            return;
        currentIndex++;
        currentLinguisticTerm = Data.getInstance().getLinguisticTerm(currentIndex);
        updateInfo();
        updateChart();
    }

    public void onSaveButtonClick(View view) {
        String nameValue = name.getText().toString();
        try {
            validateName(nameValue);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        currentLinguisticTerm.setName(nameValue);

        String shortNameValue = shortName.getText().toString();
        try {
            validateShortName(shortNameValue);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        currentLinguisticTerm.setShortName(shortNameValue);

        float leftValue = 0;
        try {
            leftValue = Float.parseFloat(left.getText().toString());
            currentLinguisticTerm.setLeft(leftValue);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        float middleValue = 0;
        try {
            middleValue = Float.parseFloat(middle.getText().toString());
            currentLinguisticTerm.setMiddle(middleValue);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        float rightValue = 0;
        try {
            rightValue = Float.parseFloat(right.getText().toString());
            currentLinguisticTerm.setRight(rightValue);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Data.getInstance().setLinguisticTerm(currentIndex, currentLinguisticTerm);
        updateInfo();
        updateChart();
    }

    public void onNormalizeButtonClick(View view) {

    }

    public void onFinishButtonClick(View view) {

    }

    private boolean validateName(String value) throws Exception {
        if (value == null || value.length() == 0) {
            throw new Exception("Name is null or empty.");
        }

        for (int i = 0; i < Data.getInstance().getNumberLinguisticTerms(); i++)
        {
            if (i != currentIndex && value.equals(Data.getInstance().getLinguisticTerm(i).getName()))
                throw new Exception("Name already exists.");
        }
        return true;
    }

    private boolean validateShortName(String value) throws Exception {
        if (value == null || value.length() == 0) {
            throw new Exception("Short name is null or empty.");
        }

        for (int i = 0; i < Data.getInstance().getNumberLinguisticTerms(); i++)
        {
            if (i != currentIndex && value.equals(Data.getInstance().getLinguisticTerm(i).getShortName()))
                throw new Exception("Short name already exists.");
        }
        return true;
    }


}