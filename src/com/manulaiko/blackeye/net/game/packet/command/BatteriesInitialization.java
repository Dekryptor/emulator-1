package com.manulaiko.blackeye.net.game.packet.command;

/**
 * BatteriesInitialization command.
 *
 * Builds the batteries initialization command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class BatteriesInitialization extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int lcb10, mcb25, mcb50, ucb100, sab50, rsb75;
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
        this.add("B");
        this.add(this.lcb10);
        this.add(this.mcb25);
        this.add(this.mcb50);
        this.add(this.ucb100);
        this.add(this.sab50);
        this.add(this.rsb75);

        return super.toString();
    }
}
