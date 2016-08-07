package com.manulaiko.blackeye.simulator.clan;

/**
 * Clan class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.clan
 */
public class Clan
{
    /**
     * Clan ID
     */
    public int id;

    /**
     * Clan tag
     */
    public String tag;

    /**
     * Clan name
     */
    public String name;

    /**
     * Clan faction
     */
    public int factionsID;

    /**
     * Constructor
     *
     * @param id         Clan ID
     * @param tag        Clan tag
     * @param name       Clan name
     * @param factionsID Faction ID
     */
    public Clan(int id, String tag, String name, int factionsID)
    {
        this.id         = id;
        this.tag        = tag;
        this.name       = name;
        this.factionsID = factionsID;
    }
}
