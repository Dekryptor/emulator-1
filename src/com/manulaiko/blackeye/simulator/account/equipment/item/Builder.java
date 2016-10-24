package com.manulaiko.blackeye.simulator.account.equipment.item;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Item builder.
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
     * Builds an item.
     */
    public void build() throws Exception
    {
        this._object = new Item(
                this._result.getInt("id"),
                this._result.getInt("accounts_id"),
                this._result.getInt("items_id"),
                this._result.getInt("levels_id"),
                this._result.getInt("amount")
        );

        this._setLevel(this._result.getInt("levels_id"));
        this._setItem(this._result.getInt("items_id"));
    }

    /**
     * Sets items's level.
     *
     * @param id Level ID.
     *
     * @throws Exception If level ID does not exist or build failed.
     */
    private void _setLevel(int id) throws Exception
    {
        Level l = (Level)GameManager.levels.getByID(id);

        ((Item)this._object).setLevel(l);
    }

    /**
     * Sets items's item.
     *
     * @param id Item ID.
     *
     * @throws Exception If item ID does not exist or build failed.
     */
    private void _setItem(int id) throws Exception
    {
        com.manulaiko.blackeye.simulator.item.Item i = (com.manulaiko.blackeye.simulator.item.Item)GameManager.items.getByID(id);

        ((Item)this._object).setItem(i);
    }
}
