package com.nitorac.hashdroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsFragment extends ListFragment {

    private static final List<Map<String,String>> items =
            new ArrayList<Map<String,String>>();
    private static final String[] keys =
            { "line1", "line2" };
    private static final int[] controlIds =
            { android.R.id.text1,
              android.R.id.text2 };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String language = getString(R.string.SLanguage);
        String subLanguage = getString(R.string.subLanguage);
        String credits = getString(R.string.SCredits);
        String app_name = getString(R.string.app_name);
        String copyCredits = getString(R.string.creditsCopy);
        String version = String.valueOf(MainActivity.APPversion);
        String creditsWeb = getString(R.string.credits_web);

        String currentLang = getResources().getConfiguration().locale.toString();
        String displayLang;
        if(currentLang.equals("en"))
        {
            displayLang = getString(R.string.english);
        }
        else if(currentLang.equals("fr"))
        {
            displayLang = getString(R.string.french);
        }
        else
        {
            displayLang = "ERROR";
        }

            Map<String, String> map = new HashMap<String, String>();
            items.clear();
            map.put("line1", language);
            map.put("line2", subLanguage + " " + displayLang);
            items.add(map);
            map = new HashMap<String, String>();
            map.put("line1", credits);
            map.put("line2", app_name + " v" + version + "\n"
                           + copyCredits + "\n"
                           + creditsWeb);
            items.add(map);

        ListAdapter adapter = new SimpleAdapter(
                getActivity(),
                items,
                android.R.layout.simple_list_item_2,
                keys,
                controlIds );
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id)
    {
        if (position == 0){
           // Access to MainActivity objects : ((MainActivity)getActivity())
            ((MainActivity)getActivity()).langSpinnerView();
        }
        else if (position == 1){
                if(((MainActivity)getActivity()).haveNetworkConnection() == true) {
                    String url = "http://nitorac.url.ph/404";
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }else{
                    String connectMessage = getString(R.string.connect_message);
                    Toast.makeText(getActivity(),connectMessage,Toast.LENGTH_SHORT).show();
                }
        }
    }

}
