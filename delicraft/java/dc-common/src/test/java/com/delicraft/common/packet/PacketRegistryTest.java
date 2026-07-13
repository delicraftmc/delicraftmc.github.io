package com.delicraft.common.packet;

import static org.junit.jupiter.api.Assertions.*;

import com.delicraft.common.packet.serializer.HandshakeSerializers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacketRegistryTest {
  private PacketRegistry registry;

  @BeforeEach
  void setUp() {
    registry = new PacketRegistry();
    registry.register(HandshakeSerializers.REQUEST);
    registry.register(HandshakeSerializers.RESPONSE);
  }

  @Test
  void encodeDecodeHandshakeRequest() {
    var original = new DLPacket.HandshakeRequest(1, "delicraft-client", "TestPlayer");

    byte[] encoded = registry.encode(original);
    assertNotNull(encoded);

    DLPacket.HandshakeRequest decoded = registry.decode(encoded);
    assertEquals(original.protocolVersion(), decoded.protocolVersion());
    assertEquals(original.clientId(), decoded.clientId());
    assertEquals(original.username(), decoded.username());
  }

  @Test
  void encodeDecodeHandshakeResponse() {
    var original = new DLPacket.HandshakeResponse(1, true, "Welcome!");

    byte[] encoded = registry.encode(original);
    assertNotNull(encoded);

    DLPacket.HandshakeResponse decoded = registry.decode(encoded);
    assertEquals(original.protocolVersion(), decoded.protocolVersion());
    assertEquals(original.success(), decoded.success());
    assertEquals(original.message(), decoded.message());
  }

  @Test
  void unknownPacketThrows() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          registry.encode(new DLPacket.KeepAlivePacket(123));
        });
  }
}
