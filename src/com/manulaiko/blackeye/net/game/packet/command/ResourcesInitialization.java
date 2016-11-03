package com.manulaiko.blackeye.net.game.packet.command;

/**
 * ResourcesInitialization command.
 *
 * Builds the resources initialization command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ResourcesInitialization extends com.manulaiko.blackeye.net.utils.Command
{
    //////////////////
    // Start params //
    //////////////////
    public int prometium, endurium, terbium, xenomit, prometid, duranium, promerium, seprom, palladium;
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
        this.add("E");
        this.add(this.prometium);
        this.add(this.endurium);
        this.add(this.terbium);
        this.add(this.xenomit);
        this.add(this.prometid);
        this.add(this.duranium);
        this.add(this.promerium);
        this.add(this.seprom);
        this.add(this.palladium);

        return super.toString();
    }
}
