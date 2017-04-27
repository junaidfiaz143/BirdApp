package com.base7.jd.birdapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.activities.UserSummary;

public class AllDetail extends Fragment {

    private LinearLayout btnAllSummary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_detail, container, false);

        btnAllSummary = (LinearLayout) view.findViewById(R.id.btnAllSummary);
        btnAllSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Summary", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), UserSummary.class));
            }
        });

        return view;
    }

}