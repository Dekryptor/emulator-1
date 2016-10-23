package com.manulaiko.blackeye.simulator.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.blackeye.simulator.map.station.Station;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Map class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Map implements Cloneable
{
    /**
     * Map ID.
     *
     * @var ID.
     */
    public int id = 0;

    /**
     * Map name.
     *
     * @var Name.
     */
    public String name = "";

    /**
     * Map size.
     *
     * @var Map size.
     */
    public Point limits = new Point(0, 0);

    /**
     * Map portals.
     *
     * @var Portals on map.
     */
    public HashMap<Integer, Portal> portals = new HashMap<>();

    /**
     * NPCS.
     *
     * @var NPCS on map.
     */
    public HashMap<Integer, NPC> npcs = new HashMap<>();

    /**
     * Collectables.
     *
     * @var Collectables on map.
     */
    public HashMap<Integer, Collectable> collectables = new HashMap<>();

    /**
     * Stations.
     *
     * @var Stations on map.
     */
    public ArrayList<Station> stations = new ArrayList<>();

    /**
     * Whether map is pvp or not.
     *
     * @var PVP status.
     */
    public boolean isPVP = false;

    /**
     * Whether map is starter map.
     *
     * Used to show where are the enemies in the minimap.
     *
     * @var Starter status.
     */
    public boolean isStarter = false;

    /**
     * Map's owner.
     *
     * @var Owner's ID.
     */
    public int factionsID = -1;

    /**
     * Constructor.
     *
     * @param id         Map id.
     * @param factionsID Map's owner faction.
     * @param isPVP      Whether map is a pvp map or not.
     * @param isStarter  Whether map is a starter map or not.
     * @param name       Map name.
     * @param limits     Map limits.
     */
    public Map(int id, int factionsID, boolean isPVP, boolean isStarter, String name, Point limits)
    {
        this.id         = id;
        this.factionsID = factionsID;
        this.isPVP      = isPVP;
        this.isStarter  = isStarter;
        this.name       = name;
        this.limits     = limits;
    }

    /**
     * Adds a NPC to the array.
     *
     * @param npc NPC to add.
     */
    public void addNPC(NPC npc)
    {
        int id = -this.npcs.size();

        if(id <= -2147483647) {
            id = 0;
        }

        npc.id = id;

        this.npcs.put(id, npc);
    }

    /**
     * Adds a portal to the array.
     *
     * @param portal Portal to add.
     */
    public void addPortal(Portal portal)
    {
        this.portals.put(portal.id, portal);
    }

    /**
     * Adds a collectable to the array.
     *
     * @param collectable Collectable to add.
     */
    public void addCollectable(Collectable collectable)
    {
        int id = -this.collectables.size();

        if(id <= -2147483647) {
            id = 0;
        }

        collectable.id = id;

        this.collectables.put(id, collectable);
    }

    /**
     * Adds a station to the array.
     *
     * @param station Station to add.
     */
    public void addStation(Station station)
    {
        this.stations.add(station);
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Map clone()
    {
        try {
            Map m = (Map)super.clone();

            this.npcs.forEach((i, n)->{
                m.addNPC(n.clone());
            });
            this.stations.forEach((s)->{
                m.addStation(s.clone());
            });
            this.portals.forEach((i, p)->{
                m.addPortal(p.clone());
            });
            this.collectables.forEach((i, c)->{
                m.addCollectable(c.clone());
            });

            return m;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone map!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
