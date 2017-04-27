package com.base7.jd.birdapp.activities;

import android.app.ProgressDialog;
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
import com.base7.jd.birdapp.datamodels.Bird;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SellBirds extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private Query query = null;

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private Button btnAdd;
    private EditText txtName, txtContact, txtAddress;

    private String boughtBirdId = "";

    private TextView birdRingNo;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_birds);

        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait..!");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        birdRingNo = (TextView) findViewById(R.id.birdRingNo);

        txtName = (EditText) findViewById(R.id.txtName);

        txtContact = (EditText) findViewById(R.id.txtContact);

        txtAddress = (EditText) findViewById(R.id.txtAddress);

        Button btnBird = (Button) findViewById(R.id.btnBird);

        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellBirds.this, SelectBird.class);
                intent.putExtra("select", "all");
                startActivityForResult(intent, 1);
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String contact = txtContact.getText().toString();
                String address = txtAddress.getText().toString();

                if (!name.equals("") && !contact.equals("") && !address.equals("")) {
                    dialog.show();
                    rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdBuyers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                if (ds.child("03107919762").exists()) {
                                    dialog.hide();
                                    Toast.makeText(SellBirds.this, "already exists", Toast.LENGTH_LONG).show();
                                } else {

                                    dialog.hide();
                                    Toast.makeText(SellBirds.this, "not exists", Toast.LENGTH_LONG).show();
                                    //rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdBuyers").child(contact).setValue(new Buyer(name, contact, address, boughtBirdId));

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    query = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds").orderByChild("id").equalTo(boughtBirdId);
                    // upload();
                } else
                    Toast.makeText(SellBirds.this, "please fill all fields.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String id = data.getExtras().getString("id");
                birdRingNo.setText("Ring No: " + id);
                boughtBirdId = id;
            }
        }
    }

    private void upload() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    Toast.makeText(SellBirds.this, "check: " + b.getId(), Toast.LENGTH_LONG).show();
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    String day = "" + calendar.get(java.util.Calendar.DAY_OF_MONTH);
                    String month = "" + calendar.get(java.util.Calendar.MONTH);
                    String year = "" + calendar.get(java.util.Calendar.YEAR);
                    b.setDaySell(day);
                    b.setMonthSell(month);
                    b.setYearSell(year);
                    rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdSelled").child(b.getId()).setValue(b);
                    rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds").child(boughtBirdId).removeValue();
                    dialog.hide();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
