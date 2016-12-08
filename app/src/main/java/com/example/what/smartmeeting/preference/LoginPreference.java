package com.example.what.smartmeeting.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.what.smartmeeting.entity.Account;

/**
 * Created by what on 04/04/2016.
 */
public class LoginPreference {
    private SharedPreferences preferences;
    private static String TAG_ID="id";
    private static String TAG_PW="password";
    private static String TAG_LOGIN="login";
    public LoginPreference(Context context){
        this.preferences = context.getSharedPreferences("logindata",0);
    }
    public void setLogined(Account account){
        SharedPreferences.Editor editor= preferences.edit();
        editor.clear();
        editor.putString(TAG_ID,account.getId());
        editor.putString(TAG_PW, account.getPassword());
        editor.putBoolean(TAG_LOGIN,true);
        editor.commit();
    }
    public Account getLogined(){
       String id= preferences.getString(TAG_ID, "");
       String pw= preferences.getString(TAG_PW,"");
        return  new Account(id,pw);
    }
    public void setLogout(){
        SharedPreferences.Editor editor= preferences.edit();
        editor.clear();
        editor.commit();
    }
    public boolean islogined(){
        boolean login=preferences.getBoolean(TAG_LOGIN,false);
        if (login){
            return true;
        }
        return false;
    }
}
