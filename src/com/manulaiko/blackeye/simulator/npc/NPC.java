package com.manulaiko.blackeye.simulator.npc;

import java.util.HashMap;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * NPC class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class NPC implements Cloneable
{
    /**
     * NPC ID.
     *
     * @var ID.
     */
    public int id = 0;

    /**
     * NPC name.
     *
     * @var Name.
     */
    public String name = "";

    /**
     * NPC position.
     *
     * @var Position.
     */
    public Point position = new Point(0, 0);

    /**
     * Graphic.
     *
     * @var Graphic ID.
     */
    public int gfx;

    /**
     * Health points.
     *
     * @var Health points.
     */
    public int health;

    /**
     * Max health points.
     *
     * @var Max health points.
     */
    public int maxHealth;

    /**
     * Shield points.
     *
     * @var Shield points.
     */
    public int shield;

    /**
     * Max shield points.
     *
     * @var Max shield points.
     */
    public int maxShield;

    /**
     * Shield absorption.
     *
     * @var Shield absorption rate.
     */
    public double shieldAbs;

    /**
     * Damage points.
     *
     * @var Damage points.
     */
    public int damage;

    /**
     * Speed.
     *
     * @var Speed points.
     */
    public int speed;

    /**
     * AI type.
     *
     * @var AI type.
     */
    public int aiType;

    /**
     * Rewards.
     *
     * @var Killing reward.
     */
    public Reward reward;

    /**
     * Constructor.
     *
     * @param id        NPC id.
     * @param gfx       Graphic.
     * @param name      NPC name.
     * @param health    Health points.
     * @param shield    Shield points.
     * @param shieldAbs Shield absorption.
     * @param damage    Damage.
     * @param speed     Speed.
     * @param aiType    AI type.
     */
    public NPC(
            int id, int gfx, String name, int health, int shield,
            double shieldAbs, int damage, int speed, int aiType
    ) {
        this.id        = id;
        this.gfx       = gfx;
        this.name      = name;
        this.health    = health;
        this.maxHealth = health;
        this.shield    = shield;
        this.maxShield = shield;
        this.shieldAbs = shieldAbs;
        this.damage    = damage;
        this.speed     = speed;
        this.aiType    = aiType;
    }

    /**
     * Sets the reward object.
     *
     * @param experience Experience points.
     * @param honor      Honor points.
     * @param credits    Credits.
     * @param uridium    Uridium.
     * @param resources  Resources.
     */
    public void setReward(int experience, int honor, int credits, int uridium, HashMap resources)
    {
        this.reward = new Reward(experience, honor, credits, uridium, resources);
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public NPC clone()
    {
        try {
            NPC n                               = (NPC)super.clone();
            HashMap<Integer, Integer> resources = new HashMap<>();

            // idk if this actually works since the tuple is made of
            // Integer objects instead of int primitive types...
            // TODO Check it's properly cloned
            this.reward.resources.forEach((i, r)->{
                resources.put(i, r);
            });

            n.setReward(
                    n.reward.experience,
                    n.reward.honor,
                    n.reward.credits,
                    n.reward.uridium,
                    resources
            );

            return n;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone npc!");
            Console.println(e.getMessage());

            return null;
        }
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
         * Credits.
         *
         * @var Credits.
         */
        public int credits;

        /**
         * Uridium.
         *
         * @var Uridium.
         */
        public int uridium;

        /**
         * Resources.
         * 
         * Key: Resource ID.
         * Value: Amount of resources.
         *
         * @var Resources.
         */
        public HashMap<Integer, Integer> resources = new HashMap<>();

        /**
         * Constructor.
         *
         * @param experience Experience points.
         * @param honor      Honor points.
         * @param credits    Credits.
         * @param uridium    Uridium.
         * @param resources  Cargo box Resources.
         */
        public Reward(int experience, int honor, int credits, int uridium, HashMap resources)
        {
            this.experience = experience;
            this.honor      = honor;
            this.credits    = credits;
            this.uridium    = uridium;
            this.resources  = resources;
        }
    }
}
