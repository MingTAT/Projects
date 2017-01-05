package com.healthy.healthyhelper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class Teacher {

    private int id;
    private String name;
    private String grp_name;

    public Teacher(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Teacher(String name, String grp_name) {
        this.name = name;
        this.grp_name = grp_name;
    }

    public String getGrp_name() {
        return grp_name;
    }

    public void setGrp_name(String grp_name) {
        this.grp_name = grp_name;
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

    /**
     *
     * 获取教师列表
     *
     * @return
     */
    public List<Teacher> getTeacherList(){
        ArrayList<Teacher> list = new ArrayList<>();
        return list;
    }
}
