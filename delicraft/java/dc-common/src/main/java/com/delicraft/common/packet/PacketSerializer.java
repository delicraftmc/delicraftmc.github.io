package com.delicraft.common.packet;

public interface PacketSerializer<T extends DLPacket> {
  void serialize(T packet, PacketBuffer buffer);

  T deserialize(PacketBuffer buffer);

  int getPacketId();

  Class<T> getPacketClass();
}
