package com.manulaiko.blackeye.simulator.npc;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.packets.commands.CreateShip;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * NPC class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class NPC
{
    /**
     * NPC ID
     */
    public int id = 0;

    /**
     * NPC name
     */
    public String name = "";

    /**
     * NPC position
     */
    public Point position = new Point(0, 0);

    /**
     * Graphic
     */
    public int gfx;

    /**
     * Health points
     */
    public int health;

    /**
     * Max health points
     */
    public int maxHealth;

    /**
     * Shield points
     */
    public int shield;

    /**
     * Max shield points
     */
    public int maxShield;

    /**
     * Shield absorption
     */
    public double shieldAbs;

    /**
     * Damage points
     */
    public int damage;

    /**
     * Speed
     */
    public int speed;

    /**
     * AI type
     */
    public int aiType;

    /**
     * Rewards
     */
    public Reward reward;

    /**
     * Reward json
     */
    public JSONObject rewardJSON;

    /**
     * Constructor
     *
     * @param id        NPC id
     * @param gfx       Graphic
     * @param name      NPC name
     * @param health    Health points
     * @param shield    Shield points
     * @param shieldAbs Shield absorption
     * @param damage    Damage
     * @param speed     Speed
     * @param aiType    AI type
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
     * Sets the reward object
     */
    public void setReward(JSONObject reward)
    {
        try {
            this.reward = new Reward(
                    reward.getInt("experience"),
                    reward.getInt("honor"),
                    reward.getInt("credits"),
                    reward.getInt("uridium"),
                    reward.getJSONArray("resources")
            );
            this.rewardJSON = reward;
        } catch(Exception e) {
            Console.println("Couldn't set reward!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds and returns CreateShip packet
     *
     * @return Create ship packet
     */
    public CreateShip getCreateShipCommand()
    {
        CreateShip p = (CreateShip) ServerManager.game.packetFactory.getCommandByName("CreateShip");

        p.id            = this.id;
        p.shipID        = this.gfx;
        p.expansion     = 0;
        p.clanTag       = "";
        p.name          = this.name;
        p.x             = this.position.getX();
        p.y             = this.position.getY();
        p.factionID     = 0;
        p.clanID        = 0;
        p.rankID        = 0;
        p.warningIcon   = false;
        p.clanDiplomacy = 0;
        p.ggRings       = 0;
        p.isNPC         = true;
        p.isCloaked     = false;

        return p;
    }

    /**
     * Reward class
     */
    public class Reward
    {
        /**
         * Experience points
         */
        public int experience;

        /**
         * Honor points
         */
        public int honor;

        /**
         * Credits
         */
        public int credits;

        /**
         * Uridium
         */
        public int uridium;

        /**
         * Resources
         */
        public HashMap<Integer, Integer> resources = new HashMap<>();

        /**
         * Constructor
         *
         * @param experience Experience points
         * @param honor      Honor points
         * @param credits    Credits
         * @param uridium    Uridium
         * @param resources  Cargobox resources
         */
        public Reward(int experience, int honor, int credits, int uridium, JSONArray resources)
        {
            try {
                this.experience = experience;
                this.honor = honor;
                this.credits = credits;
                this.uridium = uridium;

                for(int i = 0; i < resources.length(); i++) {
                    this.resources.put(i, resources.getInt(i));
                }
            } catch(Exception e) {
                Console.println("Couldn't instance reward!");
                Console.println(e.getMessage());
            }
        }
    }
}
