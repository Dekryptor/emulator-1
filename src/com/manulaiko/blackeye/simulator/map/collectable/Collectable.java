package com.manulaiko.blackeye.simulator.map.collectable;

import java.util.ArrayList;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.packet.command.CreateCollectable;
import com.manulaiko.blackeye.net.game.packet.command.CreateShip;
import com.manulaiko.blackeye.simulator.Simulator;
import org.json.JSONArray;
import org.json.JSONObject;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Collectable class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Collectable extends Simulator implements Cloneable
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
     *
     * @var ID.
     */
    public int id = 1;

    /**
     * Graphic.
     *
     * @var Graphic ID.
     */
    public int gfx = 1;

    /**
     * Class.
     *
     * @var Class of collectable.
     */
    public int classID = 1;

    /**
     * Name.
     *
     * @var Name.
     */
    public String name = "box0";

    /**
     * Position.
     *
     * @var Position on map.
     */
    public Point position = new Point(0, 0);

    /**
     * Available rewards.
     *
     * @var Rewards of this collectable.
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
     *
     * @param reward Reward to add.
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
            Console.println("Couldn't clone collectable!");
            Console.println(e.getMessage());

            return null;
        }
    }

    /**
     * Builds and returns the CreateCollectable command.
     *
     * @return CreateCollectable command.
     */
    public CreateCollectable getCreateCollectableCommand()
    {
        CreateCollectable p = (CreateCollectable) ServerManager.game.packetFactory.getCommandByName("CreateShip");

        p.id  = this.id;
        p.gfx = this.gfx;
        p.x   = this.position.getX();
        p.y   = this.position.getY();

        return p;
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
         *
         * @var Item to reward.
         */
        public int itemsID;

        /**
         * Amount.
         *
         * @var Amount of items to add.
         */
        public int amount;

        /**
         * Probability.
         *
         * @var Probability of receiving this reward.
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

        /**
         * Parses the object to a JSON.
         *
         * @return Reward as JSON.
         */
        public String toString()
        {
            JSONObject json = new JSONObject();

            try {
                json.append("items_id", this.itemsID);
                json.append("amount", this.amount);
                json.append("probability", this.probability);
            } catch(Exception e) {
                return "{}";
            }

            return json.toString();
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

        JSONArray rewards = new JSONArray();
        this.rewards.forEach((r)->{
            try {
                rewards.put(new JSONObject(r.toString()));
            } catch(Exception e) {
                rewards.put(new JSONObject());
            }
        });

        fields.put("gfx", this.gfx);
        fields.put("class", this.classID);
        fields.put("name", this.name);
        fields.put("rewards", rewards);

        return fields;
    }
}
