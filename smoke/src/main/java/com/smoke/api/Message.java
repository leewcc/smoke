package com.smoke.api;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.H;

/**
 * Created by CHEN on 2016/12/2.
 */
public class Message {

    public static boolean sendMessage(String userPhone,String userName,String roomName) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://api.netease.im/sms/sendtemplate.action";
        HttpPost post = new HttpPost(url);

        String appKey = "7c55814748f9efd52652537dc69f8077";
        String appSecret = "dda3cb34c3f4";
        String nonce = "chenjunmingzuishuai";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);

        //设置请求头
        post.addHeader("AppKey", appKey);
        post.addHeader("CurTime", curTime);
        post.addHeader("Nonce", nonce);
        post.addHeader("CheckSum", checkSum);
        post.addHeader("charset", "utf-8");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        //设置数据内容
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("templateid", "3036017"));
        nvps.add(new BasicNameValuePair("mobiles", "['"+userPhone+"']"));
        nvps.add(new BasicNameValuePair("params", "['"+userName+"','"+roomName+"']"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //执行请求
        try {
            HttpResponse r = httpClient.execute(post);
            System.out.println(EntityUtils.toString(r.getEntity(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://api.netease.im/sms/sendtemplate.action";
        HttpPost post = new HttpPost(url);

        String appKey = "7c55814748f9efd52652537dc69f8077";
        String appSecret = "dda3cb34c3f4";
        String nonce = "sfasfasfas";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);

        //设置请求头
        post.addHeader("AppKey", appKey);
        post.addHeader("CurTime", curTime);
        post.addHeader("Nonce", nonce);
        post.addHeader("CheckSum", checkSum);
        post.addHeader("charset", "utf-8");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        //设置数据内容
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("templateid", "3041013"));
        nvps.add(new BasicNameValuePair("mobiles", "['15914370252']"));
        nvps.add(new BasicNameValuePair("params", "['俊铭','102']"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //执行请求
        try {
            HttpResponse r = httpClient.execute(post);
            System.out.println(EntityUtils.toString(r.getEntity(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
