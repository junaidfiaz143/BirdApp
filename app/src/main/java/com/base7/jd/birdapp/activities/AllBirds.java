package com.base7.jd.birdapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.Calendar;

public class AllBirds extends AppCompatActivity {

    private DatabaseReference rf = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference dbRef = null;

    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<Bird> arrayList = new ArrayList<>();

    private RecyclerView recyclerView;

    private Spinner spnDay, spnMonth, spnYear;

    private SwitchCompat dataSwitch;

    ProgressBar progressBar;

    private String[] strDay = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] strMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] strYear = {"2017", "2016", "2015", "2014"};

    int year;

    private LinearLayout errorLayout;

    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_birds);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        errorLayout = (LinearLayout) findViewById(R.id.errorLayout);
        errorLayout.setVisibility(View.GONE);

        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
                upload();
            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dataSwitch = (SwitchCompat) findViewById(R.id.dataSwitch);

        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    spnDay.setEnabled(true);
                    spnMonth.setEnabled(true);
                    spnYear.setEnabled(true);

                    Toast.makeText(AllBirds.this, "Date: " + spnDay.getSelectedItem() + "-" + spnMonth.getSelectedItem() + "-" + spnYear.getSelectedItem(), Toast.LENGTH_LONG).show();
                } else {
                    spnDay.setEnabled(false);
                    spnMonth.setEnabled(false);
                    spnYear.setEnabled(false);
                }
            }
        });

        dbRef = rf.child("rGRdX7ofp5YLgeCSt3TZGGfPKQl2").child("birds");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllBirds.this);
        recyclerView.setLayoutManager(layoutManager);

        spnDay = (Spinner) findViewById(R.id.spnDay);
        spnMonth = (Spinner) findViewById(R.id.spnMonth);
        spnYear = (Spinner) findViewById(R.id.spnYear);

        ArrayAdapter<String> spnDayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, strDay);
        spnDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDay.setAdapter(spnDayAdapter);

        ArrayAdapter<String> spnMonthAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, strMonth);
        spnMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(spnMonthAdapter);

        ArrayAdapter<String> spnYearAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, strYear);
        spnYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(spnYearAdapter);

        spnDay.setSelection(spnDayAdapter.getPosition(String.valueOf(day)));
        spnMonth.setSelection(month);
        spnYear.setSelection(spnYearAdapter.getPosition(String.valueOf(year)));

        spnDay.setEnabled(false);
        spnMonth.setEnabled(false);
        spnYear.setEnabled(false);

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

        recyclerView.setVisibility(View.GONE);

        checkInternet();
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
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
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
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //dialog.hide();
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
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //dialog.hide();
            }
        });
    }

    public void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            Toast.makeText(this, "connected", Toast.LENGTH_LONG).show();
            errorLayout.setVisibility(View.GONE);
        } else
        {
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

            Toast.makeText(this, "not connected", Toast.LENGTH_LONG).show();
        }
    }

}
