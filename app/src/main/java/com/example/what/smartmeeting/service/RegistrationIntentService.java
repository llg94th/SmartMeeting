package com.example.what.smartmeeting.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.what.smartmeeting.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by ngoc on 4/13/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        InstanceID instanceID = InstanceID.getInstance(this);
        String senderID = getResources().getString(R.string.gcm_defaultSenderId);
        String user = intent.getStringExtra("user");
        try {
            String token = instanceID.getToken(senderID, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
            sendRegistrationToServer(token,user);
        } catch (IOException e) {
            e.printStackTrace();
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    private void sendRegistrationToServer(String token,String user) {
        final String REG_URL = "http://hungntph04073.esy.es/gcm-register.php";
        String getField = "?id="+user+"&reg_id="+token;
        String url = REG_URL+getField;
        try {
            URL url1 = new URL(url);
            url1.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}
