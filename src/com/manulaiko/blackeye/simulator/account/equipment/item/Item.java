package com.manulaiko.blackeye.simulator.account.equipment.item.ship;

/**
 * Item class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.equipment.ship
 */
public class Item
{
    /**
     * Item ID
     */
    public int id;

    /**
     * Item object
     */
    public com.manulaiko.blackeye.simulator.item.Item item;

    /**
     * Item level
     */
    public int level;

    /**
     * Item amount
     */
    public int amount;

    /**
     * Constructor
     *
     * @param id     Item id
     * @param level  Item level
     * @param amount Item amount
     */
    public Item(int id, int level, int amount)
    {
        this.id     = id;
        this.level  = level;
        this.amount = amount;
    }
}
