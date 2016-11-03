package com.manulaiko.blackeye.net.utils;

/**
 * Command class.
 *
 * All the commands extends this class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Command extends PacketParser
{
    /**
     * Constructor.
     */
    public Command()
    {
        this.add(0);
    }

    /**
     * Sets packet parser.
     *
     * @param p Packet parser.
     */
    public void setPacketParser(PacketParser p)
    {
        this._packet = p._packet;
        this._i      = p._i;
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
}
