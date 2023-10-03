package net.wavem.rcljava.rosidl.message.builtin_interfaces;

import net.wavem.rcljava.rosidl.infra.RCLMessage;
import net.wavem.rcljava.rosidl.infra.RCLTypeSupport;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Time extends RCLMessage implements RCLTypeSupport<Time> {
    private final int sec;
    private final int nanosec;

    public Time(int sec, int nanosec) {
        this.sec = sec;
        this.nanosec = nanosec;
    }

    public int getSec() {
        return sec;
    }

    public int getNanosec() {
        return nanosec;
    }

    @Override
    public Time read(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data);
        buf.order(ByteOrder.LITTLE_ENDIAN);

        int sec = buf.getInt();
        int nanosec = buf.getInt();

        return new Time(sec, nanosec);
    }
}