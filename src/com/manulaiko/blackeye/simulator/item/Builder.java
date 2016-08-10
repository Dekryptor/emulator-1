package com.manulaiko.blackeye.simulator.item;

import java.sql.ResultSet;

import com.manulaiko.tabitha.Console;

/**
 * Item builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.item
 */
public class Builder
{
    /**
     * Item object
     *
     * The current item we're building
     */
    private Item _item;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._item = new Item(
                    rs.getInt("id"),
                    rs.getString("loot_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getBoolean("is_elite"),
                    rs.getInt("value"),
                    rs.getString("extras")
            );
        } catch(Exception e) {
            Console.println("Couldn't build item!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a item
     *
     * @para item Item to clone
     */
    public Builder(Item item)
    {
        try {
            this._item = new Item(
                    item.id,
                    item.lootID,
                    item.name,
                    item.category,
                    item.price,
                    item.isElite,
                    item.value,
                    item.extrasJSON
            );
        } catch(Exception e) {
            Console.println("Couldn't clone item!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns the item
     *
     * @return The item
     */
    public Item getItem()
    {
        return this._item;
    }
}
