/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NettyPacketEncoder
extends MessageToByteEncoder<Packet<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
    private final EnumPacketDirection direction;

    public NettyPacketEncoder(EnumPacketDirection direction) {
        this.direction = direction;
    }

    protected void encode(ChannelHandlerContext p_encode_1_, Packet<?> p_encode_2_, ByteBuf p_encode_3_) throws IOException, Exception {
        EnumConnectionState enumconnectionstate = (EnumConnectionState)((Object)p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get());
        if (enumconnectionstate == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + p_encode_2_.toString());
        }
        Integer integer = enumconnectionstate.getPacketId(this.direction, p_encode_2_);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), integer, p_encode_2_.getClass().getName());
        }
        if (integer == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
        packetbuffer.writeVarIntToBuffer(integer);
        try {
            p_encode_2_.writePacketData(packetbuffer);
        }
        catch (Throwable throwable) {
            LOGGER.error(throwable);
        }
    }
}

