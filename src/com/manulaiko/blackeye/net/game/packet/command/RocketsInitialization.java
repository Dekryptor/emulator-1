package com.manulaiko.blackeye.net.game.packet.command;

/**
 * BatteriesInitialization command.
 *
 * Builds the batteries initialization command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class RocketsInitialization extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int r310, plt2026, plt2021, plt3031, pld8, dcr, wiz, acm, smb, ish, pem, mpem, ddm, sabm;
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
        this.add("3");
        this.add(this.r310);
        this.add(this.plt2026);
        this.add(this.plt2021);
        this.add(this.plt3031);
        this.add(this.pld8);
        this.add(this.dcr);
        this.add(this.wiz);
        this.add(this.acm);
        this.add(this.smb);
        this.add(this.ish);
        this.add(this.pem);
        this.add(this.mpem);
        this.add(this.ddm);
        this.add(this.sabm);

        return super.toString();
    }
}
