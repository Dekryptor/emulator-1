package com.manulaiko.blackeye.net.game.packet.command;

/**
 * ActivatePortal command.
 *
 * Builds the Activate portal command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ActivatePortal extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int mapID, portalID;
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
        this.add("U");
        this.add(this.mapID);
        this.add(this.portalID);

        return super.toString();
    }
}
