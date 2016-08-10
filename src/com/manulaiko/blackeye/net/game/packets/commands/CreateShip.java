package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * CreateShip command
 * 
 * Builds the Create ship command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CreateShip extends com.manulaiko.blackeye.net.game.packets.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, shipID,  x, y, factionID, clanID, expansion, rankID, ggRings, clanDiplomacy;

    public boolean warningIcon, isCloaked, isNPC;

    public String name, clanTag;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("C");
        this._packet.add(this.id);
        this._packet.add(this.shipID);
        this._packet.add(this.expansion);
        this._packet.add(this.clanTag);
        this._packet.add(this.name);
        this._packet.add(this.x);
        this._packet.add(this.y);
        this._packet.add(this.factionID);
        this._packet.add(this.clanID);
        this._packet.add(this.rankID);
        this._packet.add(this.warningIcon);
        this._packet.add(this.clanDiplomacy);
        this._packet.add(this.ggRings);
        this._packet.add(this.isNPC);
        this._packet.add(this.isCloaked);

        return this._packet.toString();
    }
}
