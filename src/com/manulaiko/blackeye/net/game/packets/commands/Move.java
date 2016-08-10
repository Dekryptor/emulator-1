package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * Move command
 *
 * Builds the move packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Move extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, newX, newY;

    public double time;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("1");
        this._packet.add(this.id);
        this._packet.add(this.newX);
        this._packet.add(this.newY);
        this._packet.add(this.time);

        return this._packet.toString();
    }
}
