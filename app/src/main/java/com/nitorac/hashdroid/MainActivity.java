package com.nitorac.hashdroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.nitorac.hashdroid.adapter.TabsPagerAdapterInput;

import java.util.Locale;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapterInput mAdapter;
	private ActionBar actionBar;
    private static final String APP_SHARED_PREFS = "com.nitorac.hashdroid";
    private SharedPreferences Langsettings;
    private SharedPreferences.Editor editor;
    public static double APPversion = 1.2;

    public static String userInput;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Langsettings = getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        editor = Langsettings.edit();
        Locale locale = new Locale(Langsettings.getString("lang", Locale.getDefault().getLanguage()));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapterInput(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        String encrypt = getString(R.string.Tab1Crypt);
        String decrypt = getString(R.string.Tab2Decrypt);
        String settings = getString(R.string.Tab3Settings);
        String[] tabs = {encrypt, decrypt, settings};

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

    public void restartInLanguage(String lang) {
        editor.putString("lang", lang);
        editor.commit();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void langSpinnerView() {

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(getString(R.string.listViewSelTit));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getString(R.string.french));
        arrayAdapter.add(getString(R.string.english));
        builderSingle.setNegativeButton(getString(R.string.cancel_button),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                        {
                            restartInLanguage("fr");
                        }
                        else if (which == 1)
                        {
                            restartInLanguage("en");
                        }
                    }
                });
        builderSingle.show();
    }

    public void hashString(View view)
    {
        Intent intent = new Intent(this, EncryptResultActivity.class);
        EditText input = (EditText) findViewById(R.id.inputString);
        String stringInput = input.getText().toString();
        if(stringInput.isEmpty()){
            Toast.makeText(MainActivity.this, getString(R.string.emptyText), Toast.LENGTH_SHORT).show();
        }else {
            userInput = stringInput;
            startActivity(intent);
        }
    }


    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    @Override
    public void onResume() {super.onResume();}

    @Override
    public void onPause(){
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
    public final void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        View focus = getCurrentFocus();
        if (focus != null) {
            hiddenKeyboard(focus);
        }
    }

    @Override
    public final void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
        View focus = getCurrentFocus();
        if (focus != null) {
            hiddenKeyboard(focus);
        }
    }

    @Override
    public final void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        View focus = getCurrentFocus();
        if (focus != null) {
            hiddenKeyboard(focus);
        }
    }

    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

