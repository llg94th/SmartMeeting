package com.example.what.smartmeeting.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by what on 29/03/2016.
 */
public class Account {
    private String id;
    private String password;
    private String name;
    private String email;
    private int position_id;
    private int department_id;
    private boolean isFistlogin;
    private String photo_profile_url;
    private String position_name;
    private String department_name;
      Bitmap bitmap;

    public Account(String id, String password, String name, int position, String email, boolean isFistlogin, String photo_profile_url, int department) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.position_id = position;
        this.email = email;
        this.isFistlogin = isFistlogin;
        this.photo_profile_url = photo_profile_url;
        this.department_id = department;
        this.bitmap = loadImage();

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public Account(String id, String email, String password, String name, int department_id, int position_id, boolean isFistlogin, String position_name, String photo_profile_url, String department_name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.department_id = department_id;
        this.position_id = position_id;
        this.isFistlogin = isFistlogin;
        this.position_name = position_name;
        this.photo_profile_url = photo_profile_url;
        this.department_name = department_name;
        this.bitmap = loadImage();
    }

    public Account(String email, String name, String position_name, String department_name, String photo_profile_url) {
        this.email = email;
        this.name = name;
        this.position_name = position_name;
        this.department_name = department_name;
        this.photo_profile_url = photo_profile_url;
        this.bitmap=this.loadImage();
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public Account(String id, String pw) {
        this.id = id;
        this.password=pw;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto_profile_url() {
        return photo_profile_url;
    }

    public void setPhoto_profile_url(String photo_profile_url) {
        this.photo_profile_url = photo_profile_url;
    }

    public boolean isFistlogin() {
        return isFistlogin;
    }

    public void setIsFistlogin(boolean isFistlogin) {
        this.isFistlogin = isFistlogin;
    }

    public int getDepartment() {
        return department_id;
    }

    public void setDepartment(int department) {
        this.department_id = department;
    }

    public int getPosition() {
        return position_id;
    }

    public void setPosition(int position) {
        this.position_id = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap loadImage() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bitmap=null;
        URL imageURL = null;
        try {
            imageURL = new URL(this.getPhoto_profile_url());
            HttpURLConnection connection = (HttpURLConnection) imageURL
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);// Convert to bitmap

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bitmap;
    }
}
