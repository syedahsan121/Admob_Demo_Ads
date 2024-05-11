package com.adsdemoby_ahsan.demo_admob_ads.Utilz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.List;

public class SpUtils {
    public static List<String> stickerPaths = Collections.emptyList();
    private static SpUtils instance;
    private final SharedPreferences prefs;

    public SpUtils(Context context) {
        instance = this;
        prefs = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
    }

    @SuppressLint("StaticFieldLeak")
    public static SpUtils getInstance() {
        return instance;
    }

    public void setStringValue(String name, String value) {
        prefs.edit().putString(name, value).apply();
    }

    public void setBooleanValue(String name, Boolean value) {
        prefs.edit().putBoolean(name, value).apply();
    }

    public String getStringValue(String name) {
        return prefs.getString(name, "");
    }

    public int getIntValue() {
        return prefs.getInt("key", 0);
    }

    public void setIntValue(int value) {
        prefs.edit().putInt("key", value).apply();
    }

    public String getStringValue(String name, String defaultValue) {
        return prefs.getString(name, defaultValue);
    }

    public boolean getBooleanValue(String name) {
        return prefs.getBoolean(name, true);
    }
}
