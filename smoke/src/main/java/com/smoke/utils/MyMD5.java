package com.smoke.utils;

import org.springframework.util.DigestUtils;

/**
 * Created by CHEN on 2016/11/26.
 *
 * 用来生产md5加密密码
 */
public class MyMD5 {
    //盐值
    private final static  String salt="#$!425%$^846(^_5&7(*$&7GA%G3652QH8A$@%WR!@";
    private final static  String salt1="#^_5QH8AH8AGA%G3$!425%$^84$@6(65#￥%2%WR!@";

    /**
     * 根据value生成 md5 值
     * @param value
     * @return
     */
    public static String getMD5(String value) {
        String code=value+salt;
        String md5= DigestUtils.md5DigestAsHex(code.getBytes());
        return md5;
    }



}
