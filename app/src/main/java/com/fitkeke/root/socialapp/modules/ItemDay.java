package com.fitkeke.root.socialapp.modules;

public class ItemDay {

    int id;
    String name;
    String date;
    String state;
    int dayNum;

    public ItemDay() {

    }

    public ItemDay(int id, String name, String date, String state, int dayNum) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.state = state;
        this.dayNum = dayNum;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }
}
