package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.util.ArrayList;

import com.manulaiko.blackeye.simulator.account.equipment.item.Item;

import com.manulaiko.tabitha.Console;

/**
 * Configuration class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Configuration implements Cloneable
{
    /**
     * Configuration ID.
     *
     * @var Configuration ID.
     */
    public int id;

    /**
     * Configuration ship id.
     *
     * @var `accounts_equipment_ships_id`.
     */
    public int shipID;

    /**
     * Configuration.
     *
     * The number of the configuration (1 or 2, or maybe 3).
     *
     * @var Configuration number.
     */
    public int configuration;

    /**
     * Speed.
     *
     * @var Speed points.
     */
    public int speed;

    /**
     * Laser damage.
     *
     * @var Damage points.
     */
    public int damage;

    /**
     * Elite lasers.
     *
     * It will be used to send the right laser graphic.
     *
     * @var Amount of elite lasers.
     */
    public int eliteLasers;

    /**
     * Shield.
     *
     * @var Shield points.
     */
    public int shield;

    /**
     * Shield absorption.
     *
     * @var Shield absorption rate.
     */
    public double shieldAbsorption;

    /**
     * Expansions.
     *
     * @var Configuration expansions graphic.
     */
    public int expansions;

    /**
     * Lasers.
     *
     * @var Configuration lasers.
     */
    public ArrayList<Item> lasers = new ArrayList<>();

    /**
     * Hellstorms.
     *
     * @var Configuration hellstorms.
     */
    public ArrayList<Item> hellstorms = new ArrayList<>();

    /**
     * Generators.
     *
     * @var Configuration generators.
     */
    public ArrayList<Item> generators = new ArrayList<>();

    /**
     * Extras.
     *
     * @var Configuration extras.
     */
    public ArrayList<Item> extras = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param id            Configuration ID.
     * @param shipID        accounts_equipment_ships_id.
     * @param configuration Configuration number.
     */
    public Configuration(int id, int shipID, int configuration)
    {
        this.id             = id;
        this.shipID         = shipID;
        this.configuration  = configuration;
    }

    /**
     * Calculates and returns damage.
     *
     * @return Laser damage.
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
     * Calculates and returns speed.
     *
     * @return Speed.
     */
    public int calculateSpeed()
    {
        int speed = 0;

        for(Item generator : this.generators) {
            if(!generator.item.category.equalsIgnoreCase("generator_speed")) {
                continue;
            }
            speed += generator.item.value;
        }

        return speed;
    }

    /**
     * Calculates and return shield.
     *
     * @return Shield.
     */
    public int calculateShield()
    {
        int shield = 0;

        for(Item generator : this.generators) {
            if(!generator.item.category.equalsIgnoreCase("generator_shield")) {
                continue;
            }
            shield += generator.item.value;
        }

        return shield;
    }

    /**
     * Calculates and returns shield absorption.
     *
     * @return Shield absorption.
     */
    public double calculateShieldAbsorption()
    {
        double absorption = 0.00;
        int    shields    = 0;

        for(Item generator : this.generators) {
            if(!generator.item.category.equalsIgnoreCase("generator_shield")) {
                continue;
            }

            try {
                absorption += generator.item.extras.getDouble("absorption");
                shields += 1;
            } catch(Exception e) {
                Console.println("Couldn't calculate shield absorption!");
                Console.println(e.getMessage());
            }
        }

        return (absorption / shields);
    }

    /**
     * Calculates and returns expansions.
     *
     * @return Expansions.
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
     * Calculates and sets configuration's stats.
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
     * Adds a laser to the array.
     *
     * @param laser Laser to add.
     */
    public void addLaser(Item laser)
    {
        this.lasers.add(laser);
    }

    /**
     * Adds a hellstorm to the array.
     *
     * @param hellstorm Hellstorm to add.
     */
    public void addHellstorm(Item hellstorm)
    {
        this.hellstorms.add(hellstorm);
    }

    /**
     * Adds a generator to the array.
     *
     * @param generator Generator to add.
     */
    public void addGenerator(Item generator)
    {
        this.generators.add(generator);
    }

    /**
     * Adds an extra to the array.
     *
     * @param extra Extra to add.
     */
    public void addExtra(Item extra)
    {
        this.extras.add(extra);
    }


    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Configuration clone()
    {
        try {
            Configuration c = (Configuration)super.clone();

            this.hellstorms.forEach((h)->{
                c.addHellstorm(h.clone());
            });
            this.lasers.forEach((l)->{
                c.addLaser(l.clone());
            });
            this.generators.forEach((g)->{
                c.addGenerator(g.clone());
            });
            this.extras.forEach((e)->{
                c.addExtra(e.clone());
            });

            return c;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone configuration!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
