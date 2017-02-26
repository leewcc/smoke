package com.smoke.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by asus2015 on 2016/11/30.
 */
public class ByteUtil {

    public static int byte2ToInt(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);


        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put(bytes[0]);
        byteBuffer.put(bytes[1]);

        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    public static int byte4ToInt(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        byteBuffer.put(bytes[0]);
        byteBuffer.put(bytes[1]);
        byteBuffer.put(bytes[3]);
        byteBuffer.put(bytes[4]);

        return byteBuffer.getInt();
    }
}
