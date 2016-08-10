package com.manulaiko.blackeye.simulator.item;

import org.json.JSONException;
import org.json.JSONObject;

import com.manulaiko.tabitha.Console;

/**
 * Item class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Item
{
    /**
     * Item ID
     */
    public int id;

    /**
     * Loot ID
     */
    public String lootID;

    /**
     * Item name
     */
    public String name;

    /**
     * Category
     */
    public String category;

    /**
     * Price
     */
    public int price;

    /**
     * Whether item is elite or not
     */
    public boolean isElite;

    /**
     * Value of the item
     *
     * Lasers = damage
     * Speed generators = speed
     * ...
     */
    public int value;

    /**
     * Extra values of the item
     *
     * Shield generators = shield absorption
     * ...
     */
    public JSONObject extras;

    /**
     * Extra values of the item
     *
     * Shield generators = shield absorption
     * ...
     */
    public String extrasJSON;

    /**
     * Constructor
     *
     * @param id         ID
     * @param lootID     loot id
     * @param name       Item name
     * @param category   Category
     * @param price      Price
     * @param isElite    Whether item is elite or not
     * @param value      Item value
     * @param extrasJSON Item's extra values
     */
    public Item(int id, String lootID, String name, String category, int price, boolean isElite, int value, String extrasJSON)
    {
        this.id         = id;
        this.lootID     = lootID;
        this.name       = name;
        this.category   = category;
        this.price      = price;
        this.isElite    = isElite;
        this.value      = value;
        this.extrasJSON = extrasJSON;

        try {
            this.extras = new JSONObject(extrasJSON);
        } catch(JSONException e) {
            Console.println("Couldn't create item!");
            Console.println(e.getMessage());
        }
    }
}
