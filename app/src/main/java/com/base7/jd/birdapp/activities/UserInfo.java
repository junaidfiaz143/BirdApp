package com.base7.jd.birdapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.base7.jd.birdapp.R;
import com.base7.jd.birdapp.adapters.UserInfoAdapter;

public class UserInfo extends AppCompatActivity {


    private RelativeLayout btnBird, btnRelation, btnClutch, btnMakeRelation, btnSellBird, btnBuyer;

    private ViewPager viewPager;

    private RadioButton userInfo, birdDetail, allDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new UserInfoAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    userInfo.setChecked(true);
                else if (position == 1)
                    birdDetail.setChecked(true);
                else
                    allDetail.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        userInfo = (RadioButton) findViewById(R.id.rdUserInfo);
        userInfo.setChecked(true);
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        birdDetail = (RadioButton) findViewById(R.id.rdBirdDetail);
        birdDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        allDetail = (RadioButton) findViewById(R.id.rdAllDetail);
        allDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        btnBird = (RelativeLayout) findViewById(R.id.btnBird);
        btnRelation = (RelativeLayout) findViewById(R.id.btnRelation);
        btnClutch = (RelativeLayout) findViewById(R.id.btnClutch);
        btnMakeRelation = (RelativeLayout) findViewById(R.id.btnMakeRelation);
        btnSellBird = (RelativeLayout) findViewById(R.id.btnSellBird);
        btnBuyer = (RelativeLayout) findViewById(R.id.btnBuyer);


        btnBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, AllBirds.class));
            }
        });

        btnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, AllRelations.class));
            }
        });

        btnClutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, AllClutches.class));
            }
        });

        btnMakeRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, MakeRelation.class));
            }
        });

        btnSellBird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, SellBirds.class));
            }
        });

        btnBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfo.this, AllBirds.class));
            }
        });
    }


}
