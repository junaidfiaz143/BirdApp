package com.base7.jd.birdapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base7.jd.birdapp.fragments.BirdSummary;
import com.base7.jd.birdapp.fragments.CategorySummary;

/**
 * Created by jd on 26-Apr-17.
 */

public class UserSummaryAdapter extends FragmentPagerAdapter {
    public UserSummaryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BirdSummary();
        } else if (position == 1) {
            return new CategorySummary();
        } else if (position == 2) {
            return new CategorySummary();
        } else return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "bird";
        } else if (position == 1) {
            return "category";
        } else if (position == 2) {
            return "buyer";
        } else return null;
    }
}
