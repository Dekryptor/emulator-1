package com.manulaiko.blackeye.net.game.packet.command;

/**
 * RemoveShip command.
 *
 * Builds the Remove ship command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class RemoveShip extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string.
     *
     * @return Packet as string.
     */
    public String toString()
    {
        this.add("R");
        this.add(this.id);

        return super.toString();
    }
}
