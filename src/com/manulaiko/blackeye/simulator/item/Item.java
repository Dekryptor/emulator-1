package com.manulaiko.blackeye.simulator.item;

import com.manulaiko.tabitha.Console;
import org.json.JSONObject;

/**
 * Item class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Item implements Cloneable
{
    /**
     * Item ID.
     *
     * @var ID.
     */
    public int id;

    /**
     * Loot ID.
     *
     * @var Loot ID.
     */
    public String lootID;

    /**
     * Item name.
     *
     * @var Name.
     */
    public String name;

    /**
     * Category.
     *
     * @var Category.
     */
    public String category;

    /**
     * Price.
     *
     * @var Item price.
     */
    public int price;

    /**
     * Whether item is elite or not.
     *
     * @var Elite status.
     */
    public boolean isElite;

    /**
     * Value of the item
     *
     * Lasers = damage
     * Speed generators = speed
     * ...
     *
     * @var Item value.
     */
    public int value;

    /**
     * Extra values of the item.
     *
     * Shield generators = shield absorption.
     *
     * @var Extra properties.
     */
    public JSONObject extras;

    /**
     * Constructor
     *
     * @param id       ID
     * @param lootID   loot id
     * @param name     Item name
     * @param category Category
     * @param price    Price
     * @param isElite  Whether item is elite or not
     * @param value    Item value
     * @param extras   Item's extra values
     */
    public Item(int id, String lootID, String name, String category, int price, boolean isElite, int value, JSONObject extras)
    {
        this.id       = id;
        this.lootID   = lootID;
        this.name     = name;
        this.category = category;
        this.price    = price;
        this.isElite  = isElite;
        this.value    = value;
        this.extras   = extras;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Item clone()
    {
        try {
            Item i = (Item)super.clone();

            i.extras = new JSONObject(this.extras.toString());

            return i;
        } catch(Exception e) {
            Console.println("Couldn't clone item!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
