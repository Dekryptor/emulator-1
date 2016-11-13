package com.manulaiko.blackeye.simulator.clan;

import java.util.HashMap;

import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;

/**
 * Clan class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Clan extends Simulator implements Cloneable
{
    /**
     * Instances and returns a new Simulator.
     *
     * @return New Simulator object.
     */
    public static Clan create()
    {
        Clan s          = new Clan(0, "", "", 0);
        s._isInsert     = true;
        s.databaseTable = "clans";

        return s;
    }

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

    /**
     * Returns table identifier.
     *
     * @reutrn Table identifier.
     */
    protected int _getDatabaseIdentifier()
    {
        return this.id;
    }

    /**
     * Sets database ID.
     *
     * @param id Database ID.
     */
    protected void _setDatabaseIdentifier(int id)
    {
        this.id = id;
    }

    /**
     * Returns table fields.
     *
     * @return Table fields.
     */
    protected HashMap<String, Object> _getDatabaseFields()
    {
        HashMap<String, Object> fields = new HashMap<>();

        fields.put("tag", this.tag);
        fields.put("name", this.name);
        fields.put("factions_id", this.factionID);

        return fields;
    }
}
