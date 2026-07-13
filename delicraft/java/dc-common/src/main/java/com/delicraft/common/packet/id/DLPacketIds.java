package com.delicraft.common.packet.id;

public final class DLPacketIds {
  private DLPacketIds() {}

  public static final class Handshake {
    private Handshake() {}

    public static final int REQUEST = 0x00;
    public static final int RESPONSE = 0x01;
  }

  public static final int KEEP_ALIVE = 0x02;
  public static final int DISCONNECT = 0x03;
  public static final int PLAYER_DATA_REQUEST = 0x10;
  public static final int PLAYER_DATA_RESPONSE = 0x11;
  public static final int MOD_LIST_REQUEST = 0x20;
  public static final int MOD_LIST_RESPONSE = 0x21;
}
