package com.manulaiko.blackeye.net.game.packet.command;

/**
 * StandardMessage command.
 *
 * Builds the Standard message command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class StandardMessage extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public String message;
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
        this.add("A");
        this.add("STD");
        this.add(this.message);

        return super.toString();
    }
}
