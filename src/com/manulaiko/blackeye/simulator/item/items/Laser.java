package com.manulaiko.blackeye.simulator.item.items;

/**
 * Laser class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.item.items
 */
public class Laser extends com.manulaiko.blackeye.simulator.item.Item
{
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
    public Laser(int id, String lootID, String name, String category, int filter, int price, boolean isElite, int value)
    {
        super(id, lootID, name, category, filter, price, isElite, value);
    }
}
