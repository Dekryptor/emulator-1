package com.manulaiko.blackeye.net.sockswork.packets.commands;

/**
 * setFactionIDResponse command
 *
 * Sends the response of a SetFactionID packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SetFactionIDResponse extends com.manulaiko.blackeye.net.sockswork.packets.Command
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
        return "SetFactionIDResponse";
    }

    /**
     * Builds and returns the packet
     *
     * @return Packet as a string
     */
    public String toString()
    {
        this._packet.add(6);
        this._packet.add(this.status);

        return this._packet.toString();
    }
}
