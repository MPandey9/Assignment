package com.example.assignment.PrefManager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "assignsharedpref";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_MOBILE = "keymobile";

    private static SharedPrefManager mInstance;
    private static Context mCtx;


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    //to login
    public void userLogin(String name,String mobile) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_MOBILE,mobile);
        editor.apply();
    }
    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString((KEY_MOBILE), null) != null;
    }
}
