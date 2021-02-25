package com.velocitypowered.proxy.protocol.packet.title;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class GenericTitlePacket implements MinecraftPacket {

  public enum ActionType {
    SET_TITLE(0),
    SET_SUBTITLE(1),
    SET_ACTION_BAR(2),
    SET_TIMES(3),
    HIDE(4),
    RESET(5);

    private final int action;

    ActionType(int action) {
      this.action = action;
    }

    public int getAction(ProtocolVersion version) {
      return version.compareTo(ProtocolVersion.MINECRAFT_1_11) < 0
              ? action > 2 ? action - 1 : action : action;
    }
  }


  private ActionType action;

  protected void setAction(ActionType action) {
    this.action = action;
  }

  public final ActionType getAction() {
    return action;
  }

  public String getComponent() {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public void setComponent(String component) {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public int getFadeIn() {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public void setFadeIn(int fadeIn) {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public int getStay() {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public void setStay(int stay) {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public int getFadeOut() {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }

  public void setFadeOut(int fadeOut) {
    throw new UnsupportedOperationException("Invalid function for this TitlePacket ActionType");
  }



  @Override
  public final void decode(ByteBuf buf, ProtocolUtils.Direction direction,
                           ProtocolVersion version) {
    throw new UnsupportedOperationException(); // encode only
  }

  /**
   * Creates a version and type dependent TitlePacket.
   *
   * @param type       Action the packet should invoke
   * @param version    Protocol version of the target player
   * @return GenericTitlePacket instance that follows the invoker type/version
   */
  public static GenericTitlePacket constructTitlePacket(ActionType type, ProtocolVersion version) {
    GenericTitlePacket packet = null;
    if (version.compareTo(ProtocolVersion.MINECRAFT_1_17) >= 0) {
      switch (type) {
        case SET_ACTION_BAR:
          packet = new TitleActionbarPacket();
          break;
        case SET_SUBTITLE:
          packet = new TitleSubtitlePacket();
          break;
        case SET_TIMES:
          packet = new TitleTimesPacket();
          break;
        case SET_TITLE:
          packet = new TitleTextPacket();
          break;
        case HIDE:
        case RESET:
          packet = new TitleClearPacket();
          break;
        default:
          throw new IllegalArgumentException("Invalid ActionType");
      }
    } else {
      packet = new LegacyTitlePacket();
    }
    packet.setAction(type);
    return packet;
  }

}
