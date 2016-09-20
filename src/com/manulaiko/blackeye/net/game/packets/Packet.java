package com.manulaiko.blackeye.net.game.packets;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.utils.PacketParser;

/**
 * Packet class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Packet implements Runnable
{
    /**
     * Packet parser object
     */
    protected PacketParser _packet;

    /**
     * Connection that received the packet
     */
    protected Connection _connection;

    /**
     * Sets the packet parser
     *
     * @param packet Packet parser
     */
    public void setPacket(PacketParser packet)
    {
        this._packet = packet;
    }

    /**eric ||
                    ar
     * Sets the connection object
     *
     * @param connection Connection object
     */
    public void setConnection(Connection connection)
    {
        this._connection = connection;
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
    public String getName()
    {
        return this.getClass().getSimpleName();
    }

    /**
     * Runs the packet
     */
    public void run()
    {
        this.handle(this._connection);
    }
}
