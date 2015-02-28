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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nitorac.hashdroid.adapter.TabsPagerAdapterInput;
import com.nitorac.hashdroid.libs.CipherCrypts;

import java.util.Locale;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private static final String APP_SHARED_PREFS = "com.nitorac.hashdroid";
    private SharedPreferences.Editor editor;
    public static double APPversion = 1.2;

    public static String userInput;
    public static String pwd;
    public static String selectedEncryption = "OOPS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        SharedPreferences langsettings = getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        editor = langsettings.edit();
        Locale locale = new Locale(langsettings.getString("lang", Locale.getDefault().getLanguage()));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        TabsPagerAdapterInput mAdapter = new TabsPagerAdapterInput(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        String encrypt = getString(R.string.Tab1Crypt);
        String decrypt = getString(R.string.Tab2Decrypt);
        String settings = getString(R.string.Tab3Settings);
        String[] tabs = {encrypt, decrypt, settings};

        selectedEncryption = "OOPS";

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
                    public void onClick(DialogInterface dialog, int which) {
                        String currentLang = getResources().getConfiguration().locale.toString();
                        if (which == 0) {
                            if(currentLang.equals("fr")) {
                                Toast.makeText(MainActivity.this, getString(R.string.alreadyLang), Toast.LENGTH_SHORT).show();
                            }else{
                                restartInLanguage("fr");
                            }
                        } else if (which == 1) {
                            if(currentLang.equals("en")) {
                                Toast.makeText(MainActivity.this, getString(R.string.alreadyLang), Toast.LENGTH_SHORT).show();
                            }else{
                                restartInLanguage("en");
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    public void themeSpinnerView() {

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(getString(R.string.chooseTheme));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getString(R.string.lightTheme));
        arrayAdapter.add(getString(R.string.darkTheme));
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
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if(Utils.sTheme == Utils.THEME_LIGHT){
                                Toast.makeText(MainActivity.this, getString(R.string.alreadyTheme), Toast.LENGTH_SHORT).show();
                            } else {
                                Utils.changeToTheme(MainActivity.this, Utils.THEME_LIGHT);
                            }
                        } else if (which == 1) {
                            if(Utils.sTheme == Utils.THEME_DARK){
                                Toast.makeText(MainActivity.this, getString(R.string.alreadyTheme), Toast.LENGTH_SHORT).show();
                            }else{
                                Utils.changeToTheme(MainActivity.this, Utils.THEME_DARK);
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    public void encryptionSpinnerView(View v) {

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(getString(R.string.selEncryptionBtn));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("AES-256");
        arrayAdapter.add("DES");
        arrayAdapter.add("BlowFish");
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
                    public void onClick(DialogInterface dialog, int which) {
                        Button encryptionBtn = (Button) findViewById(R.id.encryptionSel);
                        if (which == 0) {
                            encryptionBtn.setText(getString(R.string.encryptionTxtBtn) + " AES-256");
                            selectedEncryption = "AES256";
                            ResultDecryptActivity.decryptType = "AES-256";
                        } else if (which == 1) {
                            encryptionBtn.setText(getString(R.string.encryptionTxtBtn) + " DES");
                            selectedEncryption = "DES";
                            ResultDecryptActivity.decryptType = "DES";
                            Utils.changeToTheme(MainActivity.this, Utils.THEME_LIGHT);
                        } else if (which == 2) {
                            encryptionBtn.setText(getString(R.string.encryptionTxtBtn) + " BlowFish");
                            selectedEncryption = "BlowFish";
                            ResultDecryptActivity.decryptType = "BlowFish";
                            Utils.changeToTheme(MainActivity.this, Utils.THEME_DARK);
                        }
                    }
                });
        builderSingle.show();
    }

    public void decryptStringBtn(View view) {

        EditText inputString = (EditText) findViewById(R.id.inputDecrypt);
        EditText inputPassword = (EditText) findViewById(R.id.pwdInput);
        String StrToDecrypt = inputString.getText().toString();
        String PwdToDecrypt = inputPassword.getText().toString();

        if (StrToDecrypt.isEmpty()) {
            Toast.makeText(MainActivity.this, getString(R.string.textEmpty), Toast.LENGTH_SHORT).show();
        } else if (PwdToDecrypt.isEmpty()) {
            Toast.makeText(MainActivity.this, getString(R.string.pwdEmpty), Toast.LENGTH_SHORT).show();
        }
        Log.i("Encryption", selectedEncryption);
        if (selectedEncryption.equals("AES256")) {
            try {
                ResultDecryptActivity.decryptValue = CipherCrypts.cryptdecrypt(StrToDecrypt, PwdToDecrypt, false, "PBEWITHSHA-256AND256BITAES-CBC-BC");
                Intent intent = new Intent(this, ResultDecryptActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, getString(R.string.badPwd), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.selEncryptionBtn), Toast.LENGTH_SHORT).show();
        }
    }

    public void hashString(View view) {
        Intent intent = new Intent(this, EncryptResultActivity.class);
        EditText input = (EditText) findViewById(R.id.inputString);
        EditText password = (EditText) findViewById(R.id.pwdText);
        String stringInput = input.getText().toString();
        String pwdInput = password.getText().toString();
        if (stringInput.isEmpty()) {
            Toast.makeText(MainActivity.this, getString(R.string.emptyText), Toast.LENGTH_SHORT).show();
        } else {
            userInput = stringInput;
            pwd = pwdInput;
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if(Utils.sTheme == Utils.THEME_LIGHT){
                getMenuInflater().inflate(R.menu.main_light, menu);
            }else if(Utils.sTheme == Utils.THEME_DARK){
                getMenuInflater().inflate(R.menu.main_dark, menu);
            }
        } else {
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

