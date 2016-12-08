package com.example.what.smartmeeting.entity;

/**
 * Created by what on 09/04/2016.
 */
public class Position {
    private int id;
    private String name;

    public Position(int id, String name) {
        this.id = id;
        this.name = name;
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
}
