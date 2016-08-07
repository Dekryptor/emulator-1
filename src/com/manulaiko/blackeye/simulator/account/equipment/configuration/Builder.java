package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.sql.ResultSet;

import org.json.JSONArray;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.item.Item;

import com.manulaiko.tabitha.Console;

/**
 * Configuration builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.hangar
 */
public class Builder
{
    /**
     * Configuration object
     *
     * The current configuration we're building
     */
    private Configuration _configuration;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._configuration = new Configuration(
                    rs.getInt("id"),
                    rs.getInt("accounts_id"),
                    rs.getInt("accounts_equipment_ships_id"),
                    rs.getInt("configuration"),
                    rs.getString("lasers"),
                    rs.getString("hellstorms"),
                    rs.getString("generators"),
                    rs.getString("extras")
            );

            this._build(
                    this._configuration.lasersJSON,
                    this._configuration.hellstormsJSON,
                    this._configuration.generatorsJSON,
                    this._configuration.extrasJSON
            );
        } catch(Exception e) {
            Console.println("Couldn't build configuration!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a configuration
     *
     * @param configuration Configuration to clone
     */
    public Builder(Configuration configuration)
    {
        try {
            this._configuration = new Configuration(
                    configuration.id,
                    configuration.accountID,
                    configuration.shipID,
                    configuration.configuration,
                    configuration.lasersJSON,
                    configuration.hellstormsJSON,
                    configuration.generatorsJSON,
                    configuration.extrasJSON
            );

            this._build(
                    this._configuration.lasersJSON,
                    this._configuration.hellstormsJSON,
                    this._configuration.generatorsJSON,
                    this._configuration.extrasJSON
            );
        } catch(Exception e) {
            Console.println("Couldn't clone configuration!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the configuration
     *
     * @param lasers     Configuration's lasers
     * @param hellstorms Configuration's hellstorms
     * @param generators Configuration's generators
     * @param extras     Configuration's extras
     */
    private void _build(String lasers, String hellstorms, String generators, String extras)
    {
        try {
            // Load configuration's lasers
            JSONArray lasersArray = new JSONArray(lasers);
            for(int i = 0; i < lasersArray.length(); i++) {
                Item laser = GameManager.accounts.items.getByID(lasersArray.getInt(i));

                this._configuration.addLaser(laser);
            }

            // Load configuration's hellstorms
            JSONArray hellstormsArray = new JSONArray(hellstorms);
            for(int i = 0; i < hellstormsArray.length(); i++) {
                Item laser = GameManager.accounts.items.getByID(hellstormsArray.getInt(i));

                this._configuration.addHellstorm(laser);
            }

            // Load configuration's generators
            JSONArray generatorsArray = new JSONArray(generators);
            for(int i = 0; i < generatorsArray.length(); i++) {
                Item laser = GameManager.accounts.items.getByID(generatorsArray.getInt(i));

                this._configuration.addGenerator(laser);
            }

            // Load configuration's extras
            JSONArray extrasArray = new JSONArray(extras);
            for(int i = 0; i < extrasArray.length(); i++) {
                Item laser = GameManager.accounts.items.getByID(extrasArray.getInt(i));

                this._configuration.addExtra(laser);
            }
        } catch(Exception e) {
            Console.println("Couldn't build configuration!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the configuration
     *
     * @return The configuration
     */
    public Configuration getConfiguration()
    {
        return this._configuration;
    }
}
