package com.nitorac.hashdroid.adapter;

import com.nitorac.hashdroid.EncryptFragment;
import com.nitorac.hashdroid.DecryptFragment;
import com.nitorac.hashdroid.ResultCRCFragment;
import com.nitorac.hashdroid.ResultHashFragment;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
