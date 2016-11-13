package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.util.HashMap;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.Map;

import com.manulaiko.blackeye.simulator.map.collectable.Collectable;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.utils.Updatable;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;

/**
 * Ship class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Ship extends Simulator implements Cloneable, Updatable
{
    /**
     * Instances and returns a new Simulator.
     *
     * @return New Simulator object.
     */
    public static Ship create()
    {
        Ship s          = new Ship(0, 0, 0, 0, 0, 0, 0, 0);
        s._isInsert     = true;
        s.databaseTable = "accounts_equipment_ships";

        return s;
    }

    /**
     * Ship ID.
     *
     * @var Ship ID.
     */
    public int id;

    /**
     * Ship object.
     *
     * @var Actual ship.
     */
    public com.manulaiko.blackeye.simulator.ship.Ship ship;

    /**
     * ships_id.
     *
     * @var ID of `ships` table.
     */
    public int shipID;

    /**
     * Graphic ID.
     *
     * Using this instead of `shipID` allows us to have a different
     * graphic for the same ship (aka WIZ-X).
     *
     * @var Graphic ID.
     */
    public int gfx;

    /**
     * Map object.
     *
     * Map where the ship is located.
     *
     * @var Ship's map.
     */
    public Map map;

    /**
     * Map ID.
     *
     * @var Map ID.
     */
    public int mapID;

    /**
     * Position on map.
     *
     * @var Ship position.
     */
    public Point position;

    /**
     * Health points.
     *
     * @var Health points.
     */
    public int health;

    /**
     * Nanohull points.
     *
     * @var Nanohull points.
     */
    public int nanohull;

    /**
     * Shield points.
     *
     * @var Shield points.
     */
    public int shield;

    /**
     * Ship's active configuration.
     *
     * @var Active configuration.
     */
    public int activeConfiguration;

    /**
     * Near accounts.
     *
     * @var Accounts.
     */
    public HashMap<Integer, Account> nearAccounts = new HashMap<>();

    /**
     * Near NPCs.
     *
     * @var NPCs.
     */
    public HashMap<Integer, NPC> nearNPCs = new HashMap<>();

    /**
     * Near collectables.
     *
     * @var Collectables.
     */
    public HashMap<Integer, Collectable> nearCollectables = new HashMap<>();

    /**
     * Destination position.
     *
     * @var Destination position.
     */
    public Point newPosition;

    /**
     * Original position before moving.
     *
     * @var Old position.
     */
    public Point oldPosition;

    /**
     * Whether the ship is moving or not.
     *
     * @var Ship movement flag.
     */
    public boolean isMoving;

    /**
     * Flight start time.
     *
     * @var Start time.
     */
    public long time;

    /**
     * Flight end time.
     *
     * @var End time.
     */
    public long endTime;

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
     * Sets position object.
     *
     * @param position Point position.
     */
    public void setPosition(Point position)
    {
        this.position = position;
    }

    /**
     * Sets ship object.
     *
     * @param ship Ship object.
     */
    public void setShip(com.manulaiko.blackeye.simulator.ship.Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Sets map object.
     *
     * @param map Map object.
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

    /**
     * Returns table identifier.
     *
     * @return Table identifier.
     */
    protected int _getDatabaseIdentifier()
    {
        return this.id;
    }

    /**
     * Sets database ID.
     *
     * @param id Database ID.
     */
    protected void _setDatabaseIdentifier(int id)
    {
        this.id = id;
    }

    /**
     * Returns table fields.
     *
     * @return Table fields.
     */
    protected HashMap<String, Object> _getDatabaseFields()
    {
        HashMap<String, Object> fields = new HashMap<>();

        JSONArray position = new JSONArray();
        position.put(this.position.getX());
        position.put(this.position.getY());

        fields.put("ships_id", this.shipID);
        fields.put("maps_id", this.mapID);
        fields.put("position", position);
        fields.put("health", this.health);
        fields.put("nanohull", this.nanohull);
        fields.put("shield", this.shield);
        fields.put("active_configuration", this.activeConfiguration);

        return fields;
    }

    /**
     * Updates ship movement.
     */
    public void update()
    {
        if(!this.isMoving) {
            return;
        }

        long timeLeft = this.endTime - System.currentTimeMillis();

        if(
            timeLeft > 0 &&
            !this.position.toString().equalsIgnoreCase(this.newPosition.toString())
        ) {
            Point distanceLeft = new Point(
                    (int)(((double)this.oldPosition.getX() / this.time) * timeLeft),
                    (int)(((double)this.oldPosition.getY() / this.time) * timeLeft)
            );

            this.position = new Point(
                    this.newPosition.getX() - distanceLeft.getX(),
                    this.newPosition.getY() - distanceLeft.getY()
            );
        } else {
            this.position = this.newPosition;
            this.isMoving = false;
            this.time     = 0;
            this.endTime  = 0;

            GameManager.updaterManager.unsubscribe(this);
        }
    }
}
