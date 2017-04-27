package com.base7.jd.birdapp.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.adapters.GenderAdapter;
import com.base7.jd.birdapp.datamodels.Bird;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectBird extends AppCompatActivity {

    private ArrayList<Bird> birdArrayList = new ArrayList<>();

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference dbRef;

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private Query query;

    private ProgressDialog dialog;

    private RecyclerView recyclerView;

    private String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bird);

        dbRef = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectBird.this);
        recyclerView.setLayoutManager(layoutManager);

        select = getIntent().getExtras().getString("select");

        assert select != null;
        if (select.equals("male")) {
            query = dbRef.orderByChild("gender").equalTo("Male");
        } else if (select.equals("female")) {
            query = dbRef.orderByChild("gender").equalTo("Female");
        } else query = dbRef;

        upload();

        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem item = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!(newText.equals(""))) {
                    dialog.show();
                    if (select.equals("female"))
                        onSearchFemale(newText);
                    else if (select.equals("male"))
                        onSearchMale(newText);
                    else onSearch(newText);
                } else {
                    dialog.show();
                    upload();
                }
                return false;
            }
        });

        return true;
    }

    public void upload() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                birdArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    if (!b.getStatus().equals("Sold"))
                        birdArrayList.add(b);
                }

                recyclerView.setAdapter(new GenderAdapter(birdArrayList, SelectBird.this));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onSearchFemale(String query) {

        Query q = rf.orderByChild("id").equalTo(query);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                birdArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    if (b.getGender().equals("Female") && !(b.getStatus().equals("Sold")))
                        birdArrayList.add(b);
                }

                recyclerView.setAdapter(new GenderAdapter(birdArrayList, SelectBird.this));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onSearchMale(String query) {

        Query q = rf.orderByChild("id").equalTo(query);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                birdArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    if (b.getGender().equals("Male") && !(b.getStatus().equals("Sold")))
                        birdArrayList.add(b);
                }

                recyclerView.setAdapter(new GenderAdapter(birdArrayList, SelectBird.this));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onSearch(String query) {

        Query q = rf.orderByChild("id").equalTo(query);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                birdArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    birdArrayList.add(b);
                }

                recyclerView.setAdapter(new GenderAdapter(birdArrayList, SelectBird.this));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
