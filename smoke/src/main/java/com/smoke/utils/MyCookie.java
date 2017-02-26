package com.smoke.utils;

import sun.util.resources.cldr.aa.CalendarData_aa_ER;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.smoke.utils.MyRSA.getKeyPair;
import static javafx.scene.input.KeyCode.R;

/**
 * Created by CHEN on 2016/11/26.
 * <p>
 * 该类用来生成cookie的随机码，这个随机码只能使用一周
 */
public class MyCookie {

    public static String getCookie(String idPassword) {
/*       String cookie="";
        MyRSA myrsa=new MyRSA();
        Calendar c = Calendar.getInstance();
        idPassword = idPassword+","+c.get(Calendar.WEEK_OF_YEAR);//设置保存一周
        //使用rsa进行加密
        try {
            RSAPublicKey rsa=(RSAPublicKey) MyRSA.generateKeyPair().getPublic();
            byte[] en=myrsa.encrypt(myrsa.getKeyPair().getPublic(),idPassword.getBytes());
            cookie=new String(en,"ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;*/
        Calendar c = Calendar.getInstance();
        idPassword = idPassword+c.get(Calendar.WEEK_OF_YEAR);//设置保存一周
        String cookie=MyMD5.getMD5(idPassword);
        return cookie;
    }
}
