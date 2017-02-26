package com.smoke.dto;

/**
 * Created by CHEN on 2016/12/1.
 */
public class Message {

    private int status;//1 成功 0 失败
    private String message;

    public Message() {
    }

    public Message(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
