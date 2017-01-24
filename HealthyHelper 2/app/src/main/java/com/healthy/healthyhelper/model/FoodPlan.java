package com.healthy.healthyhelper.model;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class FoodPlan {


    /**
     * day : Tu（Tu）
     * breakfast : Fat-free milk(200ml) + 1 Egg yolk + 2 Egg white + Grain gruel(250g) + 1 Serving fruit
     * lunch : Fish(150g) + Grain rice(150g) + Vegetable(unlimited) + Shrimp(150g) + Vegetable(unlimited)
     * dinner : Shrimp(150g) + Vegetable(unlimited)
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
        private String breakfast;
        private String lunch;
        private String dinner;
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

        public String getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(String breakfast) {
            this.breakfast = breakfast;
        }

        public String getLunch() {
            return lunch;
        }

        public void setLunch(String lunch) {
            this.lunch = lunch;
        }

        public String getDinner() {
            return dinner;
        }

        public void setDinner(String dinner) {
            this.dinner = dinner;
        }
    }
}
