package com.example.what.smartmeeting.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.what.smartmeeting.entity.Account;
import com.example.what.smartmeeting.entity.Departments;
import com.example.what.smartmeeting.entity.Position;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by what on 09/04/2016.
 */
public class AccountLogin {
    public static Account account_logined;
    public static Departments departments_logined;
    public static Position position_loginded;

    public static String getTimeFromLong(long time) {
        String txtTime;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        txtTime = format.format(calendar.getTime());
        return txtTime;
    }
}

