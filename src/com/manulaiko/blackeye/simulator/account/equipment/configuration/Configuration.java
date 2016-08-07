package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.util.ArrayList;

import com.manulaiko.blackeye.simulator.item.Item;
import com.manulaiko.blackeye.simulator.item.items.*;

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
    public ArrayList<Laser> lasers = new ArrayList<>();

    /**
     * Hellstorms
     */
    public ArrayList<Hellstorm> hellstorms = new ArrayList<>();

    /**
     * Generators
     */
    public ArrayList<Item> generators = new ArrayList<>();

    /**
     * Extras
     */
    public ArrayList<Extra> extras = new ArrayList<>();

    /**
     * Constructor
     *
     * @param id         Configuration ID
     */
    public Configuration(int id)
    {
        this.id = id;
    }

    /**
     * Calculates and returns damage
     *
     * @return Laser damage
     */
    public int calculateDamage()
    {
        int damage = 0;

        for(Laser laser : this.lasers) {
            damage += laser.value;

            if(laser.isElite) {
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
            if(!(generator instanceof Shield)) {
                continue;
            }

            speed += generator.value;
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
            if(!(generator instanceof Generator)) {
                continue;
            }

            shield += generator.value;
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

        for(Item generator : this.generators) {
            if(!(generator instanceof Generator)) {
                continue;
            }
            Shield shield = (Shield)generator;

            absorption += shield.absorption;
            shields    += 1;
        }

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
}
