package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * InvalidSession command
 * 
 * Builds the InvalidSession
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class InvalidSession extends com.manulaiko.blackeye.net.game.packets.Command
{
    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("ERR");
        this._packet.add(4);

        return this._packet.toString();
    }
}
