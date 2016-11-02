package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;

/**
 * Pin handler.
 *
 * Handles Account's ping.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Ping extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handlers the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        if(!connection.lastReceivedPacket.equals("PNG")) {
            connection.pings = 0;
        }

        connection.pings += 1;
        if(connection.pings >= Connection.MAX_PING_AMOUNT) {
            //connection.finish();
        }
    }
}
