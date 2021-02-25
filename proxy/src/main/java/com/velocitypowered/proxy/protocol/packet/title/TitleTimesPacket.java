package com.velocitypowered.proxy.protocol.packet.title;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;

public class TitleTimesPacket extends GenericTitlePacket {

  private int fadeIn;
  private int stay;
  private int fadeOut;

  public TitleTimesPacket() {
    setAction(ActionType.SET_TIMES);
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion version) {
    buf.writeInt(fadeIn);
    buf.writeInt(stay);
    buf.writeInt(fadeOut);
  }

  @Override
  public int getFadeIn() {
    return fadeIn;
  }

  @Override
  public void setFadeIn(int fadeIn) {
    this.fadeIn = fadeIn;
  }

  @Override
  public int getStay() {
    return stay;
  }

  @Override
  public void setStay(int stay) {
    this.stay = stay;
  }

  @Override
  public int getFadeOut() {
    return fadeOut;
  }

  @Override
  public void setFadeOut(int fadeOut) {
    this.fadeOut = fadeOut;
  }

  @Override
  public String toString() {
    return "TitleTimesPacket{"
        + ", fadeIn=" + fadeIn
        + ", stay=" + stay
        + ", fadeOut=" + fadeOut
        + '}';
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}
