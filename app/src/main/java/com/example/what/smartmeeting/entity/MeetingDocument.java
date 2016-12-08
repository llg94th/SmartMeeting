package com.example.what.smartmeeting.entity;

/**
 * Created by ngoc on 4/7/2016.
 */
public class MeetingDocument {
    private int id;
    private  String name;
    private String doc_url;

    public MeetingDocument(int id, String name, String doc_url) {
        this.id = id;
        this.name = name;
        this.doc_url = doc_url;
    }

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

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }
}
