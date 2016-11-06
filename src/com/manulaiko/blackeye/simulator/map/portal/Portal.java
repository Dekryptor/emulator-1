package com.manulaiko.blackeye.simulator.map.portal;

import java.util.HashMap;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.packet.command.CreatePortal;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;

/**
 * Portal class.
 *
 * Represents a jump portal on map.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Portal extends Simulator implements Cloneable
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

    /**
     * Builds and returns the CreatePortal command.
     *
     * @return CreatePortal command.
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

        JSONArray targetPosition = new JSONArray();
        targetPosition.put(this.targetPosition.getX());
        targetPosition.put(this.targetPosition.getY());

        fields.put("maps_id", this.mapsID);
        fields.put("position", position);
        fields.put("target_position", targetPosition);
        fields.put("target_maps_id", this.targetMapsID);
        fields.put("is_visible", this.isVisible);
        fields.put("is_working" ,this.isWorking);
        fields.put("faction_scrap", this.factionScrap);
        fields.put("gfx", this.gfx);

        return fields;
    }
}
