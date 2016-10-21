package com.manulaiko.blackeye.simulator.item;

import java.sql.ResultSet;

import com.manulaiko.tabitha.Console;
import org.json.JSONObject;

/**
 * Item builder.
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
     * Builds and returns an item.
     */
    public void build()
    {
        try {
            JSONObject extras = new JSONObject(this._result.getString("extras"));

            this._object = new Item(
                    this._result.getInt("id"),
                    this._result.getString("loot_id"),
                    this._result.getString("name"),
                    this._result.getString("category"),
                    this._result.getInt("price"),
                    this._result.getBoolean("is_elite"),
                    this._result.getInt("value"),
                    extras
            );
        } catch(Exception e) {
            Console.println("Couldn't build item!");
            Console.println(e.getMessage());
        }
    }
}
