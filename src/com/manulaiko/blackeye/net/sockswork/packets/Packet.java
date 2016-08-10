package com.manulaiko.blackeye.net.sockswork.packets;

import com.manulaiko.blackeye.net.game.utils.PacketParser;
import com.manulaiko.blackeye.net.sockswork.Connection;

/**
 * Packet class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Packet
{
    /**
     * Packet parser object
     */
    protected PacketParser _packet;

    /**
     * Sets the packet parser
     *
     * @param packet Packet parser
     */
    public void setPacket(PacketParser packet)
    {
        this._packet = packet;
    }

    /**
     * Handles the packet
     *
     * @param connection Connection object that received the packet
     */
    public abstract void handle(Connection connection);

    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public abstract String getName();
}
