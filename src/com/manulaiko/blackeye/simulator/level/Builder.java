package com.manulaiko.blackeye.simulator.level;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
/**
 * Level builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder
{
    /**
     * Level object
     *
     * The current level we're building
     */
    private Level _level;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._level = new Level(
                    rs.getInt("id"),
                    rs.getLong("accounts"),
                    rs.getInt("drones"),
                    rs.getInt("pets")
            );
        } catch(Exception e) {
            Console.println("Couldn't build level!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a level
     *
     * @para level Level to clone
     */
    public Builder(Level level)
    {
        try {
            this._level = new Level(
                    level.id,
                    level.accounts,
                    level.drones,
                    level.pets
            );
        } catch(Exception e) {
            Console.println("Couldn't clone level!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the level
     *
     * @return The level
     */
    public Level getLevel()
    {
        return this._level;
    }
}
