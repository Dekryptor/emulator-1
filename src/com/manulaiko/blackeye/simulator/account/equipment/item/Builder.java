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
     * Builds an account.
     */
    public void build()
    {
        try {
            this._object = new Item(
                    this._result.getInt("id"),
                    this._result.getInt("accounts_id"),
                    this._result.getInt("items_id"),
                    this._result.getInt("levels_id"),
                    this._result.getInt("amount")
            );

            this._setLevel(this._result.getInt("levels_id"));
            this._setItem(this._result.getInt("items_id"));
        } catch(Exception e) {
            Console.println("Couldn't build item!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets items's level.
     *
     * @param id Level ID.
     *
     * @throws NotFound If level ID does not exist.
     */
    private void _setLevel(int id) throws NotFound
    {
        Level l = (Level)GameManager.levels.getByID(id);

        ((Item)this._object).setLevel(l);
    }

    /**
     * Sets items's item.
     *
     * @param id Item ID.
     *
     * @throws NotFound If item ID does not exist.
     */
    private void _setItem(int id) throws NotFound
    {
        com.manulaiko.blackeye.simulator.item.Item i = (com.manulaiko.blackeye.simulator.item.Item)GameManager.items.getByID(id);

        ((Item)this._object).setItem(i);
    }
}
