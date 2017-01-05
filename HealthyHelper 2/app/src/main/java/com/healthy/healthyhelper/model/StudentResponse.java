package com.healthy.healthyhelper.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class StudentResponse {


    /**
     * grp_name : A
     * student : xcl
     * status : active
     */

    private List<ResultBean> result;

    public static List<StudentResponse> arrayStudentResponseFromData(String str) {

        Type listType = new TypeToken<ArrayList<StudentResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String grp_name;
        private String student;
        private String status;

        public static List<ResultBean> arrayResultBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getGrp_name() {
            return grp_name;
        }

        public void setGrp_name(String grp_name) {
            this.grp_name = grp_name;
        }

        public String getStudent() {
            return student;
        }

        public void setStudent(String student) {
            this.student = student;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
