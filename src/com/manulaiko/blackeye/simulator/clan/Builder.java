package com.manulaiko.blackeye.simulator.clan;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;


import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
/**
 * Clan builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder
{
    /**
     * Clan object
     *
     * The current clan we're building
     */
    private Clan _clan;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._clan = new Clan(
                    rs.getInt("id"),
                    rs.getString("tag"),
                    rs.getString("name"),
                    rs.getInt("factions_id")
            );
        } catch(Exception e) {
            Console.println("Couldn't build clan!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a clan
     *
     * @para clan Clan to clone
     */
    public Builder(Clan clan)
    {
        try {
            this._clan = new Clan(
                    clan.id,
                    clan.tag,
                    clan.name,
                    clan.factionsID
            );
        } catch(Exception e) {
            Console.println("Couldn't clone clan!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the clan
     *
     * @return The clan
     */
    public Clan getClan()
    {
        return this._clan;
    }
}
