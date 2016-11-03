package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;

/**
 * Echo packet.
 *
 * Echoes received packet.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class EchoPacket extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection object.
     */
    public void handle(Connection connection)
    {
        connection.send(this._packet);
    }
}
