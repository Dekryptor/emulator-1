package com.manulaiko.blackeye.simulator.account.equipment.ship;

import com.manulaiko.blackeye.simulator.map.Map;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Ship class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Ship
{
    /**
     * Ship ID.
     */
    public int id;

    /**
     * Ship object.
     */
    public com.manulaiko.blackeye.simulator.ship.Ship ship;

    /**
     * ships_id.
     */
    public int shipID;

    /**
     * Graphic ID.
     */
    public int gfx;

    /**
     * Map object.
     */
    public Map map;

    /**
     * Map ID.
     */
    public int mapID;

    /**
     * Position on map.
     */
    public Point position;

    /**
     * Health points.
     */
    public int health;

    /**
     * Nanohull points.
     */
    public int nanohull;

    /**
     * Shield points.
     */
    public int shield;

    /**
     * Ship's active configuration.
     */
    public int activeConfiguration;

    /**
     * Constructor.
     *
     * @param id           Ship ID.
     * @param gfx          Graphic ID.
     * @param mapID        Map ID.
     * @param shipID       ships_id.
     * @param health       Health points.
     * @param nanohull     Nanohull points.
     * @param shield       Shield points.
     * @param activeConfig Active configuration.
     */
    public Ship(int id, int gfx, int mapID, int shipID, int health, int nanohull, int shield, int activeConfig)
    {
        this.id                  = id;
        this.gfx                 = gfx;
        this.mapID               = mapID;
        this.shipID              = shipID;
        this.health              = health;
        this.nanohull            = nanohull;
        this.shield              = shield;
        this.activeConfiguration = activeConfig;
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

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Ship clone()
    {
        try {
            Ship s = (Ship)super.clone();

            s.setMap(this.map.clone());
            s.setShip(this.ship.clone());
            s.setPosition(new Point(this.position.getX(), this.position.getY()));

            return s;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone ship!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
