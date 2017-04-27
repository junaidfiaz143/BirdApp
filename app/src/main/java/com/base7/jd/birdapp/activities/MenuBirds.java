package com.base7.jd.birdapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.base7.jd.birdapp.R;

public class MenuBirds extends AppCompatActivity {

    private Button btnAllBirds, btnAllRelations, btnAllSell, btnAllClutches, btnMakeRalation, btnBirdSell;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_birds);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize buttons and casting it
        btnAllBirds = (Button) findViewById(R.id.all_birds);
        btnAllRelations = (Button) findViewById(R.id.all_relations);
        btnAllSell = (Button) findViewById(R.id.all_sell);
        btnAllClutches = (Button) findViewById(R.id.all_clutches);
        btnMakeRalation = (Button) findViewById(R.id.makeRelation);
        btnBirdSell = (Button) findViewById(R.id.birdSell);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuBirds.this, AddBirds.class));
            }
        });

    }

    public void AllBirds(View v) {
        startActivity(new Intent(this, AllBirds.class));
    }

    public void AllRelations(View v) {
        startActivity(new Intent(this, AllRelations.class));
    }

    public void AllSell(View v) {
        startActivity(new Intent(this, AllSell.class));
    }

    public void AllClutches(View v) {
        startActivity(new Intent(this, AllClutches.class));
    }

    public void MakeRelation(View v) {
        startActivity(new Intent(this, MakeRelation.class));
    }

    public void AddCategory(View v) {
        startActivity(new Intent(this, AddCategory.class));
    }

    public void BirdSell(View view) {
        startActivity(new Intent(this, SellBirds.class));
    }

}
