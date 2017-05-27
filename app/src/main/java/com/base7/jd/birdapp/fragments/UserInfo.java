package com.base7.jd.birdapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.activities.SignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserInfo extends Fragment {

    private DatabaseReference rfBirds = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");
    private DatabaseReference rfRelations = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdRelations");
    private DatabaseReference rfClutches = FirebaseDatabase.getInstance().getReference().child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdClutches");

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private TextView btnLogout;

    private TextView txtAllBirds, txtAllRelations, txtAllClutches;

    private ProgressBar progressBar;

    private LinearLayout txtAllDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        txtAllDetails = (LinearLayout) view.findViewById(R.id.txtAllDetails);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtAllDetails.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        upload();

        txtAllBirds = (TextView) view.findViewById(R.id.txtAllBirds);
        txtAllRelations = (TextView) view.findViewById(R.id.txtAllRelations);
        txtAllClutches = (TextView) view.findViewById(R.id.txtAllClutches);

        btnLogout = (TextView) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are You Sure, LOGOUT?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(getActivity(), SignUp.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void upload() {
        rfBirds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllBirds.setText("" + dataSnapshot.getChildrenCount());
                    txtAllDetails.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rfRelations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllRelations.setText("" + dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rfClutches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    txtAllClutches.setText("" + dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}