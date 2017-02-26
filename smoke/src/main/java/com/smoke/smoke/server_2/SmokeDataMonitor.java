package com.smoke.smoke.server_2;

import com.smoke.dao.HistoryDao;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by asus2015 on 2016/12/12.
 */
public class SmokeDataMonitor implements Runnable{
    private static Logger LOGGER = LoggerFactory.getLogger(SmokeDataHandler.class);

    private int port;

    public SmokeDataMonitor(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new SmokeDataMonitor.ChildChanelHandler());

            // 绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();

            LOGGER.debug("监听开始，等待烟雾器连接");

            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }


    private class ChildChanelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new SmokeDataHandler());
        }
    }
}
