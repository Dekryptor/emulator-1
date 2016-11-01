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
        this._packet.add("p");
        this._packet.add(this.id);
        this._packet.add(this.factionScrap);
        this._packet.add(this.gfx);
        this._packet.add(this.x);
        this._packet.add(this.y);
        this._packet.add(this.isVisible);
        this._packet.add(this.assets);

        return this._packet.toString();
    }
}
