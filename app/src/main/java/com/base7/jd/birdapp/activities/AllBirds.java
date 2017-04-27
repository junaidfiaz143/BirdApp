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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.adapters.BirdAdapter;
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

public class AllBirds extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference dbRef = null;

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private ProgressDialog dialog;

    private ArrayList<Bird> arrayList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_birds);

        dbRef = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllBirds.this);
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                //int pos = viewHolder.getAdapterPosition();

                Toast.makeText(AllBirds.this, "swipe", Toast.LENGTH_LONG).show();


                //arrayList.remove(pos);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void upload() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    arrayList.add(b);
                }

                recyclerView.setAdapter(new BirdAdapter(arrayList, AllBirds.this));
                dialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.hide();
            }
        });
    }

    public void onSearch(String query) {
        Query q = dbRef.orderByChild("id").equalTo(query);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bird b = ds.getValue(Bird.class);
                    arrayList.add(b);
                }

                recyclerView.setAdapter(new BirdAdapter(arrayList, AllBirds.this));
                dialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.hide();
            }
        });
    }

}
