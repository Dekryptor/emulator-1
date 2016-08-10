package com.manulaiko.blackeye.simulator.station;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.packets.commands.CreateStation;

import com.manulaiko.tabitha.utils.Point;

/**
 * Station class
 *
 * Represents a station on the map.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator
 */
public class Station
{
    /**
     * Position on map
     */
    public Point position = new Point(0, 0);

    /**
     * Station owner
     */
    public int factionsID = -1;

    /**
     * Faction type
     */
    public int type = 1;

    /**
     * Faction name
     */
    public String name = "redStation";

    /**
     * Constructor
     *
     * @param factionsID Station's owner
     * @param position   Position on map
     * @param type       Station's type
     * @param name       Station's name
     */
    public Station(int factionsID, Point position, int type, String name)
    {
        this.position   = position;
        this.factionsID = factionsID;
        this.type       = type;
        this.name       = name;
    }

    /**
     * Builds and returns CreateStation packet
     *
     * @return Create station packet
     */
    public CreateStation getCreateStationCommand()
    {
        CreateStation p = (CreateStation) ServerManager.game.packetFactory.getCommandByName("CreateStation");

        p.id      = 0;
        p.name    = this.name;
        p.type    = this.type;
        p.faction = this.factionsID;
        p.isDMZ   = true;
        p.x       = this.position.getX();
        p.y       = this.position.getY();

        return p;
    }
}
