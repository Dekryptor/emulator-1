package com.manulaiko.blackeye.simulator.map.portal;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Portal class.
 *
 * Represents a jump portal on map.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Portal implements Cloneable
{
    /**
     * Portal id.
     *
     * @var ID.
     */
    public int id = 0;

    /**
     * Portal map.
     *
     * @var Map ID.
     */
    public int mapsID = 0;

    /**
     * Required level to use this portal.
     *
     * @var Level ID.
     */
    public int level = 1;

    /**
     * Position of the portal.
     *
     * @var Position.
     */
    public Point position = new Point(0, 0);

    /**
     * Position to spawn user when this portal is used.
     *
     * @var Target position.
     */
    public Point targetPosition = new Point(0, 0);

    /**
     * Map on which user should be spawned when this portal is used.
     *
     * @var Target map.
     */
    public int targetMapsID = 1;

    /**
     * Whether portal is visible on mini-map or not.
     *
     * @var Visibility status.
     */
    public boolean isVisible = true;

    /**
     * Whether portal is working or not.
     *
     * @var Working status.
     */
    public boolean isWorking = true;

    /**
     * Faction scrap.
     *
     * @var Faction graphic.
     */
    public int factionScrap = 0;

    /**
     * Portal design.
     *
     * @var Graphic.
     */
    public int gfx = 1;

    /**
     * Constructor.
     *
     * @param id             Portal id.
     * @param mapsID         Map id.
     * @param level          Required level to use the portal.
     * @param position       Position.
     * @param targetPosition Position where user will be spawned when using the portal.
     * @param targetMapsID   Map where user will be spawned when using the portal.
     * @param isVisible      Show portal on map.
     * @param isWorking      Works.
     * @param factionScrap   Faction scrap.
     * @param gfx            Graphic.
     */
    public Portal(
            int id, int mapsID, int level, Point position, Point targetPosition,
            int targetMapsID, boolean isVisible, boolean isWorking, int factionScrap, int gfx
    ) {
        this.id             = id;
        this.mapsID         = mapsID;
        this.level          = level;
        this.position       = position;
        this.targetPosition = targetPosition;
        this.targetMapsID   = targetMapsID;
        this.isVisible      = isVisible;
        this.isWorking      = isWorking;
        this.factionScrap   = factionScrap;
        this.gfx            = gfx;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Portal clone()
    {
        try {
            Portal p = (Portal) super.clone();

            p.position       = new Point(this.position.getX(), this.position.getY());
            p.targetPosition = new Point(this.targetPosition.getX(), this.targetPosition.getY());

            return p;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone portal!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
