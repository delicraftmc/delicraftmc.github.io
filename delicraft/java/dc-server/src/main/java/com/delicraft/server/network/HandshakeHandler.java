package com.delicraft.server.network;

import com.delicraft.common.packet.DLPacket;
import com.delicraft.common.packet.PacketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandshakeHandler {
  private static final Logger log = LoggerFactory.getLogger(HandshakeHandler.class);
  private static final int EXPECTED_PROTOCOL = 1;
  private static final String EXPECTED_CLIENT = "delicraft-client";
  private final PacketRegistry registry;

  public HandshakeHandler(PacketRegistry registry) {
    this.registry = registry;
  }

  public DLPacket.HandshakeResponse handle(DLPacket.HandshakeRequest request) {
    log.info(
        "Handshake from {} (client: {}, protocol: {})",
        request.username(),
        request.clientId(),
        request.protocolVersion());

    if (request.protocolVersion() != EXPECTED_PROTOCOL) {
      return new DLPacket.HandshakeResponse(
          EXPECTED_PROTOCOL, false, "Protocol mismatch. Expected: " + EXPECTED_PROTOCOL);
    }

    if (!EXPECTED_CLIENT.equals(request.clientId())) {
      return new DLPacket.HandshakeResponse(
          EXPECTED_PROTOCOL, false, "Unknown client: " + request.clientId());
    }

    return new DLPacket.HandshakeResponse(
        EXPECTED_PROTOCOL, true, "Welcome, " + request.username() + "!");
  }

  @SuppressWarnings("unchecked")
  public byte[] handleRaw(byte[] data) {
    var request = (DLPacket.HandshakeRequest) registry.decode(data);
    DLPacket.HandshakeResponse response = handle(request);
    return registry.encode(response);
  }
}
