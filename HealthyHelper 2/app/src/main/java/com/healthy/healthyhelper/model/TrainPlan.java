package com.healthy.healthyhelper.model;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class TrainPlan {


    /**
     * day : Mo
     * exe_type : thigh
     * exe_name : Lunge
     * num_set : 4
     * num_repeat : 20
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String day;
        private String exe_type;
        private String exe_name;
        private String num_set;
        private String num_repeat;
        private boolean hasTitle = false;

        public boolean isHasTitle() {
            return hasTitle;
        }

        public void setHasTitle(boolean hasTitle) {
            this.hasTitle = hasTitle;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getExe_type() {
            return exe_type;
        }

        public void setExe_type(String exe_type) {
            this.exe_type = exe_type;
        }

        public String getExe_name() {
            return exe_name;
        }

        public void setExe_name(String exe_name) {
            this.exe_name = exe_name;
        }

        public String getNum_set() {
            return num_set;
        }

        public void setNum_set(String num_set) {
            this.num_set = num_set;
        }

        public String getNum_repeat() {
            return num_repeat;
        }

        public void setNum_repeat(String num_repeat) {
            this.num_repeat = num_repeat;
        }
    }
}
