package com.manulaiko.blackeye.net.game.packet.command;

/**
 * ChangeConfiguration command.
 *
 * Builds the change configuration command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ChangeConfiguration extends com.manulaiko.blackeye.net.utils.Command
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
        this.add("A");
        this.add("CC");
        this.add(this.id);

        return super.toString();
    }
}
