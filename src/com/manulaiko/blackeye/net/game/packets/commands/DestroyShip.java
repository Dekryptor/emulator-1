package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * DestroyShip command
 * 
 * Builds the Destroy ship command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *     
 * @package com.manulaiko.blackeye.net.game.packets.commands
 */
public class DestroyShip extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("K");
        this._packet.add(this.id);

        return this._packet.toString();
    }
}
