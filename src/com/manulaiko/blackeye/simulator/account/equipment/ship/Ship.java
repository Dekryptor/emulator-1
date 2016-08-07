package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.awt.Point;

import com.manulaiko.blackeye.simulator.map.Map;

/**
 * Ship class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.ship
 */
public class Ship
{
    /**
     * Ship ID
     */
    public int id;

    /**
     * Ship object
     */
    public com.manulaiko.blackeye.simulator.ship.Ship ship;

    /**
     * ships_id
     */
    public int shipID;

    /**
     * Graphic ID
     */
    public int gfx;

    /**
     * Map object
     */
    public Map map;

    /**
     * Map ID
     */
    public int mapID;

    /**
     * Position on map
     */
    public Point position;

    /**
     * Position JSON
     */
    public String positionJSON;

    /**
     * Health points
     */
    public int health;

    /**
     * Nanohull points
     */
    public int nanohull;

    /**
     * Shield points
     */
    public int shield;

    /**
     * Wether ship is cloaked or not
     */
    public boolean isCloaked = false;

    /**
     * Constructor
     *
     * @param id           Ship ID
     * @param gfx          Graphic ID
     * @param mapID        Map ID
     * @param shipID       ships_id
     * @param positionJSON JSON Array
     * @param health       Health points
     * @param nanohull     Nanohull points
     * @param shield       Shield points
     */
    public Ship(int id, int gfx, int mapID, int shipID, String positionJSON, int health, int nanohull, int shield)
    {
        this.id           = id;
        this.gfx          = gfx;
        this.mapID        = mapID;
        this.shipID       = shipID;
        this.positionJSON = positionJSON;
        this.health       = health;
        this.nanohull     = nanohull;
        this.shield       = shield;
    }

    /**
     * Sets position object
     *
     * @param position Point position
     */
    public void setPosition(Point position)
    {
        this.position = position;
    }

    /**
     * Sets ship object
     *
     * @param ship Ship object
     */
    public void setShip(com.manulaiko.blackeye.simulator.ship.Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Sets map object
     *
     * @param map Map object
     */
    public void setMap(Map map)
    {
        this.map = map;
    }
}
