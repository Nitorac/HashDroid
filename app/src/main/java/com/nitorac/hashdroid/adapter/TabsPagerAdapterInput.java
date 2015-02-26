package com.nitorac.hashdroid.adapter;

import com.nitorac.hashdroid.EncryptFragment;
import com.nitorac.hashdroid.DecryptFragment;
import com.nitorac.hashdroid.SettingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapterInput extends FragmentPagerAdapter {

	public TabsPagerAdapterInput(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new EncryptFragment();
		case 1:
			return new DecryptFragment();
        case 2:
            return new SettingsFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
