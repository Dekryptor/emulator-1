package com.manulaiko.blackeye.simulator.level;

import com.manulaiko.tabitha.Console;

/**
 * Level class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Level implements Cloneable
{
    /**
     * Level ID.
     */
    public int id = 1;

    /**
     * Account experience.
     */
    public long account = 0;

    /**
     * Pet experience.
     */
    public int pet = -1;

    /**
     * Drone experience.
     */
    public short drone = -1;

    /**
     * Shield bonus.
     */
    public double shield = 0.00;

    /**
     * Damage bonus.
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
}
