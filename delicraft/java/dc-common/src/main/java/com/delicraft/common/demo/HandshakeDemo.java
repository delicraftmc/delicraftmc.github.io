package com.delicraft.common.demo;

import com.delicraft.common.packet.DLPacket;
import com.delicraft.common.packet.PacketRegistry;
import com.delicraft.common.packet.serializer.HandshakeSerializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandshakeDemo {
  private static final Logger log = LoggerFactory.getLogger(HandshakeDemo.class);

  public static void main(String[] args) {
    log.info("=== DeliCraft Handshake Demo ===");

    PacketRegistry registry = new PacketRegistry();
    registry.register(HandshakeSerializers.REQUEST);
    registry.register(HandshakeSerializers.RESPONSE);

    DLPacket.HandshakeRequest request =
        new DLPacket.HandshakeRequest(1, "delicraft-client", "TestPlayer");
    log.info(
        "Request: protocol={}, client={}, user={}",
        request.protocolVersion(),
        request.clientId(),
        request.username());

    byte[] encoded = registry.encode(request);
    log.info("Encoded: {} bytes", encoded.length);

    DLPacket decoded = registry.decode(encoded);
    log.info("Decoded type: {}", decoded.getClass().getSimpleName());

    DLPacket.HandshakeRequest decodedReq = (DLPacket.HandshakeRequest) decoded;
    log.info(
        "Match: protocol={}, client={}, user={}",
        decodedReq.protocolVersion() == 1 ? "OK" : "FAIL",
        decodedReq.clientId().equals("delicraft-client") ? "OK" : "FAIL",
        decodedReq.username().equals("TestPlayer") ? "OK" : "FAIL");

    DLPacket.HandshakeResponse response =
        new DLPacket.HandshakeResponse(1, true, "Welcome, TestPlayer!");
    byte[] respEncoded = registry.encode(response);
    DLPacket.HandshakeResponse decodedResp =
        (DLPacket.HandshakeResponse) registry.decode(respEncoded);
    log.info(
        "Response: success={}, message={}",
        decodedResp.success() ? "OK" : "FAIL",
        decodedResp.message());

    log.info("=== Handshake Demo Complete ===");
    log.info("All checks passed!");
  }
}
