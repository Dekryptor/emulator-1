package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.util.HashMap;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.packet.command.ResourcesInitialization;
import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.blackeye.simulator.account.equipment.configuration.Configuration;
import com.manulaiko.tabitha.Console;

/**
 * Hangar class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Hangar implements Cloneable
{
    /**
     * Hangar ID.
     *
     * @var Hangar ID.
     */
    public int id;

    /**
     * Account ID.
     *
     * @var Hangar owner.
     */
    public int accountID;

    /**
     * Ship object.
     *
     * @var Ship.
     */
    public Ship ship;

    /**
     * Available configurations.
     *
     * @var Available configurations.
     */
    public HashMap<Integer, Configuration> configurations = new HashMap<>();

    /**
     * Active configuration object.
     *
     * @var Active configuration.
     */
    public Configuration activeConfiguration;

    /**
     * Resources.
     *
     * @var Hangar resources.
     */
    public HashMap<Integer, Integer> resources;

    /**
     * Constructor.
     *
     * @param id        Hangar ID.
     * @param accountID Account's ID.
     */
    public Hangar(int id, int accountID)
    {
        this.id        = id;
        this.accountID = accountID;
    }

    /**
     * Sets resources map.
     *
     * @param resources Resources map.
     */
    public void setResources(HashMap<Integer, Integer> resources)
    {
        this.resources = resources;
    }

    /**
     * Sets ship object.
     *
     * @param ship Ship object.
     */
    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Changes configuration.
     *
     * @param id Configuration ID.
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
     * Adds a configuration.
     *
     * @param configuration Configuration object.
     */
    public void addConfiguration(Configuration configuration)
    {
        this.configurations.put((this.configurations.size() + 1), configuration);
    }

    /**
     * Sets configuration's map.
     *
     * @param configurations Configuration's map.
     */
    public void setConfigurations(HashMap<Integer, Configuration> configurations)
    {
        this.configurations = configurations;
    }

    /**
     * Returns speed points.
     *
     * @return Speed points.
     */
    public int getSpeed()
    {
        //TODO Add resource upgrades
        int baseSpeed = this.ship.ship.speed;
        baseSpeed += this.activeConfiguration.speed;

        return baseSpeed;
    }

    /**
     * Returns current shield points.
     *
     * @return Current shield points.
     */
    public int getShield()
    {
        return this.ship.shield;
    }

    /**
     * Returns max shield points.
     *
     * @return Max shield points.
     */
    public int getMaxShield()
    {
        //TODO Add resource upgrades and boosters
        int shield = this.activeConfiguration.shield;

        return shield;
    }

    /**
     * Returns current health points.
     *
     * @return Current health points.
     */
    public int getHealth()
    {
        return this.ship.health;
    }

    /**
     * Returns max health points.
     *
     * @return Max health points.
     */
    public int getMaxHealth()
    {
        //TODO Add boosters
        return this.ship.ship.health;
    }

    /**
     * Returns current cargo amount.
     *
     * @return Current cargo amount.
     */
    public int getCargo()
    {
        int cargo = 0;

        cargo += this.resources.getOrDefault(0, 0);
        cargo += this.resources.getOrDefault(1, 0);
        cargo += this.resources.getOrDefault(2, 0);
        cargo += this.resources.getOrDefault(3, 0);
        cargo += this.resources.getOrDefault(4, 0);
        cargo += this.resources.getOrDefault(5, 0);
        cargo += this.resources.getOrDefault(6, 0);
        cargo += this.resources.getOrDefault(7, 0);
        cargo += this.resources.getOrDefault(8, 0);

        return cargo;
    }

    /**
     * Returns max cargo amount.
     *
     * @return Max cargo amount.
     */
    public int getMaxCargo()
    {
        return this.ship.ship.cargo;
    }

    /**
     * Returns current laser ammo amount.
     *
     * @return Current battery amount.
     */
    public int getBatteriesAmount()
    {
        return this.ship.ship.batteries;
    }

    /**
     * Returns current current amount.
     *
     * @return Current rockets amount.
     */
    public int getRocketsAmount()
    {
        return this.ship.ship.rockets;
    }

    /**
     * Returns expansions graphic.
     *
     * @return Expansions graphic.
     */
    public int getExpansions()
    {
        return this.activeConfiguration.expansions;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Hangar clone()
    {
        try {
            Hangar h = (Hangar)super.clone();

            HashMap<Integer, Integer> resources = new HashMap<>();
            this.resources.forEach((i, a)->{
                resources.put(i, a);
            });
            h.setResources(resources);

            HashMap<Integer, Configuration> configurations = new HashMap<>();
            this.configurations.forEach((i, c)->{
                configurations.put(i, c.clone());
            });
            h.setConfigurations(configurations);

            h.setShip(this.ship.clone());

            return h;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone hangar");
            Console.println(e.getMessage());

            return null;
        }
    }

    /**
     * Builds and returns ResourcesInitialization command.
     *
     * @return ResourcesInitialization command.
     */
    public ResourcesInitialization getResourcesInitializationCommand()
    {
        ResourcesInitialization p = (ResourcesInitialization) ServerManager.game.packetFactory.getCommandByName("ResourcesInitialization");

        p.prometium = this.resources.getOrDefault(0, 0);
        p.endurium  = this.resources.getOrDefault(1, 0);
        p.terbium   = this.resources.getOrDefault(2, 0);
        p.xenomit   = this.resources.getOrDefault(3, 0);
        p.prometid  = this.resources.getOrDefault(4, 0);
        p.duranium  = this.resources.getOrDefault(5, 0);
        p.promerium = this.resources.getOrDefault(6, 0);
        p.seprom    = this.resources.getOrDefault(7, 0);
        p.palladium = this.resources.getOrDefault(8, 0);

        return p;
    }
}
