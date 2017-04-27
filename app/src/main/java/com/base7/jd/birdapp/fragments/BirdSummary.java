package com.base7.jd.birdapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class BirdSummary extends Fragment {

    private DatabaseReference rfBirds = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");

    private TextView txtAllBirds, txtAllMaleBirds, txtAllFemaleBirds, txtAllDeadBirds;

    private int maleCount, femaleCount, deadCount;

    private Query maleQuery = rfBirds.orderByChild("gender").equalTo("Male");
    private Query femaleQuery = rfBirds.orderByChild("gender").equalTo("Female");
    private Query deadQuery = rfBirds.orderByChild("status").equalTo("Dead");

    private ProgressDialog dialog;

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

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("please wait..!");
        dialog.show();

        return view;
    }

    public void uploadBirds() {
        rfBirds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllBirds.setText("" + dataSnapshot.getChildrenCount());
                    dialog.hide();
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
}
