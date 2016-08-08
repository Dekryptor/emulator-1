package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Configuration factory
 *
 * Used for instance hangar objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.configuration
 */
public class Factory
{
    /**
     * Instanced hangars
     */
    private HashMap<Integer, Configuration> _configurations = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given configuration
     *
     * @param id Configuration id
     *
     * @return The configuration
     *
     * @throws NotFound If configuration doesn't exist
     */
    public Configuration getByID(int id) throws NotFound
    {
        if(!this._configurations.containsKey(id)) {
            Configuration c = this.loadByID(id);

            this._configurations.put(id, c);

            return c;
        }

        return this._configurations.get(id);
    }

    /**
     * Returns the configurations associated with given `accounts_equipment_ships_id`
     *
     * @param id Ship's id
     *
     * @return Ship's configurations
     */
    public HashMap<Integer, Configuration> getByShipID(int id)
    {
        if(this._configurations.size() == 0) {
            this.loadAll();
        }

        HashMap<Integer, Configuration> configs = new HashMap<>();

        this._configurations.forEach((key, value) -> {
            if(value.shipID == id) {
                configs.put(key, value);
            }
        });

        return configs;
    }

    /**
     * Returns all hangars
     *
     * @return All hangars
     */
    public HashMap<Integer, Configuration> getAllConfigurations()
    {
        return this._configurations;
    }

    /**
     * Returns the amount of loaded configurations
     *
     * @return Amount of loaded configurations
     */
    public int getAmount()
    {
        return this._configurations.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a configuration by its ID
     *
     * @param id Configuration id
     *
     * @return The configuration
     *
     * @throws NotFound If configuration doesn't exist in database
     */
    public Configuration loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts_equipment_configurations` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getConfiguration();
            } else {
                throw new NotFound("configuration", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("configuration", "id: "+ id);
        }
    }

    /**
     * Loads all hangars from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `accounts_equipment_configurations`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Configuration c = builder.getConfiguration();

                this._configurations.put(c.id, c);
            }
        } catch(Exception e) {
            Console.println("Couldn't load configurations!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
