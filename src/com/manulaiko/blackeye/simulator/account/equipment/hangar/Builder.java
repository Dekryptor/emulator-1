package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.sql.ResultSet;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.configuration.Configuration;
import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Hangar builder.
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
     * Builds an hangar.
     */
    public void build() throws Exception
    {
        JSONArray resources = new JSONArray(this._result.getString("resources"));

        this._object = new Hangar(
                this._result.getInt("id"),
                this._result.getInt("accounts__id")
        );

        this._setResources(resources);
        this._setShip(this._result.getInt("accounts_equipment_ships_id"));
        this._setConfigurations(this._result.getInt("accounts_equipment_ships_id"));
    }

    /**
     * Sets hangar's resources.
     *
     * @param resources Resources JSON.
     *
     * @throws JSONException If the JSON couldn't be parsed.
     */
    private void _setResources(JSONArray resources) throws JSONException
    {
        HashMap<Integer, Integer> res = new HashMap<>();
        for(int i = 0; i < resources.length(); i++) {
            res.put(i, resources.getInt(i));
        }

        ((Hangar)this._object).setResources(res);
    }

    /**
     * Sets hangar's ship.
     *
     * @param id Ship ID.
     *
     * @throws Exception If ship does not exist or build failed.
     */
    private void _setShip(int id) throws Exception
    {
        Ship s = (Ship)GameManager.accounts.hangars.ships.getByID(id);

        ((Hangar)this._object).setShip(s);
    }

    /**
     * Sets hangar's configurations.
     *
     * @param id Ship ID.
     *
     * @throws Exception In case build failed.
     */
    private void _setConfigurations(int id) throws Exception
    {
        HashMap<Integer, Configuration> configurations = GameManager.accounts.hangars.configurations.getByShipID(id);

        ((Hangar)this._object).setConfigurations(configurations);
    }
}
