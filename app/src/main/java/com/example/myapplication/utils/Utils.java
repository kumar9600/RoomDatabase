package com.example.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {


    public static final String PREF = "MYPREF";
    public static final String IS_LOGIN = "islogin";
    public static final String STORY = "https://devnconnect.pappaya.education/web/nConnect/stories";
    public static final String LOGIN = "https://devnconnect.pappaya.education/web/nConnect/login";
    public static final String SESSION = "session_id";
    public static final String USERTOKEN = "user_token";
    public static final String STUDENTTOKEN = "student_token";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
