package com.manulaiko.blackeye.net.sockswork.packets.commands;

/**
 * SendData command
 *
 * Sends the response of a GetData packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.sockswork.packets.handlers
 */
public class SendData extends com.manulaiko.blackeye.net.sockswork.packets.Command
{
    /**
     * Experience points
     */
    public long experience = 0;

    /**
     * Honor points
     */
    public int honor = 0;

    /**
     * Credits
     */
    public long credits = 0;

    /**
     * Uridium
     */
    public int uridium = 0;

    /**
     * Level
     */
    public int level = 0;

    /**
     * Jackpot
     */
    public double jackpot = 0;

    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "SendData";
    }

    /**
     * Builds and returns the packet
     *
     * @return Packet as a string
     */
    public String toString()
    {
        this._packet.add(10);
        this._packet.add(this.experience);
        this._packet.add(this.honor);
        this._packet.add(this.credits);
        this._packet.add(this.uridium);
        this._packet.add(this.level);
        this._packet.add(this.jackpot);

        return this._packet.toString();
    }
}
