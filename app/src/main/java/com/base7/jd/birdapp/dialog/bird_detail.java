package com.base7.jd.birdapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.activities.UpdateBird;

/**
 * Created by jd on 24-Feb-17.
 */

public class bird_detail extends DialogFragment {

    private TextView id;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        id = (TextView) view.findViewById(R.id.birdId);

        id.setText(getArguments().getString("id"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle("birds");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), UpdateBird.class);
                intent.putExtra("id", getArguments().getString("id"));
                startActivity(intent);
            }
        });

        return builder.create();
    }
}
