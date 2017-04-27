package com.base7.jd.birdapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.datamodels.Bird;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AddBirds extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference dbRef = null;

    private FirebaseUser fbUser;

    private ArrayList<String> birdArrayList = new ArrayList<>();

    private Button btnAdd;

    private EditText txtRingNo, txtAge, txtPrice, txtClutchId;

    private String strAge, strGender, strPrice, strClutchId, strRingNo, strCategory, strStatus, strEye;

    private Spinner spnGender, spnCategory, spnEye, spnAge;

    private ImageView selectImage;

    private Uri imageUri = null;

    private String[] birdsGender = {"Male", "Female"};
    private String[] birdsEye = {"Black", "Red"};
    private String[] birdAge = {"Day", "Month", "Year"};

    private ProgressDialog dialog, progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birds);

        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        dbRef = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdCategories");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtRingNo = (EditText) findViewById(R.id.txtRingNo);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtClutchId = (EditText) findViewById(R.id.txtClutchId);

        spnGender = (Spinner) findViewById(R.id.spnGender);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnEye = (Spinner) findViewById(R.id.spnEye);
        spnAge = (Spinner) findViewById(R.id.spnAge);

        selectImage = (ImageView) findViewById(R.id.selectImage);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        upload();

        //spinners();

        progressDialog = new ProgressDialog(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("please Wait...!");
        dialog.setCancelable(false);
        dialog.show();

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strRingNo = txtRingNo.getText().toString();
                strGender = spnGender.getSelectedItem().toString();
                strAge = txtAge.getText().toString() + " " + spnAge.getSelectedItem().toString();
                strCategory = spnCategory.getSelectedItem().toString();
                strPrice = txtPrice.getText().toString();
                strEye = spnEye.getSelectedItem().toString();
                strClutchId = txtClutchId.getText().toString();
                strStatus = "Alive";

                if (!strRingNo.equals("") && !strGender.equals("") && !strAge.equals("") && !strCategory.equals("") && !strPrice.equals("") && !strEye.equals("") && !strClutchId.equals("")) {
                    //   progressDialog.show();

                    try {
                        Calendar calendar = Calendar.getInstance();
                        rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds").child(strRingNo).setValue(new Bird(strRingNo, strGender, strAge, strCategory, strPrice, strEye, strStatus, strClutchId, "" + calendar.get(Calendar.DAY_OF_MONTH), "", "" + calendar.get(Calendar.MONTH), "", "" + calendar.get(Calendar.YEAR), ""));
                        Toast.makeText(AddBirds.this, "Data Saved", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(AddBirds.this, "Exception :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    /*StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                    Toast.makeText(AddBirds.this, storageReference.toString(), Toast.LENGTH_LONG).show();

                    StorageReference birdRef = storageReference.child("birdImages").child(strRingNo);// + imageUri.getLastPathSegment());
                    birdRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.hide();
                            fbUser = FirebaseAuth.getInstance().getCurrentUser();
                            String email = fbUser.getEmail();
                            rf.child(getIntent().getExtras().getString("username")).child("birds").child(strRingNo).setValue(new Bird(strRingNo, strGender, strAge, strCategory, strPrice, strEye, strStatus, strClutchId));
                            Toast.makeText(AddBirds.this, "data saved", Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                        }
                    });*/

                } else
                    Toast.makeText(AddBirds.this, "please fill all fields.", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void upload() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String s = ds.getKey();
                    birdArrayList.add(s);
                }

                dialog.hide();
                spinners();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void spinners() {
        ArrayAdapter<String> spnGenderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, birdsGender);
        spnGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(spnGenderAdapter);

        ArrayAdapter<String> spnCategoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, birdArrayList);
        spnCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(spnCategoryAdapter);

        ArrayAdapter<String> spnEyeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, birdsEye);
        spnEyeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEye.setAdapter(spnEyeAdapter);

        ArrayAdapter<String> spnAgeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, birdAge);
        spnAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAge.setAdapter(spnAgeAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                selectImage.setImageURI(imageUri);
            }
        }
    }
}
