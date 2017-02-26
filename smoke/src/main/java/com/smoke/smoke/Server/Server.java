package com.smoke.smoke.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 数据模拟服务器.
 */
public class Server {
    static ServerSocket serverSocket;

    static int port;

    public static void main(String[] args) throws IOException, InterruptedException {
        port = 5555;

        serverSocket = new ServerSocket(port);

        while(true) {
            System.out.println("开始监听连接");
            Socket socket = serverSocket.accept();

            System.out.println("有新连接进来");
            // 定时发送数据报给客户端
            new ClientHandler(socket).handle();
        }
    }
}
