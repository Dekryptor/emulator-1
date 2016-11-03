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
     *
     * @var ID.
     */
    public int id;

    /**
     * Clan tag.
     *
     * @var Tag.
     */
    public String tag;

    /**
     * Clan name.
     *
     * @var Name.
     */
    public String name;

    /**
     * Clan faction.
     *
     * @var Faction ID.
     */
    public int factionID;

    /**
     * Constructor.
     *
     * @param id        Clan ID.
     * @param tag       Clan tag.
     * @param name      Clan name.
     * @param factionID Faction ID.
     */
    public Clan(int id, String tag, String name, int factionID)
    {
        this.id        = id;
        this.tag       = tag;
        this.name      = name;
        this.factionID = factionID;
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
