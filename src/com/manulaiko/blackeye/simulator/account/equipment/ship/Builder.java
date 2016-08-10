package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.sql.ResultSet;

import org.json.JSONArray;

import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Ship builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder
{
    /**
     * Configuration object
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
            this._ship = new Ship(
                    rs.getInt("id"),
                    rs.getInt("gfx"),
                    rs.getInt("maps_id"),
                    rs.getInt("ships_id"),
                    rs.getString("position"),
                    rs.getInt("health"),
                    rs.getInt("nanohull"),
                    rs.getInt("shield"),
                    rs.getInt("active_configuration")
            );

            this._build(this._ship.mapID, this._ship.shipID, this._ship.positionJSON);
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
     * @para ship Ship clone
     */
    public Builder(Ship ship)
    {
        try {
            this._ship = new Ship(
                    ship.id,
                    ship.gfx,
                    ship.mapID,
                    ship.shipID,
                    ship.positionJSON,
                    ship.health,
                    ship.nanohull,
                    ship.shield,
                    ship.activeConfiguration
            );

            this._build(ship.mapID, ship.shipID, ship.positionJSON);
        } catch(Exception e) {
            Console.println("Couldn't clone ship!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the ship
     *
     * @param mapID        Map ID
     * @param shipID       ships_id
     * @param positionJSON Position json
     */
    private void _build(int mapID, int shipID, String positionJSON)
    {
        try {
            this._ship.setMap(GameManager.maps.getByID(mapID));
            this._ship.setShip(GameManager.ships.getByID(shipID));

            JSONArray position = new JSONArray(positionJSON);

            this._ship.setPosition(new Point(position.getInt(0), position.getInt(1)));
        } catch(Exception e) {
            Console.println("Couldn't build ship!");
            Console.println(e.getMessage());
        }
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
