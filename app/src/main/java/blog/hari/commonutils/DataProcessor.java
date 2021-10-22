package blog.hari.commonutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataProcessor {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public DataProcessor(Context context) {
        DataProcessor.context = context;
    }

    public final static String PREFS_NAME = "appname_prefs";

    public static String getString(String key,String defValue) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(key, defValue);
    }

    public static void saveString(String value, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }


    public static void saveListItem(ArrayList<String> list, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    public static ArrayList<String> getListItem(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static void clearListItem(String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        prefs.edit().remove(key).apply();

    }
}