package com.manulaiko.blackeye.simulator.account.equipment.item;

import com.manulaiko.blackeye.simulator.level.Level;

/**
 * Item class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Item
{
    /**
     * Item ID.
     */
    public int id;

    /**
     * Account's ID.
     */
    public int accountID;

    /**
     * Item's ID.
     */
    public int itemID;

    /**
     * Item object.
     */
    public com.manulaiko.blackeye.simulator.item.Item item;

    /**
     * Item level.
     */
    public int levelID;

    /**
     * Level object.
     */
    public Level level;

    /**
     * Item amount.
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
}
