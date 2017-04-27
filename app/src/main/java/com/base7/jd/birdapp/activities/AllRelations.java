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
import com.base7.jd.birdapp.adapters.RelationAdapter;
import com.base7.jd.birdapp.datamodels.Relation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllRelations extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference().child("birdRelations");

    private RecyclerView recyclerView;

    private ArrayList<Relation> arrayList = new ArrayList<>();

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_relations);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllRelations.this);
        recyclerView.setLayoutManager(layoutManager);

        upload();

        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait..!");
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem menuItem = menu.findItem(R.id.search_menu);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!(newText.equals(""))) {
                    onSearch(newText);
                    dialog.show();
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
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Relation relation = ds.getValue(Relation.class);
                    arrayList.add(relation);
                }

                recyclerView.setAdapter(new RelationAdapter(arrayList));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onSearch(String query) {

        Query q = rf.orderByChild("clutchId").equalTo(query);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Relation relation = ds.getValue(Relation.class);
                    arrayList.add(relation);
                }

                recyclerView.setAdapter(new RelationAdapter(arrayList));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
