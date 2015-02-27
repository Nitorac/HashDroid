package com.nitorac.hashdroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class ResultDecryptActivity extends Activity {
    public static String decryptType;
    public static String decryptValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_decrypt);

        TextView dType = (TextView) findViewById(R.id.decryptType);
        TextView dValue = (TextView) findViewById(R.id.decryptValue);

        dType.setText(decryptType);
        dValue.setText(decryptValue);
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

    public void shareBtn(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, decryptValue);
        sendIntent.setType("text/plain");
        startActivityForResult(Intent.createChooser(sendIntent, "Partager via"),1);
        getFragmentManager().popBackStack();
    }

    public void copyMsg(View view)
    {
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip;
        String text = decryptValue;
        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getApplicationContext(), "Message " + getString(R.string.clip), Toast.LENGTH_SHORT).show();
    }
}
