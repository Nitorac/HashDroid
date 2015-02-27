package com.nitorac.hashdroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nitorac.hashdroid.ResultCRCFragment;
import com.nitorac.hashdroid.ResultHashFragment;

public class TabsPagerAdapterOutput extends FragmentPagerAdapter {

    public TabsPagerAdapterOutput(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new ResultHashFragment();
            case 1:
                return new ResultCRCFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
