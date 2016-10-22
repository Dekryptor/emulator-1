package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.item.Item;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Configuration builder.
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
            JSONArray hellstorms = new JSONArray(this._result.getString("hellstorms"));
            JSONArray lasers     = new JSONArray(this._result.getString("lasers"));
            JSONArray generators = new JSONArray(this._result.getString("generators"));
            JSONArray extras     = new JSONArray(this._result.getString("extras"));

            this._object = new Configuration(
                    this._result.getInt("id"),
                    this._result.getInt("accounts_equipment_ships_id"),
                    this._result.getInt("configuration")
            );

            this._setHellstorms(hellstorms);
            this._setLasers(lasers);
            this._setGenerators(generators);
            this._setExtras(extras);
        } catch(Exception e) {
            Console.println("Couldn't build item!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets configuration's hellstorms.
     *
     * @param hellstorms Hellstorms JSON.
     *
     * @throws JSONException If the JSON couldn't be parsed.
     * @throws NotFound If any of the items does not exist.
     */
    private void _setHellstorms(JSONArray hellstorms) throws JSONException, NotFound
    {
        for(int i = 0; i < hellstorms.length(); i++) {
            Item item = (Item)GameManager.items.getByID(hellstorms.getInt(i));

            ((Configuration)this._object).addHellstorm(item);
        }
    }

    /**
     * Sets configuration's lasers.
     *
     * @param lasers Lasers JSON.
     *
     * @throws JSONException If the JSON couldn't be parsed.
     * @throws NotFound If any of the items does not exist.
     */
    private void _setLasers(JSONArray lasers) throws JSONException, NotFound
    {
        for(int i = 0; i < lasers.length(); i++) {
            Item item = (Item)GameManager.items.getByID(lasers.getInt(i));

            ((Configuration)this._object).addLaser(item);
        }
    }

    /**
     * Sets configuration's generators.
     *
     * @param generators Generators JSON.
     *
     * @throws JSONException If the JSON couldn't be parsed.
     * @throws NotFound If any of the items does not exist.
     */
    private void _setGenerators(JSONArray generators) throws JSONException, NotFound
    {
        for(int i = 0; i < generators.length(); i++) {
            Item item = (Item)GameManager.items.getByID(generators.getInt(i));

            ((Configuration)this._object).addGenerator(item);
        }
    }

    /**
     * Sets configuration's extras.
     *
     * @param extras Extras JSON.
     *
     * @throws JSONException If the JSON couldn't be parsed.
     * @throws NotFound If any of the items does not exist.
     */
    private void _setExtras(JSONArray extras) throws JSONException, NotFound
    {
        for(int i = 0; i < extras.length(); i++) {
            Item item = (Item)GameManager.items.getByID(extras.getInt(i));

            ((Configuration)this._object).addExtra(item);
        }
    }
}
