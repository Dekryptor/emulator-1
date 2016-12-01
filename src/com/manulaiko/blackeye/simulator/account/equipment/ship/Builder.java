package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Ship builder.
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
     * Builds a ship..
     */
    public void build() throws Exception
    {
        JSONArray position = new JSONArray(this._result.getString("position"));

        this._object = new Ship(
                this._result.getInt("id"),
                this._result.getInt("gfx"),
                this._result.getInt("maps_id"),
                this._result.getInt("ships_id"),
                this._result.getInt("health"),
                this._result.getInt("nanohull"),
                this._result.getInt("shield"),
                this._result.getInt("active_configuration")
        );

        this._setMap(this._result.getInt("maps_id"));
        this._setShip(this._result.getInt("ships_id"));
        this._setPosition(position);
    }

    /**
     * Sets ship's map.
     *
     * @param id Map ID.
     *
     * @throws Exception If map ID does not exist or build failed.
     */
    private void _setMap(int id) throws Exception
    {
        Map m = (Map)GameManager.maps.getByID(id);

        ((Ship)this._object).setMap(m);
    }

    /**
     * Sets ship's ship.
     *
     * @param id Ship ID.
     *
     * @throws Exception If ship ID does not exist or build failed.
     */
    private void _setShip(int id) throws Exception
    {
        com.manulaiko.blackeye.simulator.ship.Ship s = (com.manulaiko.blackeye.simulator.ship.Ship)GameManager.ships.getByID(id);

        ((Ship)this._object).setShip(s);
    }

    /**
     * Sets ship's position.
     *
     * @param position Position JSON
     *
     * @throws JSONException If JSON array couldn't be parsed
     */
    private void _setPosition(JSONArray position) throws JSONException
    {
        ((Ship)this._object).setPosition(new Point(position.getInt(0), position.getInt(1)));
    }
}
