package com.manulaiko.blackeye.net.game.packets.handlers;

/**
 * Echo packet
 *
 * Echoes received packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class EchoPacket extends com.manulaiko.blackeye.net.game.packets.Packet
{
    /**
     * Handles the packet
     *
     * @param connection Connection object
     */
    public void handle(com.manulaiko.blackeye.net.game.Connection connection)
    {
        connection.send(this._packet);
    }
}
