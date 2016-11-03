package com.manulaiko.blackeye.net.game.packet.command;

/**
 * ShipInitialization command.
 *
 * Builds the Ship initialization command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ShipInitialization extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, shipID, shield, maxShield, health, maxHealth, cargo, maxCargo, x, y, mapID, factionID,
            clanID, batteries, rockets, oState, honor, levelID, uridium, rankID, ggRings, useSysFont, speed;

    public long experience, credits;

    public double jackpot;

    public String name, clanTag;

    public boolean isPremium;
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
        this.add("I");
        this.add(this.id);
        this.add(this.name);
        this.add(this.shipID);
        this.add(this.speed);
        this.add(this.shield);
        this.add(this.maxShield);
        this.add(this.health);
        this.add(this.maxHealth);
        this.add(this.cargo);
        this.add(this.maxCargo);
        this.add(this.x);
        this.add(this.y);
        this.add(this.mapID);
        this.add(this.factionID);
        this.add(this.clanID);
        this.add(this.batteries);
        this.add(this.rockets);
        this.add(this.oState); //No fucking idea
        this.add(this.isPremium);
        this.add(this.experience);
        this.add(this.honor);
        this.add(this.levelID);
        this.add(this.credits);
        this.add(this.uridium);
        this.add(this.jackpot);
        this.add(this.rankID);
        this.add(this.clanTag);
        this.add(this.ggRings);
        this.add(this.useSysFont); //No idea

        return super.toString();
    }
}
