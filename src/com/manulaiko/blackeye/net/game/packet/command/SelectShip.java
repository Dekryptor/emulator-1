package com.manulaiko.blackeye.net.game.packet.command;

/**
 * Select ship command.
 *
 * Builds the Select ship command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SelectShip extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int id, shipID, health, maxHealth, shield, maxShield;

    public boolean shieldAbility;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string
     */
    public String toString()
    {
        this.add("N");
        this.add(this.id);
        this.add(this.shipID);
        this.add(this.health);
        this.add(this.maxHealth);
        this.add(this.shield);
        this.add(this.maxShield);
        this.add(this.shieldAbility);

        return super.toString();
    }
}
