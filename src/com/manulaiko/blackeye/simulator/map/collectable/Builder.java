package com.manulaiko.blackeye.simulator.map.collectable;

import java.sql.ResultSet;

import org.json.JSONArray;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Collectable builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder
{
    /**
     * Collectable object
     *
     * The current collectable we're building
     */
    private Collectable _collectable;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            JSONArray rewards = new JSONArray(rs.getString("rewards"));
            
            this._collectable = new Collectable(
                    rs.getInt("id"),
                    rs.getInt("gfx"),
                    rs.getInt("class"),
                    rs.getString("name"),
                    rewards,
                    new Point(0, 0)
            );
            
            this._build(rewards);
        } catch(Exception e) {
            Console.println("Couldn't build collectable!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a collectable
     *
     * @para collectable Collectable to clone
     */
    public Builder(Collectable collectable)
    {
        try {
            this._collectable = new Collectable(
                    collectable.id,
                    collectable.gfx,
                    collectable.classID,
                    collectable.name,
                    collectable.rewardsJSON,
                    new Point(0, 0)
            );

            this._build(collectable.rewardsJSON);
        } catch(Exception e) {
            Console.println("Couldn't clone collectable!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the collectable
     *
     * Adds the different rewards
     *
     * @param rewards Rewards JSON
     */
    private void _build(JSONArray rewards)
    {
        try {
            for(int i = 0; i < rewards.length(); i++) {
                this._collectable.addReward(rewards.getJSONObject(i));
            }
        } catch(Exception e) {
            Console.println("Couldn't build collectable!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the collectable
     *
     * @return The collectable
     */
    public Collectable getCollectable()
    {
        return this._collectable;
    }
}
