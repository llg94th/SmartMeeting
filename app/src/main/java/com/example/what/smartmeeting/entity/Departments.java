package com.example.what.smartmeeting.entity;

/**
 * Created by ngoc on 3/24/2016.
 */
public class Departments {
    private int id;
    private String name;

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

    public Departments(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
