package com.manulaiko.blackeye.simulator.account.equipment.item.ship;

/**
 * Ship builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.ship
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
    public Builder(java.sql.ResultSet rs)
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
                    rs.getInt("shield")
            );

            this._build(this._ship.mapID, this._ship.shipID, this._ship.positionJSON);
        } catch(Exception e) {
            com.manulaiko.tabitha.Console.println("Couldn't build ship!");
            com.manulaiko.tabitha.Console.println(e.getMessage());
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
                    ship.shield
            );

            this._build(ship.mapID, ship.shipID, ship.positionJSON);
        } catch(Exception e) {
            com.manulaiko.tabitha.Console.println("Couldn't clone ship!");
            com.manulaiko.tabitha.Console.println(e.getMessage());
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
            this._ship.setMap(com.manulaiko.blackeye.launcher.GameManager.maps.getByID(mapID));
            this._ship.setShip(com.manulaiko.blackeye.launcher.GameManager.ships.getByID(shipID));

            org.json.JSONArray position = new org.json.JSONArray(positionJSON);

            this._ship.setPosition(new java.awt.Point(position.getInt(0), position.getInt(1)));
        } catch(Exception e) {
            com.manulaiko.tabitha.Console.println("Couldn't build ship!");
            com.manulaiko.tabitha.Console.println(e.getMessage());
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
