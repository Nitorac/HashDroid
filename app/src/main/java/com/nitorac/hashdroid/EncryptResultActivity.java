package com.nitorac.hashdroid;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nitorac.hashdroid.ResultHashFragment.*;
import com.nitorac.hashdroid.adapter.TabsPagerAdapterOutput;


public class EncryptResultActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapterOutput mAdapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String hash_result = getString(R.string.RTab1Hash);
        String crc_result = getString(R.string.RTab2CRC);
        String[] tabs = {hash_result, crc_result};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapterOutput(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getMenuInflater().inflate(R.menu.main, menu);
        }else {
            getMenuInflater().inflate(R.menu.main_before_api14, menu);
        }
        return true;
    }

    public void helpClick(MenuItem mi) {
        Intent intent = new Intent(this, EncryptionHelp.class);
        startActivity(intent);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
       viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
/*        for (int i=0; i < ResultHashFragment.hashValueLenght; i++)
        {
            ResultHashFragment.hashValueArray[i] = null;
            Log.i("test", String.valueOf(i));
        }*/
    }
}
