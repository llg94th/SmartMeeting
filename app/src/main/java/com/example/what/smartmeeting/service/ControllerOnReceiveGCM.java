package com.example.what.smartmeeting.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.views.MeetingDetailActivity;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ControllerOnReceiveGCM extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String tTime = data.getString("time");
        Log.d("MY_WATCH", "tTime = " + tTime);
        long time = Long.parseLong(tTime);
        String message = "Nội dung: " + data.getString("name") + ". Thời gian: " + AccountLogin.getTimeFromLong(time);
        String info_url = "http://hungntph04073.esy.es/get-account-info-by-id.php?id=" + data.getString("creater");
        String jsonText = "";
        try {
            URL url = new URL(info_url);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            int byteCharacter2;
            while ((byteCharacter2 = is.read()) != -1) {
                char c = (char) byteCharacter2;
                jsonText += c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("MY_WATCH", "createNotification = "+System.currentTimeMillis());
        createNotification(name, message, Integer.parseInt(data.getString("id")));
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body, int meeting_id) {
        Bitmap drawable = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setLargeIcon(drawable).setContentTitle(title+" đã tạo cuộc họp mới").setSmallIcon(R.drawable.ic_stat_ic_notifytion)
                .setContentText(body);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mBuilder.setSound(alarmSound).setAutoCancel(true);
        Intent intent = new Intent(getBaseContext(), MeetingDetailActivity.class);
        intent.setAction(meeting_id+"");
        intent.putExtra("meeting_id", meeting_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.addAction(R.drawable.ic_action_view, "Xem chi tiết cuộc họp", pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}