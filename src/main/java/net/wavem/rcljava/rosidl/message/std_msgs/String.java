package net.wavem.rcljava.rosidl.message.std_msgs;

import net.wavem.rcljava.rosidl.infra.RCLMessage;
import net.wavem.rcljava.rosidl.infra.RCLTypeSupport;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class String extends RCLMessage {
    private final java.lang.String data;

    public String(java.lang.String data) {
        this.data = data;
    }

    public java.lang.String getData() {
        return data;
    }

    public static String build(String data) {
        return new String(data.data);
    }

    public static byte[] write(String data) {
        java.lang.String stringData = data.getData();
        int stringDataLen = stringData.length() + 1;
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES * 2 + stringDataLen);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(stringDataLen);
        buf.put(stringData.getBytes());

        return buf.array();
    }

    public static String read(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data);
        buf.order(ByteOrder.LITTLE_ENDIAN);

        int len = buf.getInt();
        StringBuilder strData = new StringBuilder();

        while (len-- > 0) {
            strData.append((char) (buf.get() & 0xFFFF));
        }

        return new String(strData.toString());
    }
}
