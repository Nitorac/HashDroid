package com.nitorac.hashdroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.nitorac.hashdroid.libs.HashCrypts;

import java.util.ArrayList;
import java.util.List;

public class ResultHashFragment extends Fragment {
    HashAdapter hashListAdapter;
    Activity act;
    static String input;

    public class hashItems {
        String hashType;
        String hashValue;
    }

    public String[] hashTypeArray = {"MD5","SHA-1"};

    public static String MD5Hash;
    public static String SHA1Hash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_results_encrypt_hash, container, false);
        input = MainActivity.userInput;

        MD5Hash = HashCrypts.cryptMD5(input);
        SHA1Hash = HashCrypts.cryptSHA1(input);


        hashListAdapter = new HashAdapter();
        ListView hash = (ListView)rootView.findViewById(R.id.listView1);
        hash.setFocusable(false);
        hash.setAdapter(hashListAdapter);
        hash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View convertView, int position,
                                    long arg3) {

                hashItems hashTypeItem = hashListAdapter.getHash(position);
                Toast.makeText(act.getApplicationContext(), hashTypeItem.hashType,Toast.LENGTH_LONG).show();

                String [] hashValueArray = {MD5Hash, SHA1Hash};

                ClipboardManager myClipboard;
                myClipboard = (ClipboardManager)act.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                String text = hashValueArray[position];
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
            }
        });
        return rootView;
    }

    public class HashAdapter extends BaseAdapter {

        List<hashItems> hashList = getDataForListView();
        @Override
        public int getCount() {

            return hashList.size();
        }

        @Override
        public hashItems getItem(int arg0) {

            return hashList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {

            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                LayoutInflater inflater = (LayoutInflater) act.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_result_encrypt, parent,false);
            }

            TextView hashType = (TextView)convertView.findViewById(R.id.hashType);
            TextView hashValue = (TextView)convertView.findViewById(R.id.hashValue);
            ImageView shareBtn = (ImageView)convertView.findViewById(R.id.shareBtn);

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(act.getApplicationContext(), R.anim.imgview_animation));
                    Toast.makeText(act.getApplicationContext(), "XDDXD " + position, Toast.LENGTH_SHORT).show();
                }
            });

            hashItems hashTypeItem = hashList.get(position);

            hashType.setText(hashTypeItem.hashType);
            hashValue.setText(hashTypeItem.hashValue);

            return convertView;
        }

        public hashItems getHash(int position)
        {
            return hashList.get(position);
        }

    }

    public List<hashItems> getDataForListView()
    {
        String [] hashValueArray = {MD5Hash, SHA1Hash};
        List<hashItems> hashList = new ArrayList<hashItems>();

        for(int i=0;i<2;i++)
        {

            hashItems hashItem = new hashItems();
            hashItem.hashType = hashTypeArray[i];
            hashItem.hashValue = hashValueArray[i];
            hashList.add(hashItem);
        }

        return hashList;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = activity;
    }

}
