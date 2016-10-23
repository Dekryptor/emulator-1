package com.manulaiko.blackeye.net.game.packets;

import com.manulaiko.blackeye.net.game.utils.PacketParser;

/**
 * Command class.
 *
 * All the commands extends this class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Command
{
    /**
     * Packet parser.
     */
    protected PacketParser _packet;

    /**
     * Constructor.
     */
    public Command()
    {
        this._packet = new PacketParser();
        this._packet.add(0);
    }

    /**
     * Sets packet parser.
     *
     * @param p Packet parser.
     */
    public void setPacketParser(PacketParser p)
    {
        this._packet = p;
        this._packet.add(0);
    }

    /**
     * Returns packet name.
     *
     * @return Packet name.
     */
    public String getName()
    {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns packet as a string.
     *
     * @return Packet as a string.
     */
    public abstract String toString();
}
