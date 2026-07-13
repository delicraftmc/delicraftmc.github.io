package com.delicraft.common.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PacketRegistry {
  private final Map<Integer, PacketSerializer<?>> serializers = new HashMap<>();
  private final Map<Class<?>, Integer> classToId = new HashMap<>();

  public <T extends DLPacket> void register(PacketSerializer<T> serializer) {
    serializers.put(serializer.getPacketId(), serializer);
    classToId.put(serializer.getPacketClass(), serializer.getPacketId());
  }

  @SuppressWarnings("unchecked")
  public <T extends DLPacket> Optional<PacketSerializer<T>> getSerializer(int packetId) {
    return Optional.ofNullable((PacketSerializer<T>) serializers.get(packetId));
  }

  @SuppressWarnings("unchecked")
  public <T extends DLPacket> Optional<PacketSerializer<T>> getSerializer(Class<T> packetClass) {
    Integer id = classToId.get(packetClass);
    if (id == null) return Optional.empty();
    return Optional.ofNullable((PacketSerializer<T>) serializers.get(id));
  }

  @SuppressWarnings("unchecked")
  public <T extends DLPacket> byte[] encode(T packet) {
    int id = getPacketId(packet.getClass());
    PacketSerializer<T> serializer = (PacketSerializer<T>) serializers.get(id);
    if (serializer == null) {
      throw new IllegalArgumentException("No serializer for packet: " + packet.getClass());
    }
    PacketBuffer buffer = new PacketBuffer();
    buffer.writeByte(id);
    serializer.serialize(packet, buffer);
    return buffer.toByteArray();
  }

  @SuppressWarnings("unchecked")
  public <T extends DLPacket> T decode(byte[] data) {
    PacketBuffer buffer = new PacketBuffer(data);
    int id = buffer.readByte();
    PacketSerializer<T> serializer = (PacketSerializer<T>) serializers.get(id);
    if (serializer == null) {
      throw new IllegalArgumentException("No serializer for packet id: " + id);
    }
    return serializer.deserialize(buffer);
  }

  public int getPacketId(Class<?> packetClass) {
    Integer id = classToId.get(packetClass);
    if (id == null) {
      throw new IllegalArgumentException("Unknown packet class: " + packetClass);
    }
    return id;
  }
}
