package com.manulaiko.blackeye.simulator.level;

/**
 * Level class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Level
{
    /**
     * Level ID
     */
    public int id;

    /**
     * Accounts' experience
     */
    public long accounts;

    /**
     * Drones' experience
     */
    public int drones;

    /**
     * PETs' experience
     */
    public int pets;

    /**
     * Constructor
     *
     * @param id       Level ID
     * @param accounts Accounts' experience
     * @param drones   Drones' experience
     * @param pets     PETs' experience
     */
    public Level(int id, long accounts, int drones, int pets)
    {
        this.id       = id;
        this.accounts = accounts;
        this.drones   = drones;
        this.pets     = pets;
    }
}
