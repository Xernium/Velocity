package com.velocitypowered.proxy.protocol.packet.title;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;

public class TitleClearPacket extends GenericTitlePacket {

  private boolean resetTimes;

  public TitleClearPacket() {
    setAction(ActionType.HIDE);
  }

  @Override
  public void setAction(ActionType action) {
    if (action != ActionType.HIDE && action != ActionType.RESET) {
      throw new IllegalArgumentException("TitleClearPacket only accepts CLEAR and RESET actions");
    }
    super.setAction(action);
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion version) {
    buf.writeBoolean(getAction() == ActionType.RESET);
  }

  @Override
  public String toString() {
    return "TitleClearPacket{"
        + ", reset=" + resetTimes
        + '}';
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}
