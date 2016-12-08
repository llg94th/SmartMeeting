package com.example.what.smartmeeting.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ngoc on 3/24/2016.
 */
public class Meetings {
    private int id;
    private String name;
    private String creater;
    private String description;
    private long time;
    private String place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        String txtTime;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        txtTime = format.format(calendar.getTime());
        return txtTime;
    }
    public long getTimeOnMiliseconds() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Meetings() {

    }

    public Meetings(int id, String name, String creater, String description, long time, String place) {

        this.id = id;
        this.name = name;
        this.creater = creater;
        this.description = description;
        this.time = time;
        this.place = place;
    }
    public static ArrayList<Meetings> createListDummyData(){
        ArrayList<Meetings> list = new ArrayList<Meetings>();
        list.add(new Meetings(1,"Họp đầu Sprint","hungntph04073","Họp đầu Spint dự án PRO112",1457744400000l,"H307"));
        list.add(new Meetings(1,"Họp giữa Sprint","hungntph04073","Họp giữa Spint dự án PRO112",1458781200000l,"H307"));
        list.add(new Meetings(1,"Họp cuối Sprint","hungntph04073","Họp cuối Spint dự án PRO112",1459386000000l,"H307"));
        return list;
    }
}
