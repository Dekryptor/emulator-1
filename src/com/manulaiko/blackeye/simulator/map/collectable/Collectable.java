package com.manulaiko.blackeye.simulator.map.collectable;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Collectable class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Collectable
{
    ///////////////////////////
    // Start class constants //
    ///////////////////////////
    public static final int CLASS_BOX      = 0;
    public static final int CLASS_RESOURCE = 1;
    public static final int CLASS_BEACON   = 2;
    public static final int CLASS_FIREWORK = 3;
    /////////////////////////
    // End class constants //
    /////////////////////////

    /**
     * Collectable ID.
     */
    public int id = 1;

    /**
     * Graphic.
     */
    public int gfx = 1;

    /**
     * Class.
     */
    public int classID = 1;

    /**
     * Name.
     */
    public String name = "box0";

    /**
     * Position.
     */
    public Point position = new Point(0, 0);

    /**
     * Available rewards.
     */
    public ArrayList<Reward> rewards = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param id       Collectable ID.
     * @param gfx      Graphic.
     * @param classID  Class.
     * @param name     Name.
     * @param position Position.
     */
    public Collectable(int id, int gfx, int classID, String name, Point position)
    {
        this.id       = id;
        this.gfx      = gfx;
        this.classID  = classID;
        this.name     = name;
        this.position = position;
    }

    /**
     * Adds a reward to the array.
     */
    public void addReward(JSONObject reward)
    {
        try {
            this.rewards.add(new Reward(
                    reward.getInt("items_id"),
                    reward.getInt("amount"),
                    reward.getDouble("probability")
            ));
        } catch(Exception e) {
            Console.println("Couldn't add reward!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Collectable clone()
    {
        try {
            Collectable c = (Collectable)super.clone();

            c.position = new Point(this.position.getX(), this.position.getY());
            this.rewards.forEach((r)->{
                c.rewards.add(r.clone());
            });

            return c;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone station!");
            Console.println(e.getMessage());

            return null;
        }
    }
    /**
     * Reward class.
     *
     * @author Manulaiko <manulaiko@gmail.com>
     */
    public class Reward implements Cloneable
    {
        /**
         * Item id.
         */
        public int itemsID;

        /**
         * Amount.
         */
        public int amount;

        /**
         * Probability.
         */
        public double probability;

        /**
         * Constructor
         *
         * @param itemsID     Item to award.
         * @param amount      Amount of `items_id` to award.
         * @param probability Chances of this reward being awarded.
         */
        public Reward(int itemsID, int amount, double probability)
        {
            this.itemsID     = itemsID;
            this.amount      = amount;
            this.probability = probability;
        }

        /**
         * Clones the object.
         *
         * @return Cloned object.
         */
        public Reward clone()
        {
            try {
                Reward r = (Reward)super.clone();

                return r;
            } catch(CloneNotSupportedException e) {
                Console.println("Couldn't clone reward!");
                Console.println(e.getMessage());

                return null;
            }
        }
    }
}
