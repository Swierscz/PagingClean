package com.sierzega.pagingclean;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPrefUtil {
    private static String PAGE_ACTION = "action";

    public void savePage(Context context, int page) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(PAGE_ACTION, page);
        editor.commit();
    }

    public int getPage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PAGE_ACTION, -1);
    }

}
