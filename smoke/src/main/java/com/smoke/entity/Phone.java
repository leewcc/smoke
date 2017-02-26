package com.smoke.entity;

/**
 * Created by CHEN on 2016/11/26.
 */
public class Phone {
    private int id;     //id

    private int userId;             //用户Id
    private String userPhone;         //用户手机

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(obj instanceof Phone) {
            Phone phone = (Phone)obj;
            if(phone.getId() == this.getId()) {
                return true;
            }
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
