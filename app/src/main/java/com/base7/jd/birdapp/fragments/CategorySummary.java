package com.base7.jd.birdapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base7.jd.birdapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class CategorySummary extends Fragment {

    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_summary, container, false);

        lineChart = (LineChart) view.findViewById(R.id.lineChart);

        setGraphData();

        final ArrayList<String> monthsArrayList = new ArrayList<>();
        monthsArrayList.add("Jan");
        monthsArrayList.add("Feb");
        monthsArrayList.add("Mar");
        monthsArrayList.add("Apr");
        monthsArrayList.add("May");
        monthsArrayList.add("Jun");
        monthsArrayList.add("Jul");
        monthsArrayList.add("Aug");
        monthsArrayList.add("Sept");
        monthsArrayList.add("Oct");
        monthsArrayList.add("Nov");
        monthsArrayList.add("Dec");

        //for showing x-axis on bottom
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        lineChart.getAxisRight().setTextColor(Color.WHITE); // right y-axis
        lineChart.getXAxis().setTextColor(Color.WHITE);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return monthsArrayList.get((int) value);
            }
        });

        lineChart.setContentDescription("Birds Added");
        lineChart.setGridBackgroundColor(Color.WHITE);


        return view;
    }

    private void setGraphData() {
        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, 5f));
        values.add(new Entry(1, 8f));
        values.add(new Entry(2, 20f));
        values.add(new Entry(3, 5f));
        values.add(new Entry(4, 15f));
        values.add(new Entry(5, 5f));
        values.add(new Entry(6, 33f));
        values.add(new Entry(7, 6f));
        values.add(new Entry(8, 5f));
        values.add(new Entry(9, 15f));

        LineDataSet set1 = new LineDataSet(values, "Category Graph");

        //set1.setDrawIcons(false);

        ILineDataSet dataSetss = set1;

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSetss);

        // set data
        lineChart.setData(data);
    }
}
