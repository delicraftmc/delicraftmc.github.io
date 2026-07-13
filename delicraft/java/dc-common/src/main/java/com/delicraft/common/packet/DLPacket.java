package com.delicraft.common.packet;

import com.delicraft.common.packet.id.DLPacketIds;

public sealed interface DLPacket
    permits DLPacket.HandshakeRequest,
        DLPacket.HandshakeResponse,
        DLPacket.KeepAlivePacket,
        DLPacket.DisconnectPacket {

  int getId();

  PacketDirection getDirection();

  record HandshakeRequest(int protocolVersion, String clientId, String username)
      implements DLPacket {
    @Override
    public int getId() {
      return DLPacketIds.Handshake.REQUEST;
    }

    @Override
    public PacketDirection getDirection() {
      return PacketDirection.SERVERBOUND;
    }
  }

  record HandshakeResponse(int protocolVersion, boolean success, String message)
      implements DLPacket {
    @Override
    public int getId() {
      return DLPacketIds.Handshake.RESPONSE;
    }

    @Override
    public PacketDirection getDirection() {
      return PacketDirection.CLIENTBOUND;
    }
  }

  record KeepAlivePacket(long timestamp) implements DLPacket {
    @Override
    public int getId() {
      return DLPacketIds.KEEP_ALIVE;
    }

    @Override
    public PacketDirection getDirection() {
      return PacketDirection.SERVERBOUND;
    }
  }

  record DisconnectPacket(String reason) implements DLPacket {
    @Override
    public int getId() {
      return DLPacketIds.DISCONNECT;
    }

    @Override
    public PacketDirection getDirection() {
      return PacketDirection.CLIENTBOUND;
    }
  }
}
