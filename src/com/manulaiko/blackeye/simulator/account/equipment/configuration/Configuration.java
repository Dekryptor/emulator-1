package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.util.ArrayList;

import com.manulaiko.blackeye.simulator.account.equipment.item.Item;

/**
 * Configuration class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.configuration
 */
public class Configuration
{
    /**
     * Configuration ID
     */
    public int id;

    /**
     * Account ID
     */
    public int accountID;

    /**
     * `accounts_equipment_ships_id`
     */
    public int shipID;

    /**
     * Configuration
     */
    public int configuration;

    /**
     * Speed
     */
    public int speed;

    /**
     * Laser damage
     */
    public int damage;

    /**
     * Elite lasers
     */
    public int eliteLasers;

    /**
     * Shield
     */
    public int shield;

    /**
     * Shield absorption
     */
    public double shieldAbsorption;

    /**
     * Expansions
     */
    public int expansions;

    /**
     * Lasers
     */
    public ArrayList<Item> lasers = new ArrayList<>();

    /**
     * Lasers JSON
     */
    public String lasersJSON;

    /**
     * Hellstorms
     */
    public ArrayList<Item> hellstorms = new ArrayList<>();

    /**
     * Hellstorms JSON
     */
    public String hellstormsJSON;

    /**
     * Generators
     */
    public ArrayList<Item> generators = new ArrayList<>();

    /**
     * Generators JSON
     */
    public String generatorsJSON;

    /**
     * Extras
     */
    public ArrayList<Item> extras = new ArrayList<>();

    /**
     * Extras JSON
     */
    public String extrasJSON;

    /**
     * Constructor
     *
     * @param id            Configuration ID
     * @param accountID     Account's ID
     * @param shipID        accounts_equipment_ships_id
     * @param configuration Configuration number
     */
    public Configuration(int id, int accountID, int shipID, int configuration, String lasers, String hellstorms, String generators, String extras)
    {
        this.id             = id;
        this.accountID      = accountID;
        this.shipID         = shipID;
        this.configuration  = configuration;
        this.lasersJSON     = lasers;
        this.hellstormsJSON = hellstorms;
        this.generatorsJSON = generators;
        this.extrasJSON     = extras;
    }

    /**
     * Calculates and returns damage
     *
     * @return Laser damage
     */
    public int calculateDamage()
    {
        int damage = 0;

        for(Item laser : this.lasers) {
            damage += laser.item.value;

            if(laser.item.isElite) {
                this.eliteLasers += 1;
            }
        }

        return damage;
    }

    /**
     * Calculates and returns speed
     *
     * @return Speed
     */
    public int calculateSpeed()
    {
        int speed = 0;

        for(Item generator : this.generators) {
            if(generator.item.category.equalsIgnoreCase("generator_speed")) {
                speed += generator.item.value;
            }
        }

        return speed;
    }

    /**
     * Calculates and return shield
     *
     * @return Shield
     */
    public int calculateShield()
    {
        int shield = 0;

        for(Item generator : this.generators) {
            if(generator.item.category.equalsIgnoreCase("generator_shield")) {
                shield += generator.item.value;
            }
        }

        return shield;
    }

    /**
     * Calculates and returns shield absorption
     *
     * @return Shield absorption
     */
    public double calculateShieldAbsorption()
    {
        double absorption = 0.00;
        int    shields    = 0;

        /* TODO add shield absorption
        for(Item generator : this.generators) {
            if(generator.item.category.equalsIgnoreCase("generator_shield")) {

            }
            absorption += shield.absorption;
            shields    += 1;
        }*/


        return (absorption / shields);
    }

    /**
     * Calculates and returns expansions
     *
     * @return Expansions
     */
    public int calculateExpansions()
    {
        int expansions = 1;

        if(this.eliteLasers <= (this.lasers.size() / 2)) {
            expansions = 2;
        } else if(this.eliteLasers == this.lasers.size()) {
            expansions = 3;
        }

        return expansions;
    }

    /**
     * Calculates and sets configuration's stats
     */
    public void calculate()
    {
        this.damage           = this.calculateDamage();
        this.shield           = this.calculateShield();
        this.speed            = this.calculateSpeed();
        this.shieldAbsorption = this.calculateShieldAbsorption();
        this.expansions       = this.calculateExpansions();
    }

    /**
     * Adds a laser to the array
     *
     * @param laser Laser to add
     */
    public void addLaser(Item laser)
    {
        this.lasers.add(laser);
    }

    /**
     * Adds a hellstorm to the array
     *
     * @param hellstorm Hellstorm to add
     */
    public void addHellstorm(Item hellstorm)
    {
        this.hellstorms.add(hellstorm);
    }

    /**
     * Adds a generator to the array
     *
     * @param generator Generator to add
     */
    public void addGenerator(Item generator)
    {
        this.generators.add(generator);
    }

    /**
     * Adds an extra to the array
     *
     * @param extra Extra to add
     */
    public void addExtra(Item extra)
    {
        this.extras.add(extra);
    }
}
