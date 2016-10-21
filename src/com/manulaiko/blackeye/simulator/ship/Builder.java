package com.manulaiko.blackeye.simulator.ship;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.item.Item;
import com.manulaiko.tabitha.Console;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ship builder.
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
     * Builds a ship.
     */
    public void build()
    {
        try {
            JSONObject reward = new JSONObject(this._result.getString("reward"));

            this._object = new Ship(
                    this._result.getInt("id"),
                    this._result.getInt("items_id"),
                    this._result.getInt("health"),
                    this._result.getInt("speed"),
                    this._result.getInt("cargo"),
                    this._result.getInt("lasers"),
                    this._result.getInt("generators"),
                    this._result.getInt("extras"),
                    this._result.getInt("batteries"),
                    this._result.getInt("rockets")
            );

            this._setReward(reward);
        } catch(Exception e) {
            Console.println("Couldn't build ship!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets ship's reward.
     *
     * @param reward Reward JSON.
     */
    private void _setReward(JSONObject reward) throws JSONException
    {
        int experience = reward.getInt("experience");
        int honor      = reward.getInt("honor");

        ((Ship)this._object).setReward(experience, honor);
    }
}
