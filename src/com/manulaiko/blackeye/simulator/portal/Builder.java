package com.manulaiko.blackeye.simulator.portal;

import java.awt.Point;
import java.sql.ResultSet;

import org.json.JSONArray;

import com.manulaiko.tabitha.Console;

/**
 * Portal builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.portal
 */
public class Builder
{
    /**
     * Portal object
     *
     * The current portal we're building
     */
    private Portal _portal;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            JSONArray p  = new JSONArray(rs.getString("position"));
            JSONArray tp = new JSONArray(rs.getString("target_position"));
            
            Point position       = new Point(p.getInt(0), p.getInt(1));
            Point targetPosition = new Point(tp.getInt(0), tp.getInt(1));
            
            this._portal = new Portal(
                    rs.getInt("id"),
                    rs.getInt("maps_id"),
                    rs.getInt("levels_id"),
                    position,
                    targetPosition,
                    rs.getInt("target_maps_id"),
                    rs.getBoolean("is_visible"),
                    rs.getBoolean("is_working"),
                    rs.getInt("faction_scrap"),
                    rs.getInt("gfx")
            );
        } catch(Exception e) {
            Console.println("Couldn't build portal!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a portal
     *
     * @para portal Portal to clone
     */
    public Builder(Portal portal)
    {
        Portal p = new Portal(
                portal.id,
                portal.mapsID,
                portal.level,
                portal.position,
                portal.targetPosition,
                portal.targetMapsID,
                portal.isVisible,
                portal.isWorking,
                portal.factionScrap,
                portal.gfx
        );

        this._portal = p;
    }
    
    /**
     * Returns the portal
     *
     * @return The portal
     */
    public Portal getPortal()
    {
        return this._portal;
    }
}
