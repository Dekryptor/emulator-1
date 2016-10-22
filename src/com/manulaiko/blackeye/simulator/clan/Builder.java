package com.manulaiko.blackeye.simulator.clan;

import java.sql.ResultSet;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;

/**
 * Clan builder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder extends com.manulaiko.blackeye.simulator.Builder
{
    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        super(rs);
    }

    /**
     * Builds a clan.
     */
    public void build()
    {
        try {
            this._object = new Clan(
                    this._result.getInt("id"),
                    this._result.getString("tag"),
                    this._result.getString("name"),
                    this._result.getInt("factions_id")
            );
        } catch(Exception e) {
            Console.println("Couldn't build clan!");
            Console.println(e.getMessage());
        }
    }
}
