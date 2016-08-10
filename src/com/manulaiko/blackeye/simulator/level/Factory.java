package com.manulaiko.blackeye.simulator.level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Level factory
 *
 * Used for instance level objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced maps
     */
    private HashMap<Integer, Level> _levels = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given level
     *
     * @param id Level id
     *
     * @return The level
     *
     * @throws NotFound If level doesn't exist
     */
    public Level getByID(int id) throws NotFound
    {
        if(!this._levels.containsKey(id)) {
            Level l = this.loadByID(id);

            this._levels.put(id, l);

            return l;
        }

        return this._levels.get(id);
    }

    /**
     * Returns all items
     *
     * @return All items
     */
    public HashMap<Integer, Level> getAllLevels()
    {
        return this._levels;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._levels.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a level by its ID
     *
     * @param id Level id
     *
     * @return The level
     *
     * @throws NotFound If level doesn't exist in database
     */
    public Level loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `levels` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getLevel();
            } else {
                throw new NotFound("level", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("level", "id: "+ id);
        }
    }

    /**
     * Loads all level from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `levels`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Level l = builder.getLevel();

                this._levels.put(l.id, l);
            }
        } catch(Exception e) {
            Console.println("Couldn't load level!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
