package com.nitorac.hashdroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.nitorac.hashdroid.libs.CRCCrypts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitorac.
 */

public class ResultCRCFragment extends Fragment {
    HashAdapter hashListAdapter;
    Activity act;
    static String input;

    public class hashItems {
        String hashType;
        String hashValue;
    }

    int itemCount = 4;
    public String[] hashTypeArray = {"CRC-8","CRC-16","FCS-16","CRC-32 / FCS-32"};
    String [] hashValueArrayTemp = new String [itemCount];
    String [] hashValueArray;

    public static String CRC8;
    public static String CRC16;
    public static String FCS16;
    public static String CRC32;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_results_encrypt, container, false);

        input = MainActivity.userInput;
        hashValueArrayTemp = new String[]{
                CRC8 = CRCCrypts.cryptCRC8(input),
                CRC16 = CRCCrypts.cryptCRC16(input),
                FCS16 = CRCCrypts.cryptFCS16(input),
                CRC32 = CRCCrypts.cryptCRC32(input)
        };
        hashValueArray = hashValueArrayTemp;

        hashListAdapter = new HashAdapter();
        ListView hash = (ListView)rootView.findViewById(R.id.listView1);
        hash.setFocusable(false);
        hash.setAdapter(hashListAdapter);
        hash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View convertView, int position,
                                    long arg3) {

                hashItems hashTypeItem = hashListAdapter.getHash(position);
                try {
                    ClipboardManager myClipboard;
                    myClipboard = (ClipboardManager) act.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData myClip;
                    String text = hashValueArray[position];
                    myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(act.getApplicationContext(), hashTypeItem.hashType + " " + getString(R.string.clip), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
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
                LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_result_encrypt_crc_light, parent,false);
            }

            TextView hashType = (TextView)convertView.findViewById(R.id.hashType);
            final TextView hashValue = (TextView)convertView.findViewById(R.id.hashValue);
            ImageView shareBtn = (ImageView)convertView.findViewById(R.id.shareBtn);
            //   final String [] hashValueArray = {MD5Hash, SHA1Hash};
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(act.getApplicationContext(), R.anim.imgview_animation));
                    String share = String.format("%s : %s", hashTypeArray[position], hashValueArray[position]);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, share);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
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
        List<hashItems> hashList = new ArrayList<hashItems>();

        for(int i=0;i<itemCount;i++)
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
        act = getActivity();
    }

}