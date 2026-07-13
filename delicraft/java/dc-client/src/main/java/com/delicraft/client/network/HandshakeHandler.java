package com.delicraft.client.network;

import com.delicraft.common.packet.DLPacket;
import com.delicraft.common.packet.PacketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandshakeHandler {
  private static final Logger log = LoggerFactory.getLogger(HandshakeHandler.class);
  private final PacketRegistry registry;
  private boolean authenticated;

  public HandshakeHandler(PacketRegistry registry) {
    this.registry = registry;
  }

  public byte[] createHandshake(String username) {
    var request = new DLPacket.HandshakeRequest(1, "delicraft-client", username);
    log.info("Sending handshake for user: {}", username);
    return registry.encode(request);
  }

  @SuppressWarnings("unchecked")
  public boolean handleResponse(byte[] data) {
    var response = (DLPacket.HandshakeResponse) registry.decode(data);
    log.info("Handshake response: success={}, message={}", response.success(), response.message());
    this.authenticated = response.success();
    return authenticated;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }
}
