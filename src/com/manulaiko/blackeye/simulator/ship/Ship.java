package com.manulaiko.blackeye.simulator.ship;

import java.util.HashMap;

import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;
import org.json.JSONObject;

/**
 * Ship class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Ship extends Simulator implements Cloneable
{
    /**
     * Ship id.
     *
     * @var ID.
     */
    public int id;

    /**
     * Item id.
     *
     * @var ID of `items` table.
     */
    public int itemsID;

    /**
     * Health point.
     *
     * @var Health points.
     */
    public int health;

    /**
     * Speed points.
     *
     * @var Base speed.
     */
    public int speed;

    /**
     * Cargo.
     *
     * @var Cargo space.
     */
    public int cargo;

    /**
     * Lasers slots.
     *
     * @var Lasers.
     */
    public int lasers;

    /**
     * Generators slot.
     *
     * @var Generators.
     */
    public int generators;

    /**
     * Extras slots.
     *
     * @var Extras.
     */
    public int extras;

    /**
     * Batteries amount.
     *
     * @var Batteries space.
     */
    public int batteries;

    /**
     * Rockets amount.
     *
     * @var Rocket space.
     */
    public int rockets;

    /**
     * Reward.
     *
     * @var Killing reward.
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
     * @param honor      Honor points.
     */
    public void setReward(int experience, int honor)
    {
        this.reward = new Reward(experience, honor);
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Ship clone()
    {
        try {
            Ship s = (Ship)super.clone();

            s.setReward(this.reward.experience, this.reward.honor);

            return s;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone ship!");
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

        fields.put("items_id", this.itemsID);
        fields.put("health", this.health);
        fields.put("speed", this.speed);
        fields.put("cargo", this.cargo);
        fields.put("lasers", this.lasers);
        fields.put("extras", this.extras);
        fields.put("batteries", this.batteries);
        fields.put("rockets", this.rockets);
        fields.put("reward", this.reward);

        return fields;
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
         *
         * @var Experience.
         */
        public int experience;

        /**
         * Honor points.
         *
         * @var Honor.
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

        /**
         * Returns the reward as a JSON.
         *
         * @return Reward as JSON.
         */
        public String toString()
        {
            JSONObject json = new JSONObject();

            try {
                json.put("experience", this.experience);
                json.put("honor", this.honor);
            } catch(Exception e) {
                return "{}";
            }

            return json.toString();
        }
    }
}
