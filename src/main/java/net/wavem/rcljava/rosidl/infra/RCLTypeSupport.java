package net.wavem.rcljava.rosidl.infra;

public interface RCLTypeSupport<T> {
    T read(byte[] data);
}
