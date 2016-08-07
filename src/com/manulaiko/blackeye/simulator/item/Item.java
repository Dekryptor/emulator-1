package com.manulaiko.blackeye.simulator.item;

/**
 * Item class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.item
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
     * Filter
     */
    public int filter;

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
     * Constructor
     *
     * @param id       ID
     * @param lootID   loot id
     * @param name     Item name
     * @param category Category
     * @param filter   Item filter
     * @param price    Price
     * @param isElite  Whether item is elite or not
     * @param value    Item value
     */
    public Item(int id, String lootID, String name, String category, int filter, int price, boolean isElite, int value)
    {
        this.id       = id;
        this.lootID   = lootID;
        this.name     = name;
        this.category = category;
        this.filter   = filter;
        this.price    = price;
        this.isElite  = isElite;
        this.value    = value;
    }
}
