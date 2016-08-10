package com.manulaiko.blackeye.simulator.clan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Clan factory
 *
 * Used for instance clan objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced maps
     */
    private HashMap<Integer, Clan> _clans = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given clan
     *
     * @param id Clan id
     *
     * @return The clan
     *
     * @throws NotFound If clan doesn't exist
     */
    public Clan getByID(int id) throws NotFound
    {
        if(!this._clans.containsKey(id)) {
            Clan c = this.loadByID(id);

            this._clans.put(id, c);

            return c;
        }

        return this._clans.get(id);
    }

    /**
     * Returns given clan
     *
     * @param name Clan name
     *
     * @return The clan
     *
     * @throws NotFound If clan doesn't exist
     */
    public Clan getByName(String name) throws NotFound
    {
        for(Entry<Integer, Clan> c : this._clans.entrySet()) {
            if(c.getValue().name.equals(name)) {
                return c.getValue();
            }
        }

        Clan c = this.loadByName(name);

        this._clans.put(c.id, c);

        return c;
    }

    /**
     * Returns given clan
     *
     * @param tag Clan tag
     *
     * @return The clan
     *
     * @throws NotFound If clan doesn't exist
     */
    public Clan getByTag(String tag) throws NotFound
    {
        for(Entry<Integer, Clan> c : this._clans.entrySet()) {
            if(c.getValue().tag.equals(tag)) {
                return c.getValue();
            }
        }

        Clan c = this.loadByTag(tag);

        this._clans.put(c.id, c);

        return c;
    }

    /**
     * Returns all items
     *
     * @return All items
     */
    public HashMap<Integer, Clan> getAllClans()
    {
        return this._clans;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._clans.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a clan by its ID
     *
     * @param id Clan id
     *
     * @return The clan
     *
     * @throws NotFound If clan doesn't exist in database
     */
    public Clan loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `clans` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getClan();
            } else {
                throw new NotFound("clan", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("clan", "id: "+ id);
        }
    }

    /**
     * Builds and returns an clan by its name
     *
     * @param name Clan name
     *
     * @return The Clan
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Clan loadByName(String name) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `clans` WHERE `name`=?");
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getClan();
            } else {
                throw new NotFound("clan", name);
            }
        } catch(SQLException e) {
            throw new NotFound("clan", name);
        }
    }

    /**
     * Builds and returns an clan by its tag
     *
     * @param tag Clan tag
     *
     * @return The Clan
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Clan loadByTag(String tag) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `clans` WHERE `tag`=?");
            ps.setString(1, tag);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getClan();
            } else {
                throw new NotFound("clan", tag);
            }
        } catch(SQLException e) {
            throw new NotFound("clan", tag);
        }
    }

    /**
     * Loads all items from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `clans`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Clan c = builder.getClan();

                this._clans.put(c.id, c);
            }
        } catch(Exception e) {
            Console.println("Couldn't load clan!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
