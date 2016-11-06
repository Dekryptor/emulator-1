package com.manulaiko.blackeye.simulator.level;

import java.util.HashMap;

import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;

/**
 * Level class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Level extends Simulator implements Cloneable
{
    /**
     * Level ID.
     *
     * @var ID.
     */
    public int id = 1;

    /**
     * Account experience.
     *
     * Experience needed for accounts to reach this level.
     *
     * @var Accounts experience.
     */
    public long account = 0;

    /**
     * Pet experience.
     *
     * Experience needed for pet to reach this level.
     *
     * @var Pet experience.
     */
    public int pet = -1;

    /**
     * Drone experience.
     *
     * Experience needed for drones to reach this level.
     *
     * @var Drone experience.
     */
    public short drone = -1;

    /**
     * Shield bonus.
     *
     * Shield bonus for item upgrades.
     *
     * @var Shield bonus.
     */
    public double shield = 0.00;

    /**
     * Damage bonus.
     *
     * Damage bonus for item upgrades.
     *
     * @var Damage bonus.
     */
    public double damage = 0.00;

    /**
     * Constructor.
     *
     * @param id      Level ID.
     * @param account Account experience.
     * @param pet     Pet experience.
     * @param drone   Drone experience.
     * @param shield  Shield bonus.
     * @param damage  Damage bonus.
     */
    public Level(int id, long account, int pet, short drone, double shield, double damage)
    {
        this.id      = id;
        this.account = account;
        this.pet     = pet;
        this.drone   = drone;
        this.shield  = shield;
        this.damage  = damage;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Level clone()
    {
        try {
            Level l = (Level)super.clone();

            return l;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone level!");
            Console.println(e.getMessage());

            return null;
        }
    }

    /**
     * Returns table identifier.
     *
     * @return Table identifier.
     */
    protected int _getDatabaseIdentifier()
    {
        return this.id;
    }

    /**
     * Returns table fields.
     *
     * @return Table fields.
     */
    protected HashMap<String, Object> _getDatabaseFields()
    {
        HashMap<String, Object> fields = new HashMap<>();

        fields.put("accounts", this.account);
        fields.put("pets", this.pet);
        fields.put("drones", this.drone);
        fields.put("shield", this.shield);
        fields.put("damage", this.damage);

        return fields;
    }
}
