package com.base7.jd.birdapp.datamodels;

/**
 * Created by M.Junaid Fiaz on 30-Nov-16.
 */
public class Bird {

    private String id;
    private String gender;
    private String age;
    private String category;
    private String price;
    private String eye;
    private String status;
    private String clutchId;

    //Dated
    private String dayAdded;
    private String daySell;
    private String monthAdded;
    private String monthSell;
    private String yearAdded;
    private String yearSell;

    public Bird() {
    }

    public Bird(String id, String gender, String age, String category, String price, String eye, String status, String clutchId, String dayAdded, String daySell, String monthAdded, String monthSell, String yearAdded, String yearSell) {

        this.id = id;
        this.gender = gender;
        this.age = age;
        this.category = category;
        this.price = price;
        this.eye = eye;
        this.status = status;
        this.clutchId = clutchId;
        this.dayAdded = dayAdded;
        this.daySell = daySell;
        this.monthAdded = monthAdded;
        this.monthSell = monthSell;
        this.yearAdded = yearAdded;
        this.yearSell = yearSell;

    }

//Define Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClutchId(String clutchId) {
        this.clutchId = clutchId;
    }

    public void setDayAdded(String dayAdded) {
        this.dayAdded = dayAdded;
    }

    public void setDaySell(String daySell) {
        this.daySell = daySell;
    }

    public void setMonthAdded(String monthAdded) {
        this.monthAdded = monthAdded;
    }

    public void setMonthSell(String monthSell) {
        this.monthSell = monthSell;
    }

    public void setYearAdded(String yearAdded) {
        this.yearAdded = yearAdded;
    }

    public void setYearSell(String yearSell) {
        this.yearSell = yearSell;
    }

    //Define Getters


    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getEye() {
        return eye;
    }

    public String getStatus() {
        return status;
    }

    public String getClutchId() {
        return clutchId;
    }

    public String getDayAdded() {
        return dayAdded;
    }

    public String getDaySell() {
        return daySell;
    }

    public String getMonthAdded() {
        return monthAdded;
    }

    public String getMonthSell() {
        return monthSell;
    }

    public String getYearAdded() {
        return yearAdded;
    }

    public String getYearSell() {
        return yearSell;
    }
}
