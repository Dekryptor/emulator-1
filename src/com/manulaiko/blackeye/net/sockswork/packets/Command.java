package com.manulaiko.blackeye.net.sockswork.packets;

import com.manulaiko.blackeye.net.game.utils.PacketParser;

/**
 * Command class
 *
 * All the commands extends this class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Command
{
    /**
     * Packet parser
     */
    protected PacketParser _packet;

    /**
     * Constructor
     */
    public Command()
    {
        this._packet = new PacketParser();
        this._packet.add(0);
    }

    /**
     * Returns packet name
     */
    public abstract String getName();

    /**
     * Returns packet as a string
     */
    public abstract String toString();
}
