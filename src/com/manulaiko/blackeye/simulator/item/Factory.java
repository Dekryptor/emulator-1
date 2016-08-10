package com.manulaiko.blackeye.simulator.item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Item factory
 *
 * Used for instance item objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced maps
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
    public Item getByID(int id) throws NotFound
    {
        if(!this._items.containsKey(id)) {
            Item c = this.loadByID(id);

            this._items.put(id, c);

            return c;
        }

        return this._items.get(id);
    }

    /**
     * Returns given item
     *
     * @param name Item name
     *
     * @return The item
     *
     * @throws NotFound If item doesn't exist
     */
    public Item getByName(String name) throws NotFound
    {
        for(Entry<Integer, Item> i : this._items.entrySet()) {
            if(i.getValue().name.equals(name)) {
                return i.getValue();
            }
        }

        Item i = this.loadByName(name);

        this._items.put(i.id, i);

        return i;
    }

    /**
     * Returns all items
     *
     * @return All items
     */
    public HashMap<Integer, Item> getAllItems()
    {
        return this._items;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
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
     * Builds and returns a item by its ID
     *
     * @param id Item id
     *
     * @return The item
     *
     * @throws NotFound If item doesn't exist in database
     */
    public Item loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `items` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getItem();
            } else {
                throw new NotFound("item", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("item", "id: "+ id);
        }
    }

    /**
     * Builds and returns an item by its name
     *
     * @param name Item name
     *
     * @return The Item
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Item loadByName(String name) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `items` WHERE `name`=?");
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getItem();
            } else {
                throw new NotFound("item", name);
            }
        } catch(SQLException e) {
            throw new NotFound("item", name);
        }
    }

    /**
     * Loads all items from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `items`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Item m = builder.getItem();

                this._items.put(m.id, m);
            }
        } catch(Exception e) {
            Console.println("Couldn't load item!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////

    /////////////////////////
    // Start clone methods //
    /////////////////////////
    /**
     * Clones given item
     *
     * @param item Item to clone
     *
     * @return Cloned item
     */
    public Item clone(Item item)
    {
        Builder b = new Builder(item);

        return b.getItem();
    }

    /**
     * Clones given item by ID
     *
     * @param id Item id
     *
     * @return Cloned item
     *
     * @throws NotFound If item id doesn't exit
     */
    public Item cloneByID(int id) throws NotFound
    {
        Item item = this.getByID(id);

        Builder b = new Builder(item);

        return b.getItem();
    }

    /**
     * Clones given item by name
     *
     * @param name Item name
     *
     * @return Cloned item
     *
     * @throws NotFound If item id doesn't exit
     */
    public Item cloneByName(String name) throws NotFound
    {
        Item item = this.getByName(name);

        Builder b = new Builder(item);

        return b.getItem();
    }
    ///////////////////////
    // End clone methods //
    ///////////////////////
}
