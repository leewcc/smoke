package com.smoke.entity;

/**
 * Created by CHEN on 2016/11/26.
 */
public class Manager extends User{

    private String managerId;     //工号
    private String password;      //密码
    private int powerLimit;      //权限
    private String cookieValue;    //cookie


    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }
}
