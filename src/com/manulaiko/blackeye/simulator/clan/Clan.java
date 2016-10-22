package com.manulaiko.blackeye.simulator.clan;

import com.manulaiko.tabitha.Console;

/**
 * Clan class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Clan implements Cloneable
{
    /**
     * Clan ID.
     */
    public int id;

    /**
     * Clan tag.
     */
    public String tag;

    /**
     * Clan name.
     */
    public String name;

    /**
     * Clan faction.
     */
    public int factionsID;

    /**
     * Constructor.
     *
     * @param id         Clan ID.
     * @param tag        Clan tag.
     * @param name       Clan name.
     * @param factionsID Faction ID.
     */
    public Clan(int id, String tag, String name, int factionsID)
    {
        this.id         = id;
        this.tag        = tag;
        this.name       = name;
        this.factionsID = factionsID;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Clan clone()
    {
        try {
            Clan c = (Clan)super.clone();

            return c;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone clan!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
