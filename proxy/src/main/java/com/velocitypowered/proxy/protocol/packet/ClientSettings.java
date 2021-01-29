package com.velocitypowered.proxy.protocol.packet;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ClientSettings implements MinecraftPacket {

  private @Nullable String locale;
  private byte viewDistance;
  private int chatVisibility;
  private boolean chatColors;
  private byte difficulty; // 1.7 Protocol
  private short skinParts;
  private int mainHand;
  private @MonotonicNonNull Boolean useShieldOnCrouch;

  public ClientSettings() {
  }

  public ClientSettings(String locale, byte viewDistance, int chatVisibility, boolean chatColors,
      short skinParts, int mainHand, Boolean useShieldOnCrouch) {
    this.locale = locale;
    this.viewDistance = viewDistance;
    this.chatVisibility = chatVisibility;
    this.chatColors = chatColors;
    this.skinParts = skinParts;
    this.mainHand = mainHand;
    this.useShieldOnCrouch = useShieldOnCrouch;
  }

  public String getLocale() {
    if (locale == null) {
      throw new IllegalStateException("No locale specified");
    }
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public byte getViewDistance() {
    return viewDistance;
  }

  public void setViewDistance(byte viewDistance) {
    this.viewDistance = viewDistance;
  }

  public int getChatVisibility() {
    return chatVisibility;
  }

  public void setChatVisibility(int chatVisibility) {
    this.chatVisibility = chatVisibility;
  }

  public boolean isChatColors() {
    return chatColors;
  }

  public void setChatColors(boolean chatColors) {
    this.chatColors = chatColors;
  }

  public short getSkinParts() {
    return skinParts;
  }

  public void setSkinParts(short skinParts) {
    this.skinParts = skinParts;
  }

  public int getMainHand() {
    return mainHand;
  }

  public void setMainHand(int mainHand) {
    this.mainHand = mainHand;
  }

  public Boolean getUseShieldOnCrouch() {
    if (useShieldOnCrouch == null) {
      throw new IllegalStateException("useShieldOnCrouch not present");
    }
    return useShieldOnCrouch;
  }

  public void setUseShieldOnCrouch(Boolean useShieldOnCrouch) {
    this.useShieldOnCrouch = useShieldOnCrouch;
  }

  @Override
  public String toString() {
    return "ClientSettings{"
        + "locale='" + locale + '\''
        + ", viewDistance=" + viewDistance
        + ", chatVisibility=" + chatVisibility
        + ", chatColors=" + chatColors
        + ", skinParts=" + skinParts
        + ", mainHand=" + mainHand
        + ", useShieldOnCrouch=" + useShieldOnCrouch
        + '}';
  }

  @Override
  public void decode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion version) {
    this.locale = ProtocolUtils.readString(buf, 16);
    this.viewDistance = buf.readByte();
    this.chatVisibility = ProtocolUtils.readVarInt(buf);
    this.chatColors = buf.readBoolean();

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_7_6) <= 0) {
      this.difficulty = buf.readByte();
    }

    this.skinParts = buf.readUnsignedByte();

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_9) >= 0) {
      this.mainHand = ProtocolUtils.readVarInt(buf);
    }

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_16_COMBAT_6) >= 0) {
      this.useShieldOnCrouch = buf.readBoolean();
    }
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion version) {
    if (locale == null) {
      throw new IllegalStateException("No locale specified");
    }
    ProtocolUtils.writeString(buf, locale);
    buf.writeByte(viewDistance);
    ProtocolUtils.writeVarInt(buf, chatVisibility);
    buf.writeBoolean(chatColors);

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_7_6) <= 0) {
      buf.writeByte(difficulty);
    }

    buf.writeByte(skinParts);

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_9) >= 0) {
      ProtocolUtils.writeVarInt(buf, mainHand);
    }

    if (version.compareTo(ProtocolVersion.MINECRAFT_1_16_COMBAT_6) >= 0) {
      buf.writeBoolean(useShieldOnCrouch);
    }
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}
