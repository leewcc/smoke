package com.smoke.enums;

/**
 * Created by CHEN on 2016/11/26.
 */
public enum SmokeStatus {

    NURMAL(new byte[]{},"正常"),   //正常的情况
    WARNING(new byte[]{},"警报");  //异常的情况
    
    byte[] status;
    String name;

    SmokeStatus(byte[] status,String name) {
        this.status=status;
        this.name=name;
    }

    /**
     * json 转化enum专门的转化器
     * @param status
     * @return
     */
    public static SmokeStatus stateOf(int status) {
        for(SmokeStatus s:values()) {
            if(s.getStatus().equals(status)) {
                return s;
            }
        }
        return null;
    }


    public byte[] getStatus() {
        return status;
    }

    public void setStatus(byte[] status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
