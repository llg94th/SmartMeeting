package com.example.what.smartmeeting.service;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ngoc on 4/13/2016.
 */
public class SendNotifyIntentService extends IntentService {

    public SendNotifyIntentService() {
        super("sendNotifyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String creater = intent.getStringExtra("creater");
        String name = intent.getStringExtra("name");
        long time = intent.getLongExtra("time", 0);
        int dep_id = intent.getIntExtra("dep_id", 0);
        sendNotify(creater,name,time,dep_id);
    }

    private void sendNotify(String creater, String name, long time, int dep_id) {
        final String URL = "http://hungntph04073.esy.es/notifyNewMeeting.php";
        String getField = "?creater="+creater+"&name="+name+"&time="+time+"&dep_id="+dep_id;
        try {
            java.net.URL url = new URL((URL + getField).replace(" ","%20"));
            url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
