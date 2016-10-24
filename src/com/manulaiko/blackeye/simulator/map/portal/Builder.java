package com.manulaiko.blackeye.simulator.map.portal;

import java.sql.ResultSet;
import java.util.concurrent.ThreadLocalRandom;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;
import com.manulaiko.blackeye.simulator.map.station.Station;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Portal builder.
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
     * Builds a portal.
     */
    public void build() throws Exception
    {
        JSONArray p  = new JSONArray(this._result.getString("position"));
        JSONArray tp = new JSONArray(this._result.getString("target_position"));

        Point position       = new Point(p.getInt(0), p.getInt(1));
        Point targetPosition = new Point(tp.getInt(0), tp.getInt(1));

        this._object = new Portal(
                this._result.getInt("id"),
                this._result.getInt("maps_id"),
                this._result.getInt("levels_id"),
                position,
                targetPosition,
                this._result.getInt("target_maps_id"),
                this._result.getBoolean("is_visible"),
                this._result.getBoolean("is_working"),
                this._result.getInt("faction_scrap"),
                this._result.getInt("gfx")
        );
    }
}
