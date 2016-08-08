package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.util.HashMap;

import org.json.JSONArray;

import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.blackeye.simulator.account.equipment.configuration.Configuration;

/**
 * Hangar class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.hangar
 */
public class Hangar
{
    /**
     * Hangar ID
     */
    public int id;

    /**
     * Account ID
     */
    public int accountID;

    /**
     * Ship object
     */
    public Ship ship;

    /**
     * Availables configuration
     */
    public HashMap<Integer, Configuration> configurations = new HashMap<>();

    /**
     * Active configuration object
     */
    public Configuration activeConfiguration;

    /**
     * Resources
     */
    public JSONArray resources;

    /**
     * Resources JSON
     */

    public String resourcesJSON;

    /**
     * Constructor
     *
     * @param id        Hangar ID
     * @param accountID Account's ID
     * @param resources Resources JSON
     */
    public Hangar(int id, int accountID, String resources)
    {
        this.id        = id;
        this.accountID = accountID;

        try {
            this.resources     = new JSONArray(resources);
            this.resourcesJSON = resources;
        } catch(Exception e) {
            //Empty
        }
    }

    /**
     * Sets ship object
     *
     * @param ship Ship object
     */
    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Changes configuration
     *
     * @param id Configuration ID
     */
    public void changeConfiguration(int id)
    {
        this.configurations.forEach((key, value) -> {
            if(value.configuration == id) {
                this.activeConfiguration = value;
                this.activeConfiguration.calculate();
            }
        });
    }

    /**
     * Adds a configuration
     *
     * @param configuration Configuration object
     */
    public void addConfiguration(Configuration configuration)
    {
        this.configurations.put((this.configurations.size() + 1), configuration);
    }

    /**
     * Sets configuration's map
     *
     * @param configurations Configuration's map
     */
    public void setConfigurations(HashMap<Integer, Configuration> configurations)
    {
        this.configurations = configurations;
    }

    /**
     * Returns speed points
     *
     * @return Speed points
     */
    public int getSpeed()
    {
        //TODO Add resource upgrades
        int baseSpeed = this.ship.ship.speed;
        baseSpeed += this.activeConfiguration.speed;

        return baseSpeed;
    }

    /**
     * Returns current shield points
     *
     * @return Current shield points
     */
    public int getShield()
    {
        return this.ship.shield;
    }

    /**
     * Returns max shield points
     *
     * @return Max shield points
     */
    public int getMaxShield()
    {
        //TODO Add resource upgrades and boosters
        int shield = this.activeConfiguration.shield;

        return shield;
    }

    /**
     * Returns current health points
     *
     * @return Current health points
     */
    public int getHealth()
    {
        return this.ship.health;
    }

    /**
     * Returns max health points
     *
     * @return Max health points
     */
    public int getMaxHealth()
    {
        //TODO Add boosters
        return this.ship.ship.health;
    }

    /**
     * Returns current cargo amount
     *
     * @return Current cargo amount
     */
    public int getCargo()
    {
        //TODO
        return 0;
    }

    /**
     * Returns max cargo amount
     *
     * @return Max cargo amount
     */
    public int getMaxCargo()
    {
        return this.ship.ship.cargo;
    }

    /**
     * Returns current laser ammo amount
     *
     * @return Current battery amount
     */
    public int getBatteriesAmount()
    {
        return this.ship.ship.batteries;
    }

    /**
     * Returns current current amount
     *
     * @return Current rockets amount
     */
    public int getRocketsAmount()
    {
        return this.ship.ship.rockets;
    }

    /**
     * Returns expansions graphic
     *
     * @return Expansions graphic
     */
    public int getExpansions()
    {
        return this.activeConfiguration.expansions;
    }
}
