package com.smoke.smoke.Server;

import com.smoke.entity.SmokeStatus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by asus2015 on 2016/12/3.
 */
public class DatagramBuilder {
    private static final byte[] NORMAL = new byte[]{0x4E,0x4f,0x52,0x4d,0x41,0x4c,0x3c,0x3c};
    private static final byte[] WARNING = new byte[]{0x57,0x41,0x52,0x4e,0x49,0x4e,0x47,0x21};
    private static final byte[] END = new byte[] {0x0d,0x0a};

    public static byte[] createDatagram(int roomId, int equipmentId, int status) {
        SmokeStatus smokeStatus = new SmokeStatus();

        smokeStatus.setRoomId(roomId);

        smokeStatus.seteId(equipmentId);

        smokeStatus.setPm1_0((int)(Math.random() * 10));

        smokeStatus.setPm2_5((int)(Math.random() * 10));

        smokeStatus.setPm10((int)(Math.random() * 10));

        return convert(smokeStatus, status);
    }

    private static byte[] convert(SmokeStatus smokeStatus, int status) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(18);

        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        byteBuffer.put((byte)smokeStatus.getRoomId());

        byteBuffer.put((byte)smokeStatus.geteId());

        byteBuffer.put(status == 0 ? NORMAL : WARNING );

        byteBuffer.putShort((short)smokeStatus.getPm1_0());

        byteBuffer.putShort((short)smokeStatus.getPm2_5());

        byteBuffer.putShort((short)smokeStatus.getPm10());

        byteBuffer.put(END);

        return byteBuffer.array();
    }
}
