package com.manulaiko.blackeye.simulator.map;

import java.sql.ResultSet;
import java.util.concurrent.ThreadLocalRandom;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.blackeye.simulator.map.station.Station;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Map builder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder extends com.manulaiko.blackeye.simulator.Builder
{
    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        super(rs);
    }

    /**
     * Builds a map.
     */
    public void build() throws Exception
    {
        JSONArray stations     = new JSONArray(this._result.getString("stations"));
        JSONArray npcs         = new JSONArray(this._result.getString("npcs"));
        JSONArray collectables = new JSONArray(this._result.getString("collectables"));

        JSONArray l  = new JSONArray(this._result.getString("limits"));
        Point limits = new Point(l.getInt(0), l.getInt(1));

        this._object = new Map(
                this._result.getInt("id"),
                this._result.getInt("factions_id"),
                this._result.getBoolean("is_pvp"),
                this._result.getBoolean("is_starter"),
                this._result.getString("name"),
                limits
        );

        if(Main.configuration.getBoolean("maps.load_stations")) {
            this._setStations(stations);
        }

        if(Main.configuration.getBoolean("maps.load_npcs")) {
            this._setNPCS(npcs);
        }

        if(Main.configuration.getBoolean("maps.load_collectables")) {
            this._setCollectables(collectables);
        }

        if(Main.configuration.getBoolean("maps.load_portals")) {
            this._setPortals();
        }
    }

    /**
     * Sets map's stations.
     *
     * @param stations Stations JSON.
     *
     * @throws JSONException If the json couldn't be parsed.
     */
    private void _setStations(JSONArray stations) throws JSONException
    {
        for(int i = 0; i < stations.length(); i++) {
            JSONObject st   = stations.getJSONObject(i);

            JSONArray pos  = st.getJSONArray("position");
            Point position = new Point(pos.getInt(0), pos.getInt(1));

            Station station = new Station(
                    st.getInt("factions_id"),
                    position,
                    st.getInt("type"),
                    st.getString("name")
            );

            ((Map)this._object).addStation(station);
        }
    }

    /**
     * Sets map's NPCs.
     *
     * @param npcs NPCs JSON.
     *
     * @throws Exception If anything failed (JSON parsed, npc not found, npc building).
     */
    private void _setNPCS(JSONArray npcs) throws Exception
    {
        for(int i = 0; i < npcs.length(); i++) {
            JSONObject npc = npcs.getJSONObject(i);

            int id     = npc.getInt("npcs_id");
            int amount = npc.getInt("amount");

            for(int j = 0; j < amount; j++) {
                NPC n = ((NPC)GameManager.npcs.getByID(id)).clone();

                Point position = new Point(
                        ThreadLocalRandom.current()
                                         .nextInt(0, ((Map) this._object).limits.getX() + 1),
                        ThreadLocalRandom.current()
                                         .nextInt(0, ((Map) this._object).limits.getY() + 1)
                );

                n.position = position;
                ((Map)this._object).addNPC(n);
            }
        }
    }

    /**
     * Sets map's collectables.
     *
     * @param collectables Collectables JSON.
     *
     * @throws Exception If anything failed (JSON parsed, collectable not found, collectable building).
     */
    private void _setCollectables(JSONArray collectables) throws Exception
    {
        for(int i = 0; i < collectables.length(); i++) {
            JSONObject collectable = collectables.getJSONObject(i);

            JSONArray topLeft     = collectable.getJSONArray("topLeft");
            JSONArray bottomRight = collectable.getJSONArray("bottomRight");
            Collectable c         = ((Collectable)GameManager.collectables.getByID(collectable.getInt("collectables_id"))).clone();

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

            c.position = position;
            ((Map)this._object).addCollectable(c);
        }
    }

    /**
     * Sets map's portals.
     *
     * @throws Exception In case build failed.
     */
    private void _setPortals() throws Exception
    {
        int id = ((Map)this._object).id;

        GameManager.portals.getByMapID(id).forEach((Integer i, Portal p) -> {
            ((Map)this._object).addPortal(p);
        });
    }
}
