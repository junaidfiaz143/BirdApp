package com.base7.jd.birdapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Clutch;
import com.base7.jd.birdapp.datamodels.Relation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeRelation extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private Button btnMale, btnFemale, btnSave;

    private TextView maleRingNo, femaleRingNo;

    private EditText txtNoofEggs, txtClutchId;

    private String maleId = "", femaleId = "", clutchId, noOfEggs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_relation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNoofEggs = (EditText) findViewById(R.id.txtNoofEggs);
        txtClutchId = (EditText) findViewById(R.id.txtClutchId);

        btnMale = (Button) findViewById(R.id.btnMale);
        btnFemale = (Button) findViewById(R.id.btnFemale);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noOfEggs = txtNoofEggs.getText().toString();
                clutchId = txtClutchId.getText().toString();

                if (!maleId.equals("") && !femaleId.equals("") && !clutchId.equals("") && !noOfEggs.equals("")) {

                    rf.child(fbUser.getUid()).child("birdRelations").child(clutchId).setValue(new Relation(maleId, femaleId, clutchId, noOfEggs));

                    rf.child(fbUser.getUid()).child("birdClutches").child(clutchId).setValue(new Clutch(maleId, femaleId, clutchId, noOfEggs, "under procedure"));
                } else {
                    Toast.makeText(MakeRelation.this, "please fill all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        maleRingNo = (TextView) findViewById(R.id.maleRingNo);
        femaleRingNo = (TextView) findViewById(R.id.femaleRingNo);

    }

    public void selectMale(View v) {
        Intent intent = new Intent(MakeRelation.this, SelectBird.class);
        intent.putExtra("select", "male");
        startActivityForResult(intent, 1);
    }

    public void selectFemale(View v) {
        Intent intent = new Intent(MakeRelation.this, SelectBird.class);
        intent.putExtra("select", "female");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                try {
                    femaleRingNo.setText("Ring No: " + data.getExtras().get("id").toString());
                    femaleId = data.getExtras().get("id").toString();
                } catch (Exception ex) {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                }

            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                try {
                    maleRingNo.setText("Ring No: " + data.getExtras().get("id").toString());
                    maleId = data.getExtras().get("id").toString();
                } catch (Exception ex) {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                }

            }
        }

    }

}
