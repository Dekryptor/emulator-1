package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * CreateCollectable command
 * 
 * Builds the Create collectable command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *     
 * @package com.manulaiko.blackeye.net.game.packets.commands
 */
public class CreateCollectable extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, x, y, gfx;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
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
