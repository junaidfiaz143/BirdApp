package com.base7.jd.birdapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Bird;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateBird extends AppCompatActivity {

    private EditText txtRingNo, txtPrice, txtAge, txtClutchId;

    private DatabaseReference rf;

    private ArrayList<Bird> birdArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bird);

        txtRingNo = (EditText) findViewById(R.id.txtRingNo);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtClutchId = (EditText) findViewById(R.id.txtClutchId);

        Toast.makeText(this, getIntent().getExtras().getString("id"), Toast.LENGTH_LONG).show();

        update();

    }

    public void update() {
        rf = FirebaseDatabase.getInstance().getReference().child("birds");
        Query query = rf.orderByChild("id").equalTo(getIntent().getExtras().getString("id"));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bird bird = snapshot.getValue(Bird.class);
                    birdArrayList.add(bird);
                    txtRingNo.setText(birdArrayList.get(0).getId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
