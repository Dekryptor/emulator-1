package com.manulaiko.blackeye.net.game.packet.command;

/**
 * LaserAttack command.
 *
 * Builds the Laser attack command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LaserAttack extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int attacker, target, gfx;

    public boolean laserSkill, shieldSkill;

    public String name, clanTag;
    ////////////////
    // End params //
    ////////////////

    /**
     * Returns the packet as a string.
     *
     * @return Packet as a string.
     */
    public String toString()
    {
        this.add("a");
        this.add(this.attacker);
        this.add(this.target);
        this.add(this.gfx);
        this.add(this.shieldSkill);
        this.add(this.laserSkill);

        return super.toString();
    }
}
