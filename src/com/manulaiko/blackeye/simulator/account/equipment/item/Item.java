package com.manulaiko.blackeye.simulator.account.equipment.item;

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
     * Account's ID
     */
    public int accountID;

    /**
     * Item's ID
     */
    public int itemID;

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
     * @param id        Item id
     * @param accountID Item's account ID
     * @param itemID    Item actual id
     * @param level     Item level
     * @param amount    Item amount
     */
    public Item(int id, int accountID, int itemID, int level, int amount)
    {
        this.id        = id;
        this.accountID = accountID;
        this.itemID    = itemID;
        this.level     = level;
        this.amount    = amount;
    }

    /**
     * Sets item object
     *
     * @param item Item object
     */
    public void setItem(com.manulaiko.blackeye.simulator.item.Item item)
    {
        this.item = item;
    }
}
