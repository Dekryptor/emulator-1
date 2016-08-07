package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * CreateStation command
 * 
 * Builds the Create station command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *     
 * @package com.manulaiko.blackeye.net.game.packets.commands
 */
public class CreateStation extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, type, x, y, faction;

    public String name;

    public boolean isDMZ;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("s");
        this._packet.add(this.id);      // Not used
        this._packet.add(this.type);
        this._packet.add(this.name);
        this._packet.add(this.faction);
        this._packet.add(0);            // Not used
        this._packet.add(this.x);
        this._packet.add(this.y);

        return this._packet.toString();
    }
}
