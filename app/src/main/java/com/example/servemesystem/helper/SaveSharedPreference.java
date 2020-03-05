package com.example.servemesystem.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.example.servemesystem.pojo.User;
import com.google.gson.Gson;

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_OBJECT = "userObject";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static User getUserObject(Context ctx){
        Gson gson = new Gson();
        return gson.fromJson(getSharedPreferences(ctx).getString(PREF_USER_OBJECT, ""), User.class);
    }

    public static void setUserObject(Context ctx, @NonNull User user){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_OBJECT, gson.toJson(user));
        editor.commit();
    }

    public static void removeUserObject(Context ctx){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = getSharedPreferences((ctx)).edit();
        editor.remove(PREF_USER_OBJECT);
        editor.commit();
    }
}
