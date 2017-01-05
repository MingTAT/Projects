package com.healthy.healthyhelper.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class Announcement {


    /**
     * username : Kohaku
     * text : Hello World!
     * time : 8:30pm 4/15/2016
     */

    private String username;
    private String text;
    private String time;

    public static List<Announcement> arrayAnnouncementFromData(String str) {

        Type listType = new TypeToken<ArrayList<Announcement>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
