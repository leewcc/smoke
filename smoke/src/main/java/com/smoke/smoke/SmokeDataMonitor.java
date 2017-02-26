//package com.smoke.smoke;
//
//import com.smoke.smoke.exception.SocketConnectFailException;
//import org.apache.ibatis.annotations.SelectKey;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.SocketChannel;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by asus2015 on 2016/11/29.
// */
//public class SmokeDataMonitor implements Runnable {
//    private static Logger LOGGER = LoggerFactory.getLogger(SmokeDataMonitor.class);
//
//    private final int port;     // 需监听的端口
//
//    private final String host;  // 需监听的主机
//
//    private SocketChannel socketChannel;
//
//    private Selector selector;
//
//    private ExecutorService threadPool;
//
//    public SmokeDataMonitor(int port, String host) throws SocketConnectFailException {
//        this.port = port;
//        this.host = host;
//        init();
//        threadPool = Executors.newFixedThreadPool(1);   // 创建线程池
//    }
//
//    // socket 执行初始化,，进程远程连接
//    private void init() throws SocketConnectFailException {
//        try {
//            socketChannel = SocketChannel.open();
//
//            socketChannel.configureBlocking(false);
//            selector = Selector.open();
//
//            socketChannel.register(selector, SelectionKey.OP_CONNECT);
//
//            socketChannel.connect(new InetSocketAddress(host, port));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new SocketConnectFailException(String.format("connect server %s port %d failure,please check your network or host correct", host, port));
//        }
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            ByteBuffer byteBuffer = ByteBuffer.allocate(900);   // 创建一个 900 容量大小的数组来存放网络读取的内容（单个报文的长度为 18，故需设置成 18 的倍数）
//
//            // 执行读取操作，若远程服务器没有数据到达，则阻塞，一直到有数据报到达
//            while (true) {
//                System.out.println("开始等待服务器发送数据");
//
//                selector.select();
//
//                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Iterator<SelectionKey> iterator = selectionKeys.iterator();
//
//                while (iterator.hasNext()) {
//
//                    SelectionKey key = iterator.next();
//                    iterator.remove();
//
//                    SocketChannel channel = (SocketChannel) key.channel();
//
//
//                    if (key.isConnectable()) {
//                        if (channel.isConnectionPending()) {
//                            channel.finishConnect();
//                        }
//                        channel.configureBlocking(false);
//                        channel.register(selector, SelectionKey.OP_READ);
//
//                    } else if (key.isReadable()) {
//                        // 读取数据
//                        channel.read(byteBuffer);
//
//                        LOGGER.debug("当前位置：" + byteBuffer.position() );
//
//                        byteBuffer.flip();
//                        int availableLength = byteBuffer.remaining();
//                        LOGGER.debug("此次有" + availableLength + "数据");
//
//                        if (availableLength == 0) {
//                            // 若读取到的数据可用数据长度为 0，则进入下一次读取
//                            continue;
//
//                        } else if (availableLength == -1) {
//                            // 若读取到数据报可用数据长度为 -1，则该 socket 连接已到底，代表连接中断
//                            key.cancel();
//                            socketChannel.close();
//                            break;
//
//                        } else {
//                            // 将解析和处理数据报任务提交给线程池
//                            threadPool.submit(new SmokeDataHandler(byteBuffer.array().clone(), availableLength));
//                        }
//                    }
//
//                    byteBuffer.clear();
//                }
//
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(selector != null) {
//                try {
//                    selector.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        String host = "120.27.44.83";
//
//        int port = 5555;
//
//        Thread thread = new Thread(new SmokeDataMonitor(port, host));
//
//        thread.start();
//    }
//}
