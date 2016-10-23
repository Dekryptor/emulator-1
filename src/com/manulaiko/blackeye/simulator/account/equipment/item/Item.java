package com.manulaiko.blackeye.simulator.account.equipment.item;

import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.tabitha.Console;

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
     * @var Item ID.
     */
    public int id;

    /**
     * Account's ID.
     *
     * @var Owner's ID.
     */
    public int accountID;

    /**
     * Item's ID.
     *
     * @var ID of `items` table.
     */
    public int itemID;

    /**
     * Item object.
     *
     * @var Actual item.
     */
    public com.manulaiko.blackeye.simulator.item.Item item;

    /**
     * Item level.
     *
     * @var Level ID.
     */
    public int levelID;

    /**
     * Level object.
     *
     * @var Item level.
     */
    public Level level;

    /**
     * Item amount.
     *
     * @var Amount of items.
     */
    public int amount;

    /**
     * Constructor.
     *
     * @param id        Item id.
     * @param accountID Item's account ID.
     * @param itemID    Item actual id.
     * @param levelID   Item level id.
     * @param amount    Item amount.
     */
    public Item(int id, int accountID, int itemID, int levelID, int amount)
    {
        this.id        = id;
        this.accountID = accountID;
        this.itemID    = itemID;
        this.levelID   = levelID;
        this.amount    = amount;
    }

    /**
     * Sets item object.
     *
     * @param item Item object.
     */
    public void setItem(com.manulaiko.blackeye.simulator.item.Item item)
    {
        this.item = item;
    }

    /**
     * Sets level object.
     *
     * @param level Level object.
     */
    public void setLevel(Level level)
    {
        this.level = level;
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

            i.setLevel(this.level.clone());
            i.setItem(this.item.clone());

            return i;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone item!");
            Console.println(e.getMessage());

            return null;
        }
    }
}
