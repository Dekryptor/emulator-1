package com.manulaiko.blackeye.net.game.packet.command;

/**
 * LogMessage command.
 *
 * Builds the Log message command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LogMessage extends com.manulaiko.blackeye.net.utils.Command
{
    /////////////////////
    // Start constants //
    /////////////////////
    public static final String EXPERIENCE = "EP";
    public static final String HONOR      = "HON";
    public static final String CREDITS    = "CRE";
    public static final String URIDIUM    = "URI";
    public static final String JACKPOT    = "JPE";
    public static final String ORE        = "CAR";
    ///////////////////
    // End constants //
    ///////////////////

    //////////////////
    // Start params //
    //////////////////
    public long newValue;

    public int value;

    public String type;
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
        this.add("LM");
        this.add("ST");
        this.add(this.type);
        this.add(this.value);
        this.add(this.newValue);

        return super.toString();
    }
}
