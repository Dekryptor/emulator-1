package com.manulaiko.blackeye.net.game.packet.command;

/**
 * CreateCollectable command.
 *
 * Builds the Create collectable command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CreateCollectable extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, x, y, gfx;
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
        this._packet.add("c");
        this._packet.add(this.id);
        this._packet.add(this.gfx);
        this._packet.add(this.x);
        this._packet.add(this.y);

        return this._packet.toString();
    }
}
