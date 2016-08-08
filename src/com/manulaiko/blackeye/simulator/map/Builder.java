package com.manulaiko.blackeye.simulator.map;

import java.awt.Point;
import java.sql.ResultSet;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.collectable.Collectable;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.station.Station;
import com.manulaiko.blackeye.simulator.portal.Portal;

import com.manulaiko.tabitha.Console;

/**
 * Map builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.map
 */
public class Builder
{
    /**
     * Map object
     *
     * The current map we're building
     */
    private Map _map;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            JSONArray limitsJSON   = new JSONArray(rs.getString("limits"));
            JSONArray npcs         = new JSONArray(rs.getString("npcs"));
            JSONArray stations     = new JSONArray(rs.getString("stations"));
            JSONArray collectables = new JSONArray(rs.getString("collectables"));

            Point limits = new Point(limitsJSON.getInt(0), limitsJSON.getInt(1));


            this._map = new Map(
                    rs.getInt("id"),
                    rs.getInt("factions_id"),
                    rs.getBoolean("is_pvp"),
                    rs.getBoolean("is_starter"),
                    rs.getString("name"),
                    limits,
                    npcs,
                    stations,
                    collectables
            );

            this._build(rs.getInt("id"), limits, npcs, stations, collectables);
        } catch(Exception e) {
            Console.println("Couldn't build map!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a map
     *
     * @para map Map to clone
     */
    public Builder(Map map)
    {
        try {
            Map m = new Map(
                    map.id,
                    map.factionsID,
                    map.isPVP,
                    map.isStarter,
                    map.name,

                    /*
                     * The following parameters wont be cloned
                     *
                     * But as it's very rare that they will be modified
                     * I won't waste time in cloning them so fuck off
                     *
                     * If you wonder why the fuck when you change something of this
                     * objects in a object it changes in all cloned objects then
                     * you should stop, sit down and meditate about life and it's meaning
                     * because what you're trying to do is probably something bad.
                     *
                     * So, if you're reading this, either you're bored as fuck or
                     * you are facing this problem. It's not a bug, it's a feature so
                     * unless you don't want to change too much classes, you better give up.
                     *
                     * Have a nice day :)
                     *
                     * -Manulaiko
                     */
                    map.limits,
                    map.npcsJSON,
                    map.stationsJSON,
                    map.collectablesJSON
            );

            this._build(m.id, m.limits, m.npcsJSON, m.stationsJSON, m.collectablesJSON);
        } catch(Exception e) {
            Console.println("Couldn't clone map!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds a map
     *
     * @param id           Map id
     * @param limits       limits
     * @param npcs         NPCs JSON
     * @param stations     Stations JSON
     * @param collectables Collectables JSON
     *
     * @return The map
     */
    private void _build(int id, Point limits, JSONArray npcs, JSONArray stations, JSONArray collectables)
    {
        try {
            //Load NPCs
            if(Main.configuration.getBoolean("maps.load_npcs")) {
                //Loop through all kind of NPCs there are in map
                for(int i = 0; i < npcs.length(); i++) {
                    JSONObject npc = npcs.getJSONObject(i);

                    //Loop through the amount of NPCs to spawn
                    for(int j = 0; j < npc.getInt("amount"); j++) {
                        NPC n = GameManager.npcs.cloneByID(npc.getInt("npcs_id"));

                        //Create random position
                        n.position = new Point(
                                ThreadLocalRandom.current()
                                                 .nextInt(0, (int) limits.getX() + 1),
                                ThreadLocalRandom.current()
                                                 .nextInt(0, (int) limits.getY() + 1)
                        );

                        this.addNPC(n);
                    }
                }
            }

            //Load portals
            if(Main.configuration.getBoolean("maps.load_portals")) {
                GameManager.portals.getByMapID(id).forEach((Integer i, Portal p) -> {
                    this.addPortal(p);
                });
            }

            //Load stations
            if(Main.configuration.getBoolean("maps.load_stations")) {
                //Loop through all available stations
                for(int i = 0; i < stations.length(); i++) {
                    JSONObject station = stations.getJSONObject(i);
                    JSONArray position = station.getJSONArray("position");

                    this.addStation(new Station(
                            station.getInt("factions_id"),
                            new Point(
                                    position.getInt(0),
                                    position.getInt(1)
                            ),
                            station.getInt("type"),
                            station.getString("name")
                    ));
                }
            }

            //Load collectables
            if(Main.configuration.getBoolean("maps.load_collectables")) {
                //Load all available collectables
                for(int i = 0; i < collectables.length(); i++) {
                    JSONObject collectable = collectables.getJSONObject(i);

                    JSONArray topLeft = collectable.getJSONArray("topLeft");
                    JSONArray bottomRight = collectable.getJSONArray("bottom");

                    for(int j = 0; j < collectable.getInt("amount"); j++) {
                        //Build random position based on topLeft and bottomRight
                        Point position = new Point(
                                ThreadLocalRandom.current()
                                                 .nextInt(
                                                         topLeft.getInt(0),
                                                         bottomRight.getInt(0) + 1
                                                 ),
                                ThreadLocalRandom.current()
                                                 .nextInt(
                                                         topLeft.getInt(1),
                                                         bottomRight.getInt(1) + 1
                                                 )
                        );

                        Collectable c = GameManager.collectables.cloneByID(collectable.getInt("collectables_id"));
                        c.position = position;

                        this.addCollectable(c);
                    }
                }
            }
        } catch(Exception e) {
            Console.println("Couldn't build map!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the map
     *
     * @return The map
     */
    public Map getMap()
    {
        return this._map;
    }

    /**
     * Adds a NPC to the map
     *
     * @param npc NPC to add
     */
    public void addNPC(NPC npc)
    {
        this._map.addNPC(npc);
    }

    /**
     * Adds a collectable to the map
     *
     * @param collectable Collectable to add
     */
    public void addCollectable(Collectable collectable)
    {
        this._map.addCollectable(collectable);
    }

    /**
     * Adds a station to the map
     *
     * @param station Station to add
     */
    public void addStation(Station station)
    {
        this._map.addStation(station);
    }

    /**
     * Adds a portal to the map
     *
     * @param portal Portal to add
     */
    public void addPortal(Portal portal)
    {
        this._map.addPortal(portal);
    }
}
