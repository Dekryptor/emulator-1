package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.util.HashMap;

import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;

import com.manulaiko.tabitha.utils.Point;

/**
 * Ship class
 *
 * @author Manulaiko <manulaiko@gmail.com>
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
     * Ship's active configuration
     */
    public int activeConfiguration;

    /**
     * Whether ship is cloaked or not
     */
    public boolean isCloaked = false;

    /**
     * Whether the ship is moving or not
     */
    public boolean isMoving = false;

    /**
     * Whether the ship is in a demilitarized zone or not
     */
    public boolean isInDMZ = false;

    /**
     * Whether the ship can be equipped or not
     */
    public boolean canEquip = false;

    /**
     * Destination position
     */
    public Point newPosition;

    /**
     * Flight time
     */
    public long time;

    /**
     * Flight end time
     */
    public long endTime;

    /**
     * Near accounts
     */
    public HashMap<Integer, Account> nearAccounts = new HashMap<>();

    /**
     * Near NPCs
     */
    public HashMap<Integer, NPC> nearNPCs = new HashMap<>();

    /**
     * Near collectables
     */
    public HashMap<Integer, Collectable> nearCollectables = new HashMap<>();

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
     * @param activeConfig Active configuration
     */
    public Ship(int id, int gfx, int mapID, int shipID, String positionJSON, int health, int nanohull, int shield, int activeConfig)
    {
        this.id                  = id;
        this.gfx                 = gfx;
        this.mapID               = mapID;
        this.shipID              = shipID;
        this.positionJSON        = positionJSON;
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
     * Updates the ship
     */
    public void update()
    {
        if(!this.isMoving) {
            return;
        }

        long timeLeft = this.endTime - System.currentTimeMillis();

        if(timeLeft > 0) {
            Point distance = new Point(
                    this.position.getX() - this.newPosition.getX(),
                    this.position.getY() - this.newPosition.getY()
            );

            this.position = new Point(
                    (int)((distance.getX() / time) * timeLeft),
                    (int)((distance.getY() / time) * timeLeft)
            );
        } else {
            com.manulaiko.tabitha.Console.println("Ship arrived destination!");
            this.position = this.newPosition;
            this.isMoving = false;
        }
    }
}
