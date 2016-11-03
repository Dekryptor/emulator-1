package com.manulaiko.blackeye.net.game.packet.command;

/**
 * CreateShip command
 *
 * Builds the Create ship command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CreateShip extends com.manulaiko.blackeye.net.utils.Command
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
        this.add("C");
        this.add(this.id);
        this.add(this.shipID);
        this.add(this.expansion);
        this.add(this.clanTag);
        this.add(this.name);
        this.add(this.x);
        this.add(this.y);
        this.add(this.factionID);
        this.add(this.clanID);
        this.add(this.rankID);
        this.add(this.warningIcon);
        this.add(this.clanDiplomacy);
        this.add(this.ggRings);
        this.add(this.isNPC);
        this.add(this.isCloaked);

        return super.toString();
    }
}
