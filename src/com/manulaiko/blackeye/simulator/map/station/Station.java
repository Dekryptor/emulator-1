package com.manulaiko.blackeye.simulator.map.station;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Station class.
 *
 * Represents a station on the map.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Station implements Cloneable
{
    /**
     * Position on map.
     */
    public Point position = new Point(0, 0);

    /**
     * Station owner.
     */
    public int factionsID = -1;

    /**
     * Faction type.
     */
    public int type = 1;

    /**
     * Faction name.
     */
    public String name = "redStation";

    /**
     * Constructor.
     *
     * @param factionsID Station's owner.
     * @param position   Position on map.
     * @param type       Station's type.
     * @param name       Station's name.
     */
    public Station(int factionsID, Point position, int type, String name)
    {
        this.position   = position;
        this.factionsID = factionsID;
        this.type       = type;
        this.name       = name;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Station clone()
    {
        try {
            Station s  = (Station) super.clone();
            s.position = new Point(this.position.getX(), this.position.getY());

            return s;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone station!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
