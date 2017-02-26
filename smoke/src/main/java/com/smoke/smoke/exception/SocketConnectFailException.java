package com.smoke.smoke.exception;

/**
 * Created by asus2015 on 2016/11/28.
 */
public class SocketConnectFailException extends RuntimeException{

    public SocketConnectFailException(String message) {
        super(message);
    }
}
