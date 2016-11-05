package com.manulaiko.blackeye.simulator.npc;

import java.sql.ResultSet;
import java.util.HashMap;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * NPC builder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder extends com.manulaiko.blackeye.simulator.Builder
{
    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        super(rs);
    }

    /**
     * Builds a NPC.
     */
    public void build() throws Exception
    {
        JSONObject rewards = new JSONObject(this._result.getString("reward"));

        this._object = new NPC(
                this._result.getInt("id"),
                this._result.getInt("gfx"),
                this._result.getString("name"),
                this._result.getInt("health"),
                this._result.getInt("shield"),
                this._result.getDouble("shield_absorption"),
                this._result.getInt("damage"),
                this._result.getInt("speed"),
                this._result.getInt("ai_type")
        );

        this._setReward(rewards);
    }

    /**
     * Sets NPC's rewards.
     *
     * @param reward Reward to add.
     *
     * @throws JSONException If the json couldn't be parsed.
     */
    private void _setReward(JSONObject reward) throws JSONException
    {
        int experience = reward.getInt("experience");
        int honor      = reward.getInt("honor");
        int credits    = reward.getInt("credits");
        int uridium    = reward.getInt("uridium");
        JSONArray res  = reward.getJSONArray("resources");

        HashMap<Integer, Integer> resources = new HashMap<>();
        for(int i = 0; i < res.length(); i++) {
            resources.put(i, res.getInt(i));
        }

        ((NPC)this._object).setReward(experience, honor, credits, uridium, resources);
    }
}
