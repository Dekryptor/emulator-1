package com.manulaiko.blackeye.simulator.ship;

import java.sql.ResultSet;

import org.json.JSONObject;

import com.manulaiko.tabitha.Console;

/**
 * Ship builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.ship
 */
public class Builder
{
    /**
     * Ship object
     *
     * The current ship we're building
     */
    private Ship _ship;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            JSONObject reward = new JSONObject(rs.getString("reward"));
            
            this._ship = new Ship(
                    rs.getInt("id"),
                    rs.getInt("items_id"),
                    rs.getInt("health"),
                    rs.getInt("speed"),
                    rs.getInt("cargo"),
                    rs.getInt("lasers"),
                    rs.getInt("generators"),
                    rs.getInt("extras"),
                    rs.getInt("batteries"),
                    rs.getInt("rockets")
            );
            
            this._build(reward);
        } catch(Exception e) {
            Console.println("Couldn't build ship!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a ship
     *
     * @para ship Ship to clone
     */
    public Builder(Ship ship)
    {
        try {

            this._ship = new Ship(
                    ship.id,
                    ship.itemsID,
                    ship.health,
                    ship.speed,
                    ship.cargo,
                    ship.lasers,
                    ship.generators,
                    ship.extras,
                    ship.batteries,
                    ship.rockets
            );

            this._build(ship.rewardJSON);
        } catch(Exception e) {
            Console.println("Couldn't clone ship!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds a ship
     *
     * @param reward Reward
     *
     * @return The ship
     */
    private void _build(JSONObject reward)
    {
        this._ship.setReward(reward);
    }

    /**
     * Returns the ship
     *
     * @return The ship
     */
    public Ship getShip()
    {
        return this._ship;
    }
}
