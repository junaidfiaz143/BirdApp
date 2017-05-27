package com.base7.jd.birdapp.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Bird;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BirdSummary extends Fragment {

    private DatabaseReference rfBirds = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");

    private TextView txtAllBirds, txtAllMaleBirds, txtAllFemaleBirds, txtAllDeadBirds;

    private int maleCount, femaleCount, deadCount;
    private int birdsCountJan = 0, birdsCountFeb = 0, birdsCountMar = 0, birdsCountApr = 0, birdsCountMay = 0, birdsCountJun = 0;
    private int birdsCountJul = 0, birdsCountAug = 0, birdsCountSep = 0, birdsCountOct = 0, birdsCountNov = 0, birdsCountDec = 0;

    private Query maleQuery = rfBirds.orderByChild("gender").equalTo("Male");
    private Query femaleQuery = rfBirds.orderByChild("gender").equalTo("Female");
    private Query deadQuery = rfBirds.orderByChild("status").equalTo("Dead");

    private ProgressDialog dialog;

    private LineChart lineChart;

    private ArrayList<Entry> entry = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bird_summary, container, false);

        txtAllBirds = (TextView) view.findViewById(R.id.txtAllBirds);
        txtAllMaleBirds = (TextView) view.findViewById(R.id.txtAllMaleBirds);
        txtAllFemaleBirds = (TextView) view.findViewById(R.id.txtAllFemaleBirds);
        txtAllDeadBirds = (TextView) view.findViewById(R.id.txtAllDeadBirds);

        uploadBirds();

        lineChart = (LineChart) view.findViewById(R.id.lineChart);

        //setGraphData();

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

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait..!");
        dialog.show();

        return view;
    }

    public void uploadBirds() {
        rfBirds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllBirds.setText("" + dataSnapshot.getChildrenCount());
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Bird b = ds.getValue(Bird.class);
                        switch (b.getMonthAdded()) {
                            case "0":
                                birdsCountJan++;
                                break;
                            case "1":
                                birdsCountFeb++;
                                break;
                            case "2":
                                birdsCountMar++;
                                break;
                            case "3":
                                birdsCountApr++;
                                break;
                            case "4":
                                birdsCountMay++;
                                break;
                            case "5":
                                birdsCountJun++;
                                break;
                            case "6":
                                birdsCountJul++;
                                break;
                            case "7":
                                birdsCountAug++;
                                break;
                            case "8":
                                birdsCountSep++;
                                break;
                            case "9":
                                birdsCountOct++;
                                break;
                            case "10":
                                birdsCountNov++;
                                break;
                            case "11":
                                birdsCountDec++;
                                break;
                        }
                    }
                    entry.add(new Entry(0, (float) birdsCountJan));
                    entry.add(new Entry(1, (float) birdsCountFeb));
                    entry.add(new Entry(2, (float) birdsCountMar));
                    entry.add(new Entry(3, (float) birdsCountApr));
                    entry.add(new Entry(4, (float) birdsCountMay));
                    entry.add(new Entry(5, (float) birdsCountJun));
                    entry.add(new Entry(6, (float) birdsCountJul));
                    entry.add(new Entry(7, (float) birdsCountAug));
                    entry.add(new Entry(8, (float) birdsCountSep));
                    entry.add(new Entry(9, (float) birdsCountOct));
                    entry.add(new Entry(10, (float) birdsCountNov));
                    entry.add(new Entry(11, (float) birdsCountDec));
                    dialog.hide();
                    setGraphData();
                } else
                    dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        maleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllMaleBirds.setText("" + dataSnapshot.getChildrenCount());
                    dialog.hide();
                } else
                    dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        femaleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllFemaleBirds.setText("" + dataSnapshot.getChildrenCount());
                    dialog.hide();
                } else
                    dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deadQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllDeadBirds.setText("" + dataSnapshot.getChildrenCount());
                    dialog.hide();
                } else
                    dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        LineDataSet set = new LineDataSet(entry, "Birds Graph");

        //set1.setDrawIcons(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        lineChart.setData(data);
    }
}
