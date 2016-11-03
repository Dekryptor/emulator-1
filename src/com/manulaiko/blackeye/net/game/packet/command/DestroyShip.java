package com.manulaiko.blackeye.net.game.packet.command;

/**
 * DestroyShip command.
 *
 * Builds the Destroy ship command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class DestroyShip extends com.manulaiko.blackeye.net.utils.Command
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
        this.add("K");
        this.add(this.id);

        return super.toString();
    }
}
