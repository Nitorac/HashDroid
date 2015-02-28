package com.nitorac.hashdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class Utils
{
    private static int sTheme;

    public final static int THEME_LIGHT = 0;
    public final static int THEME_DARK = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */

    public static void getStoredTheme(Activity activity)
    {
        SharedPreferences sp = activity.getSharedPreferences("com.nitorac.hashdroid", Activity.MODE_PRIVATE);
        try {
           sTheme = sp.getInt("theme", -1);
        }catch(Exception e){
            sTheme = 0;
        }

    }
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;

        SharedPreferences sp = activity.getSharedPreferences("com.nitorac.hashdroid", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("theme", sTheme);
        editor.commit();

        activity.finish();

        activity.startActivity(new Intent(activity, activity.getClass()));

    }

    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        getStoredTheme(activity);

        switch (sTheme)
        {
            default:
            case THEME_LIGHT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.AppTheme_Dark);
                break;
        }
    }
}