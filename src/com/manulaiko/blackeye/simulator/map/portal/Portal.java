package com.manulaiko.blackeye.simulator.map.portal;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.packets.commands.CreatePortal;

import com.manulaiko.tabitha.utils.Point;

/**
 * Portal class
 *
 * Represents a jump portal on map
 *
 * Example JSON:
 *
 *     {
 *         "level": 1,
 *         "position": [
 *             18500, //X
 *             12000, //Y
 *         ],
 *         "target_position": [
 *             1000, //X
 *             1000, //Y
 *         ],
 *         "target_maps_id": 2,
 *         "is_visible": true,
 *         "is_working": true,
 *         "faction_scrap": 0, //No faction scrap
 *         "gfx": 1 //Default portal
 *     }
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Portal
{
    /**
     * Portal id
     */
    public int id = 0;

    /**
     * Portal map
     */
    public int mapsID = 0;

    /**
     * Required level to use this portal
     */
    public int level = 1;

    /**
     * Position of the portal
     */
    public Point position = new Point(0, 0);

    /**
     * Position to spawn user when this portal is used
     */
    public Point targetPosition = new Point(0, 0);

    /**
     * Map on which user should be spawned when this portal is used
     */
    public int targetMapsID = 1;

    /**
     * Whether portal is visible on minimap or not
     */
    public boolean isVisible = true;

    /**
     * Whether portal is working or not
     */
    public boolean isWorking = true;

    /**
     * Faction scrap
     */
    public int factionScrap = 0;

    /**
     * Portal design
     */
    public int gfx = 1;

    /**
     * Constructor
     *
     * @param id             Portal id
     * @param mapsID         Map id
     * @param level          Required level to use the portal
     * @param position       Position
     * @param targetPosition Position where user will be spawned when using the portal
     * @param targetMapsID   Map where user will be spawned when using the portal
     * @param isVisible      Show portal on map
     * @param isWorking      Works
     * @param factionScrap   Faction scrap
     * @param gfx            Graphic
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
     * Builds and returns CreatePortal packet
     *
     * @return Create portal packet
     */
    public CreatePortal getCreatePortalCommand()
    {
        CreatePortal p = (CreatePortal) ServerManager.game.packetFactory.getCommandByName("CreatePortal");

        p.id           = this.id;
        p.gfx          = this.gfx;
        p.x            = this.position.getX();
        p.y            = this.position.getY();
        p.isVisible    = this.isVisible;
        p.factionScrap = this.factionScrap;

        return p;
    }
}
