package com.manulaiko.blackeye.simulator.map.collectable;

import java.sql.ResultSet;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Collectable builder.
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
     * Builds and returns a collectable.
     */
    public void build()
    {
        try {
            JSONArray rewards = new JSONArray(this._result.getString("rewards"));

            this._object = new Collectable(
                    this._result.getInt("id"),
                    this._result.getInt("gfx"),
                    this._result.getInt("class"),
                    this._result.getString("name"),
                    rewards,
                    new Point(0, 0)
            );

            this._setRewards(rewards);
        } catch(Exception e) {
            Console.println("Couldn't build collectable!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets collectable's rewards.
     *
     * @param rewards Rewards to add.
     *
     * @throws JSONException If the json couldn't be parsed.
     */
    private void _setRewards(JSONArray rewards) throws JSONException
    {
        for(int i = 0; i < rewards.length(); i++) {
            JSONObject reward = rewards.getJSONObject(i);

            // Isn't this ugly as fuck? I guess I'll have to say bye-bye to inheritance
            ((Collectable)this._object).addReward(reward);
        }
    }
}
