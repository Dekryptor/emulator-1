package com.manulaiko.blackeye.simulator.map;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Map factory
 *
 * Used for instance map objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced maps
     */
    private HashMap<Integer, Map> _maps = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given map
     *
     * @param id Map id
     *
     * @return The map
     *
     * @throws NotFound If map doesn't exist
     */
    public Map getByID(int id) throws NotFound
    {
        if(!this._maps.containsKey(id)) {
            Map m = this.loadByID(id);

            this._maps.put(id, m);

            return m;
        }

        return this._maps.get(id);
    }

    /**
     * Returns given map
     *
     * @param name Map name
     *
     * @return The map
     *
     * @throws NotFound If map doesn't exist
     */
    public Map getByName(String name) throws NotFound
    {
        for(Entry<Integer, Map> m : this._maps.entrySet()) {
            if(m.getValue().name.equals(name)) {
                return m.getValue();
            }
        }

        Map m = this.loadByName(name);

        this._maps.put(m.id, m);

        return m;
    }

    /**
     * Returns all maps
     *
     * @return All maps
     */
    public HashMap<Integer, Map> getAllMaps()
    {
        return this._maps;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._maps.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a map by its ID
     *
     * @param id Map id
     *
     * @return The map
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Map loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `maps` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getMap();
            } else {
                throw new NotFound("map", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("map", "id: "+ id);
        }
    }

    /**
     * Builds and returns a map by its name
     *
     * @param name Map name
     *
     * @return The map
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Map loadByName(String name) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `maps` WHERE `name`=?");
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getMap();
            } else {
                throw new NotFound("map", name);
            }
        } catch(SQLException e) {
            throw new NotFound("map", name);
        }
    }

    /**
     * Loads all maps from database
     */
    public void loadAll()
    {
        try {
            ResultSet result = Main.mysqlManager.query("SELECT * FROM `maps`");

            java.awt.Point p = new java.awt.Point(100, 100);

            while(result.next()) {
                Builder builder = new Builder(result);
                
                Map m = builder.getMap();

                this._maps.put(m.id, m);
            }
        } catch(Exception e) {
            Console.println("Couldn't load map!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
