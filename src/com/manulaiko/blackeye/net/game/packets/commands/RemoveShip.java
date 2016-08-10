package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * RemoveShip command
 * 
 * Builds the Remove ship command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class RemoveShip extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("R");
        this._packet.add(this.id);

        return this._packet.toString();
    }
}
