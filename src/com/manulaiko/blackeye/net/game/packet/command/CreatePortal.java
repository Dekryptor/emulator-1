package com.manulaiko.blackeye.net.game.packet.command;

/**
 * CreatePortal command.
 *
 * Builds the Create portal command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CreatePortal extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, x, y, gfx, factionScrap;

    public boolean isVisible;

    public String assets = "";
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
        this.add("p");
        this.add(this.id);
        this.add(this.factionScrap);
        this.add(this.gfx);
        this.add(this.x);
        this.add(this.y);
        this.add(this.isVisible);
        this.add(this.assets);

        return super.toString();
    }
}
