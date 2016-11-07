package com.manulaiko.blackeye.net.game.packet.command;

/**
 * Move command.
 *
 * Builds the Move ship command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Move extends com.manulaiko.blackeye.net.utils.Command
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
        this.add("1");
        this.add(this.id);
        this.add(this.newX);
        this.add(this.newY);
        this.add(this.time);

        return super.toString();
    }
}
