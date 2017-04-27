package com.base7.jd.birdapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.adapters.CategoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddCategory extends AppCompatActivity {

    private EditText txtAddCategory;

    private Button btnAdd;

    private String strCategory;

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference dbRef;

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private RecyclerView list;

    private ProgressDialog progressDialog;

    private ArrayList<String> categoryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        dbRef = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birdCategories");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAddCategory = (EditText) findViewById(R.id.txtAddCategory);

        list = (RecyclerView) findViewById(R.id.list);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCategory = txtAddCategory.getText().toString();
                if (!strCategory.equals(""))
                    rf.child(fbUser.getUid()).child("birdCategories").child(strCategory).setValue(strCategory, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(AddCategory.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddCategory.this, "Category successfully added.", Toast.LENGTH_LONG).show();
                                // upload();
                            }
                        }
                    });
                else
                    Toast.makeText(AddCategory.this, "please enter some category...", Toast.LENGTH_LONG).show();
            }
        });

        upload();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void upload() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String category = ds.getKey();
                    categoryArrayList.add(category);
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddCategory.this);
                list.setLayoutManager(layoutManager);
                list.setAdapter(new CategoryAdapter(categoryArrayList));
                progressDialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
