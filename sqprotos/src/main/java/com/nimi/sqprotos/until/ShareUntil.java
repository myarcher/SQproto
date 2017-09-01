package com.nimi.sqprotos.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareUntil {
    private static ShareUntil shareUntil;
    private SharedPreferences preferences;
    private Editor editor;

    private ShareUntil(String name, Context context) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static ShareUntil getInstance(String name, Context context) {
        if (shareUntil == null) {
            shareUntil = new ShareUntil(name, context);
        }
        return shareUntil;
    }

    public int getInt(String key, int value) {
        return preferences.getInt(key, value);
    }

    public ShareUntil putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    public ShareUntil putS(String key, String value) {
        if (value == null || value.equals("null")) {
            value = "";
        }
        editor.putString(key, value);
        return this;
    }

    public ShareUntil putB(String key, Boolean value) {
        editor.putBoolean(key, value);
        return this;
    }

    public ShareUntil putF(String key, Float value) {
        editor.putFloat(key, value);
        return this;
    }

    public void commit() {
        editor.commit();
    }

    public ShareUntil putI(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    public String getString(String key, String value) {
        return preferences.getString(key, value);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean bo) {
        return preferences.getBoolean(key, bo);
    }

    public void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Float getLong(String key, long lo) {
        return preferences.getFloat(key, lo);
    }

    public void putFloat(String key, Float value) {
        editor.putFloat(key, value);
        editor.commit();
    }
}
