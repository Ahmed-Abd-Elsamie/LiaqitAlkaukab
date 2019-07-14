package com.fitkeke.root.socialapp.modules;

import android.text.format.Time;

public class ItemFood {

    int id;
    String carb;
    String protin;
    String fats;
    String aliaf;
    String time;
    String state;
    int request;

    public ItemFood() {

    }

    public ItemFood(int id, String carb, String protin, String fats, String aliaf, String time, String state, int request) {
        this.id = id;
        this.carb = carb;
        this.protin = protin;
        this.fats = fats;
        this.aliaf = aliaf;
        this.time = time;
        this.state = state;
        this.request = request;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarb() {
        return carb;
    }

    public void setCarb(String carb) {
        this.carb = carb;
    }

    public String getProtin() {
        return protin;
    }

    public void setProtin(String protin) {
        this.protin = protin;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getAliaf() {
        return aliaf;
    }

    public void setAliaf(String aliaf) {
        this.aliaf = aliaf;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }
}
