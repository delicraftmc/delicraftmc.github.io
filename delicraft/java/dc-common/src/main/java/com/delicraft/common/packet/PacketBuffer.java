package com.delicraft.common.packet;

import java.util.UUID;

public class PacketBuffer {
  private byte[] data;
  private int readPos;
  private int writePos;

  public PacketBuffer() {
    this.data = new byte[64];
  }

  public PacketBuffer(byte[] data) {
    this.data = data;
    this.readPos = 0;
    this.writePos = data.length;
  }

  public byte[] toByteArray() {
    byte[] result = new byte[writePos];
    System.arraycopy(data, 0, result, 0, writePos);
    return result;
  }

  public void writeByte(int value) {
    ensureCapacity(1);
    data[writePos++] = (byte) (value & 0xFF);
  }

  public int readByte() {
    return data[readPos++] & 0xFF;
  }

  public void writeInt(int value) {
    ensureCapacity(4);
    data[writePos++] = (byte) (value >> 24);
    data[writePos++] = (byte) (value >> 16);
    data[writePos++] = (byte) (value >> 8);
    data[writePos++] = (byte) value;
  }

  public int readInt() {
    return (data[readPos++] & 0xFF) << 24
        | (data[readPos++] & 0xFF) << 16
        | (data[readPos++] & 0xFF) << 8
        | data[readPos++] & 0xFF;
  }

  public void writeLong(long value) {
    ensureCapacity(8);
    for (int i = 7; i >= 0; i--) {
      data[writePos++] = (byte) (value >> (i * 8));
    }
  }

  public long readLong() {
    long result = 0;
    for (int i = 7; i >= 0; i--) {
      result = (result << 8) | (data[readPos++] & 0xFF);
    }
    return result;
  }

  public void writeString(String value) {
    byte[] strBytes = value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    writeInt(strBytes.length);
    ensureCapacity(strBytes.length);
    System.arraycopy(strBytes, 0, data, writePos, strBytes.length);
    writePos += strBytes.length;
  }

  public String readString() {
    int length = readInt();
    String result = new String(data, readPos, length, java.nio.charset.StandardCharsets.UTF_8);
    readPos += length;
    return result;
  }

  public void writeUUID(UUID value) {
    writeLong(value.getMostSignificantBits());
    writeLong(value.getLeastSignificantBits());
  }

  public UUID readUUID() {
    return new UUID(readLong(), readLong());
  }

  public void writeBoolean(boolean value) {
    writeByte(value ? 1 : 0);
  }

  public boolean readBoolean() {
    return readByte() != 0;
  }

  private void ensureCapacity(int needed) {
    int minCapacity = writePos + needed;
    if (minCapacity > data.length) {
      int newCapacity = Math.max(data.length * 2, minCapacity);
      byte[] newData = new byte[newCapacity];
      System.arraycopy(data, 0, newData, 0, data.length);
      data = newData;
    }
  }
}
