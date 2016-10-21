package com.manulaiko.blackeye.simulator.ship;

/**
 * Ship class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Ship
{
    /**
     * Ship id.
     */
    public int id;

    /**
     * Item id.
     */
    public int itemsID;

    /**
     * Health point.
     */
    public int health;

    /**
     * Speed points.
     */
    public int speed;

    /**
     * Cargo.
     */
    public int cargo;

    /**
     * Lasers slots.
     */
    public int lasers;

    /**
     * Generators slot.
     */
    public int generators;

    /**
     * Extras slots.
     */
    public int extras;

    /**
     * Batteries amount.
     */
    public int batteries;

    /**
     * Rockets amount.
     */
    public int rockets;

    /**
     * Reward.
     */
    public Reward reward;

    /**
     * Constructor.
     *
     * @param id         Ship id.
     * @param itemsID    Ship item's id.
     * @param health     Max health points.
     * @param speed      Basic speed.
     * @param cargo      Max cargo.
     * @param lasers     Laser slots.
     * @param generators Generators slots.
     * @param extras     Extras slots.
     * @param batteries  Batteries amount.
     * @param rockets    Rockets amount.
     */
    public Ship(int id, int itemsID, int health, int speed, int cargo, int lasers, int generators, int extras, int batteries, int rockets)
    {
        this.id         = id;
        this.itemsID    = itemsID;
        this.health     = health;
        this.speed      = speed;
        this.cargo      = cargo;
        this.lasers     = lasers;
        this.generators = generators;
        this.extras     = extras;
        this.batteries  = batteries;
        this.rockets    = rockets;
    }

    /**
     * Sets the reward.
     *
     * @param experience Experience points.
     * @param honor      Honor points
     */
    public void setReward(int experience, int honor)
    {
        this.reward = new Reward(experience, honor);
    }

    /**
     * Reward class.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    public class Reward
    {
        /**
         * Experience points.
         */
        public int experience;

        /**
         * Honor points.
         */
        public int honor;

        /**
         * Constructor.
         *
         * @param experience Experience points.
         * @param honor      Honor points.
         */
        public Reward(int experience, int honor)
        {
            this.experience = experience;
            this.honor      = honor;
        }
    }
}
