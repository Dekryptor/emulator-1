package com.manulaiko.blackeye.net.sockswork.packets.commands;

/**
 * SetNameResponse command
 *
 * Sends the response of a SetName packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SetNameResponse extends com.manulaiko.blackeye.net.sockswork.packets.Command
{
    /**
     * Result of the request
     */
    public int status = 0;

    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "SetNameResponse";
    }

    /**
     * Builds and returns the packet
     *
     * @return Packet as a string
     */
    public String toString()
    {
        this._packet.add(8);
        this._packet.add(this.status);

        return this._packet.toString();
    }
}
