package com.ttu.ttu.login;

/**
 * Created by wind1209 on 2016/12/5.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import java.util.regex.Pattern;

public class Helper {
    public static String EXTRA_SESSION_KEY = "SessionKey";
    public static String EXTRA_LOGIN_ID = "LoginID";
    public static String EXTRA_TYPE = "type";

    public static String URL_PREFIX = "http://selquery.ttu.edu.tw/app/";
    public static String URL_Cis = "http://ttucis.ttu.edu.tw/m/";

    public static String SETTING_ACCOUNT = "settingAccount";
    public static String SETTING_PASSWORD = "settingPassword";
    public static String SETTING_SESSIONKEY = "settingSessionkey";
    public static String SETTING_TYPE = "settingtype";
    public static String SETTING_DEVICEID = "settingDeviceid";

    public static String getAccount(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(SETTING_ACCOUNT, "");
    }

    public static String getPassword(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(SETTING_PASSWORD, "");
    }

    public static String getSessionkey(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(SETTING_SESSIONKEY, "");
    }

    public static String getType(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(SETTING_TYPE, "");
    }

    public static String getURL(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(URL_PREFIX, "http://selquery.ttu.edu.tw/app/");
    }

    public static String getURLCis(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(URL_PREFIX, "http://ttucis.ttu.edu.tw/m/");
    }

    public static String getDeviceid(Context instance) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        return preferences.getString(SETTING_DEVICEID, "");
    }

    public static void setAccount(Context instance, String account) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(SETTING_ACCOUNT, account).apply();
    }

    public static void setPassword(Context instance, String password) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(SETTING_PASSWORD, password).apply();
    }

    public static void setSessionkey(Context instance, String sessionkey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(SETTING_SESSIONKEY, sessionkey).apply();
    }

    public static void setType(Context instance, String type) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(SETTING_TYPE, type).apply();
    }

    public static void setURL(Context instance, String type) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(URL_PREFIX, type).apply();
    }

    public static void setSettingDeviceid(Context instance, String deviceid) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(instance);
        preferences.edit().putString(SETTING_DEVICEID, deviceid).apply();
    }

    public static boolean haveInternet(Context instance) {
        boolean rs = false;
        ConnectivityManager connManager = (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();

        if(info == null || !info.isConnected()) {
            rs = false;
        } else  {
            if(!info.isAvailable()) {
                rs = false;
            } else {
                rs = true;
            }
        }

        return rs;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isPad(Context context) {
        //回傳true就是平板
        //回傳false就是手機囉
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
