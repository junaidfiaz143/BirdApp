package com.base7.jd.birdapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.activities.AddBirds;
import com.base7.jd.birdapp.activities.AddCategory;
import com.base7.jd.birdapp.activities.AllSell;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BirdDetail extends Fragment {

    private DatabaseReference rfBirdsDead = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdsDead");
    private DatabaseReference rfBirdsSell = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdsSell");
    private DatabaseReference rfCategories = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdCategories");

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private LinearLayout btnAllSelledBird, btnAllDeadBirds, btnAllBirdsCategories;

    private ProgressDialog dialog;

    private TextView txtAllCategories, txtAllSellBirds, txtAllDeadBirds;

    private FloatingActionButton btnAddBird;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bird_detail, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait..!");
        dialog.show();

        upload();

        txtAllCategories = (TextView) view.findViewById(R.id.txtAllCategories);
        txtAllSellBirds = (TextView) view.findViewById(R.id.txtAllSellBirds);
        txtAllDeadBirds = (TextView) view.findViewById(R.id.txtAllDeadBirds);

        btnAllSelledBird = (LinearLayout) view.findViewById(R.id.btnAllSellBirds);
        btnAllDeadBirds = (LinearLayout) view.findViewById(R.id.btnAllDeadBirds);
        btnAllBirdsCategories = (LinearLayout) view.findViewById(R.id.btnAllBirdsCategories);
        btnAllSelledBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllSell.class));
            }
        });

        btnAllDeadBirds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "check status", Toast.LENGTH_LONG).show();
            }
        });

        btnAllBirdsCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddCategory.class));
            }
        });

        btnAddBird = (FloatingActionButton) view.findViewById(R.id.btnAddBird);
        btnAddBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddBirds.class));
            }
        });

        return view;
    }

    public void upload() {
        rfBirdsDead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllSellBirds.setText("" + dataSnapshot.getChildrenCount());
                    dialog.hide();
                } else
                    dialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rfBirdsSell.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.show();
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

        rfCategories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.show();
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllCategories.setText("" + dataSnapshot.getChildrenCount());
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
