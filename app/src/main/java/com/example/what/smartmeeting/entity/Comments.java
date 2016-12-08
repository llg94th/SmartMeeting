package com.example.what.smartmeeting.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ngoc on 3/27/2016.
 */
public class Comments {
    private int id;
    private String content;
    private String account_id;
    private int value;
    private int meeting_id;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getTime() {
        String txtTime;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        txtTime = format.format(calendar.getTime());
        return txtTime;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Comments(int id, String content, String account_id, int value, int meeting_id, long time) {

        this.id = id;
        this.content = content;
        this.account_id = account_id;
        this.value = value;
        this.meeting_id = meeting_id;
        this.time = time;
    }
    public static ArrayList<Comments> createListDummyData(){
        ArrayList<Comments> list = new ArrayList<Comments>();
        list.add(new Comments(1,"Breakfast procuring nay end happiness allowance assurance frankness","Nguyễn Tuấn Hùng",0,1,1457744400000l));
        list.add(new Comments(2,"Met simplicity nor difficulty unreserved who","Mầu Hồng Phi",0,1,1457744400000l));
        list.add(new Comments(3,"Breakfast procuring nay end happiness allowance assurance frankness","Nguyễn Tuấn Hùng",0,1,1457744400000l));
        list.add(new Comments(3,"Breakfast procuring nay end happiness allowance assurance frankness","Nguyễn Tuấn Hùng",0,1,1457744400000l));
        list.add(new Comments(3,"Breakfast procuring nay end happiness allowance assurance frankness","Nguyễn Tuấn Hùng",0,1,1457744400000l));
        return list;
    }
}
