package com.velocitypowered.proxy.protocol.packet.title;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;

public class TitleSubtitlePacket extends GenericTitlePacket {

  private String component;

  public TitleSubtitlePacket() {
    setAction(ActionType.SET_SUBTITLE);
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion version) {
    ProtocolUtils.writeString(buf, component);
  }

  @Override
  public String getComponent() {
    return component;
  }

  @Override
  public void setComponent(String component) {
    this.component = component;
  }

  @Override
  public String toString() {
    return "TitleSubtitlePacket{"
        + ", component='" + component + '\''
        + '}';
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}
