package com.manulaiko.blackeye.net.game.packet.command;

/**
 * RemoveCollectable command.
 *
 * Builds the RemoveCollectable command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class RemoveCollectable extends com.manulaiko.blackeye.net.utils.Command
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
     * @return Packet as a string.
     */
    public String toString()
    {
        this.add("2");
        this.add(this.id);

        return super.toString();
    }
}
