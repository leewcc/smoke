package com.smoke.smoke.client;

import com.smoke.entity.SmokeStatus;
import com.smoke.smoke.Server.DatagramBuilder;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by asus2015 on 2016/12/12.
 */
public class SmokeClient {
    private static int roomId = 1;
    private static int equipmentId = 1;

    public static void main(String[] args)  {
        try {
            Socket socket = new Socket("120.27.44.83", 5555);

            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

            BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());

            int statu = 0;
            while (true) {
                byte[] status = DatagramBuilder.createDatagram(roomId, equipmentId, statu);
                for(int i = 0; i < status.length; i++) {
                    System.out.print(status[i]);
                }

                output.write(status);
                output.flush();
                System.out.println("发送数据");

                statu = buffer.readLine().length();
                statu = statu > 1 ? 1 : 0;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
