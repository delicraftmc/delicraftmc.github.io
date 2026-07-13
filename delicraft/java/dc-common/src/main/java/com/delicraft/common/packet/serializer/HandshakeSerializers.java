package com.delicraft.common.packet.serializer;

import com.delicraft.common.packet.DLPacket;
import com.delicraft.common.packet.PacketBuffer;
import com.delicraft.common.packet.PacketSerializer;
import com.delicraft.common.packet.id.DLPacketIds;

public final class HandshakeSerializers {
  private HandshakeSerializers() {}

  public static final PacketSerializer<DLPacket.HandshakeRequest> REQUEST =
      new PacketSerializer<>() {
        @Override
        public int getPacketId() {
          return DLPacketIds.Handshake.REQUEST;
        }

        @Override
        public Class<DLPacket.HandshakeRequest> getPacketClass() {
          return DLPacket.HandshakeRequest.class;
        }

        @Override
        public void serialize(DLPacket.HandshakeRequest packet, PacketBuffer buffer) {
          buffer.writeInt(packet.protocolVersion());
          buffer.writeString(packet.clientId());
          buffer.writeString(packet.username());
        }

        @Override
        public DLPacket.HandshakeRequest deserialize(PacketBuffer buffer) {
          return new DLPacket.HandshakeRequest(
              buffer.readInt(), buffer.readString(), buffer.readString());
        }
      };

  public static final PacketSerializer<DLPacket.HandshakeResponse> RESPONSE =
      new PacketSerializer<>() {
        @Override
        public int getPacketId() {
          return DLPacketIds.Handshake.RESPONSE;
        }

        @Override
        public Class<DLPacket.HandshakeResponse> getPacketClass() {
          return DLPacket.HandshakeResponse.class;
        }

        @Override
        public void serialize(DLPacket.HandshakeResponse packet, PacketBuffer buffer) {
          buffer.writeInt(packet.protocolVersion());
          buffer.writeBoolean(packet.success());
          buffer.writeString(packet.message());
        }

        @Override
        public DLPacket.HandshakeResponse deserialize(PacketBuffer buffer) {
          return new DLPacket.HandshakeResponse(
              buffer.readInt(), buffer.readBoolean(), buffer.readString());
        }
      };
}
