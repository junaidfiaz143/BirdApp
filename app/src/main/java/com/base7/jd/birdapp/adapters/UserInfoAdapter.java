package com.base7.jd.birdapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base7.jd.birdapp.fragments.AllDetail;
import com.base7.jd.birdapp.fragments.BirdDetail;
import com.base7.jd.birdapp.fragments.UserInfo;

/**
 * Created by jd on 22-Apr-17.
 */

public class UserInfoAdapter extends FragmentPagerAdapter {
    public UserInfoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new UserInfo();
        } else if (position == 1) {
            return new BirdDetail();
        } else if (position == 2) {
            return new AllDetail();
        } else return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
