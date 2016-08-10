package com.manulaiko.blackeye.simulator.account.equipment.item;

import java.util.HashMap;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Ship factory
 *
 * Used for instance ship objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced hangars
     */
    private HashMap<Integer, Item> _items = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given item
     *
     * @param id Item id
     *
     * @return The item
     *
     * @throws NotFound If item doesn't exist
     */
    public Item getByID(int id) throws com.manulaiko.tabitha.exceptions.NotFound
    {
        if(!this._items.containsKey(id)) {
            Item s = this.loadByID(id);

            this._items.put(id, s);

            return s;
        }

        return this._items.get(id);
    }

    /**
     * Returns all ships
     *
     * @return All ships
     */
    public HashMap<Integer, Item> getAllItems()
    {
        return this._items;
    }

    /**
     * Returns account's items
     *
     * @param id Account's id
     *
     * @return Account's items
     */
    public HashMap<Integer, Item> getByAccountID(int id)
    {
        HashMap<Integer, Item> items = new HashMap<>();

        this._items.forEach((key, value) -> {
            if(value.accountID == id) {
                items.put(key, value);
            }
        });

        return items;
    }

    /**
     * Returns the amount of loaded ships
     *
     * @return Amount of loaded ships
     */
    public int getAmount()
    {
        return this._items.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a ship by its ID
     *
     * @param id Ship id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist in database
     */
    public Item loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts_equipment_items` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getItem();
            } else {
                throw new NotFound("item", "id: " + id);
            }
        } catch(SQLException e) {
            throw new NotFound("item", "id: " + id);
        }
    }

    /**
     * Loads all ships from database
     */
    public void loadAll()
    {
        try {
            ResultSet result = Main.mysqlManager.query("SELECT * FROM `accounts_equipment_items`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Item i = builder.getItem();

                this._items.put(i.id, i);
            }
        } catch(Exception e) {
            Console.println("Couldn't load items!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
