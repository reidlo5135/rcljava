package net.wavem.rcljava.rosidl.message.std_msgs;

import net.wavem.rcljava.rosidl.infra.RCLMessage;
import net.wavem.rcljava.rosidl.infra.RCLTypeSupport;
import net.wavem.rcljava.rosidl.message.builtin_interfaces.Time;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Header extends RCLMessage implements RCLTypeSupport<Header> {
    private final Time stamp;
    private final java.lang.String frame_id;

    public Header(Time stamp, java.lang.String frame_id) {
        this.stamp = stamp;
        this.frame_id = frame_id;
    }

    public Time getStamp() {
        return stamp;
    }

    public java.lang.String getFrame_id() {
        return frame_id;
    }

    @Override
    public Header read(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data);
        buf.order(ByteOrder.LITTLE_ENDIAN);

        Time time = stamp.read(data);
        buf.position(8);

        int len = buf.getInt();
        StringBuilder frame_id = new StringBuilder();

        while (len-- > 0) {
            frame_id.append((char) (buf.get() & 0xFFFF));
        }

        return new Header(time, frame_id.toString());
    }
}