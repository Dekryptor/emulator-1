package com.manulaiko.blackeye.simulator.npc;

import java.awt.Point;
import java.sql.ResultSet;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.tabitha.Console;

/**
 * NPC builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.npc
 */
public class Builder
{
    /**
     * NPC object
     *
     * The current npc we're building
     */
    private NPC _npc;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            JSONObject reward = new JSONObject(rs.getString("reward"));
            
            this._npc = new NPC(
                    rs.getInt("id"),
                    rs.getInt("gfx"),
                    rs.getString("name"),
                    rs.getInt("health"),
                    rs.getInt("shield"),
                    rs.getDouble("shield_absorbtion"),
                    rs.getInt("damage"),
                    rs.getInt("speed"),
                    rs.getInt("ai_type")
            );
            
            this._build(reward);
        } catch(Exception e) {
            Console.println("Couldn't build npc!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a npc
     *
     * @para npc NPC to clone
     */
    public Builder(NPC npc)
    {
        try {
            this._npc = new NPC(
                    npc.id,
                    npc.gfx,
                    npc.name,
                    npc.health,
                    npc.shield,
                    npc.shieldAbs,
                    npc.damage,
                    npc.speed,
                    npc.aiType
            );

            this._build(npc.rewardJSON);
        } catch(Exception e) {
            Console.println("Couldn't clone npc!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets the reward
     */
    private void _build(JSONObject reward)
    {
        this._npc.setReward(reward);
    }
    
    /**
     * Returns the npc
     *
     * @return The npc
     */
    public NPC getNPC()
    {
        return this._npc;
    }
}
