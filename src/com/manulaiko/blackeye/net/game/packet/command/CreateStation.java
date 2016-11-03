package com.manulaiko.blackeye.net.game.packet.command;

/**
 * CreateStation command
 *
 * Builds the Create station command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CreateStation extends com.manulaiko.blackeye.net.utils.Command
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
        this.add("s");
        this.add(this.id);      // Not used
        this.add(this.type);
        this.add(this.name);
        this.add(this.faction);
        this.add(0);            // Not used
        this.add(this.x);
        this.add(this.y);

        return super.toString();
    }
}
