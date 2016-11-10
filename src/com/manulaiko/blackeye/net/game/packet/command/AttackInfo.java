package com.manulaiko.blackeye.net.game.packet.command;

/**
 * AttackInfo command.
 *
 * Builds the Attack info command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class AttackInfo extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int attacker, target, damage, shield, health;

    public boolean isHeal;
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
        this.add("Y");
        this.add(this.attacker);
        this.add(this.target);
        this.add((this.isHeal) ? "H" : "L");
        this.add(this.health);
        this.add(this.shield);
        this.add(this.damage);

        return super.toString();
    }
}
