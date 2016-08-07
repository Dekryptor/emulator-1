package com.manulaiko.blackeye.net.game.packets.commands;

/**
 * ShipInitialization command
 * 
 * Builds the Ship initialization command
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *     
 * @package com.manulaiko.blackeye.net.game.packets.commands
 */
public class ShipInitialization extends com.manulaiko.blackeye.net.game.packets.Command
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
     * Returns the packet as a string
     */
    public String toString()
    {
        this._packet.add("I");
        this._packet.add(this.id);
        this._packet.add(this.name);
        this._packet.add(this.shipID);
        this._packet.add(this.speed);
        this._packet.add(this.shield);
        this._packet.add(this.maxShield);
        this._packet.add(this.health);
        this._packet.add(this.maxHealth);
        this._packet.add(this.cargo);
        this._packet.add(this.maxCargo);
        this._packet.add(this.x);
        this._packet.add(this.y);
        this._packet.add(this.mapID);
        this._packet.add(this.factionID);
        this._packet.add(this.clanID);
        this._packet.add(this.batteries);
        this._packet.add(this.rockets);
        this._packet.add(this.oState); //No fucking idea
        this._packet.add(this.isPremium);
        this._packet.add(this.experience);
        this._packet.add(this.honor);
        this._packet.add(this.levelID);
        this._packet.add(this.credits);
        this._packet.add(this.uridium);
        this._packet.add(this.jackpot);
        this._packet.add(this.rankID);
        this._packet.add(this.clanTag);
        this._packet.add(this.ggRings);
        this._packet.add(this.useSysFont); //No idea

        return this._packet.toString();
    }
}
