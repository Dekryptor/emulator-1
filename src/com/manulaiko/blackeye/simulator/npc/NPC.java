package com.manulaiko.blackeye.simulator.npc;

import java.util.HashMap;

import com.manulaiko.tabitha.utils.Point;

/**
 * NPC class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class NPC
{
    /**
     * NPC ID.
     */
    public int id = 0;

    /**
     * NPC name.
     */
    public String name = "";

    /**
     * NPC position.
     */
    public Point position = new Point(0, 0);

    /**
     * Graphic.
     */
    public int gfx;

    /**
     * Health points.
     */
    public int health;

    /**
     * Max health points.
     */
    public int maxHealth;

    /**
     * Shield points.
     */
    public int shield;

    /**
     * Max shield points.
     */
    public int maxShield;

    /**
     * Shield absorption.
     */
    public double shieldAbs;

    /**
     * Damage points.
     */
    public int damage;

    /**
     * Speed.
     */
    public int speed;

    /**
     * AI type
     */
    public int aiType;

    /**
     * Rewards.
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
         * Credits.
         */
        public int credits;

        /**
         * Uridium.
         */
        public int uridium;

        /**
         * Resources.
         * 
         * Key: Resource ID.
         * Value: Amount of resources.
         */
        public HashMap<Integer, Integer> resources = new HashMap<>();

        /**
         * Constructor
         *
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
