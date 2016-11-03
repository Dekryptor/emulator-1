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
        this.add("c");
        this.add(this.id);
        this.add(this.gfx);
        this.add(this.x);
        this.add(this.y);

        return super.toString();
    }
}
