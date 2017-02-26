package com.smoke.dto;

/**
 * Created by CHEN on 2016/12/1.
 *
 * 封转的数据报
 * 包括数据本身
 *
 */
public class DataGram<T> {

    private T data;               //发送的数据
    private boolean success;    //数据是否能够正确获得

    //2016 01 04
    private int status;//1 成功 0失败
    private String message;

    public DataGram() {
    }

    public DataGram(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public DataGram(T data, boolean success, int status, String message) {
        this.data = data;
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
