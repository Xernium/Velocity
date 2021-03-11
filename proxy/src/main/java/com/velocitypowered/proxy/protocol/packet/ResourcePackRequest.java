package com.velocitypowered.proxy.protocol.packet;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.ProtocolUtils.Direction;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ResourcePackRequest implements MinecraftPacket {

  private @MonotonicNonNull String url;
  private @MonotonicNonNull String hash;
  private boolean isRequired; // 1.17+

  public @Nullable String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isRequired() {
    return isRequired;
  }

  public @Nullable String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public void setRequired(boolean required) {
    isRequired = required;
  }

  @Override
  public void decode(ByteBuf buf, Direction direction, ProtocolVersion protocolVersion) {
    this.url = ProtocolUtils.readString(buf);
    this.hash = ProtocolUtils.readString(buf);
    if (protocolVersion.compareTo(ProtocolVersion.MINECRAFT_1_17) >= 0) {
      this.isRequired = buf.readBoolean();
    }
  }

  @Override
  public void encode(ByteBuf buf, Direction direction, ProtocolVersion protocolVersion) {
    if (url == null || hash == null) {
      throw new IllegalStateException("Packet not fully filled in yet!");
    }
    ProtocolUtils.writeString(buf, url);
    ProtocolUtils.writeString(buf, hash);
    if (protocolVersion.compareTo(ProtocolVersion.MINECRAFT_1_17) >= 0) {
      buf.writeBoolean(isRequired);
    }
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }

  @Override
  public String toString() {
    return "ResourcePackRequest{"
        + "url='" + url + '\''
        + ", hash='" + hash + '\''
        + '}';
  }
}
