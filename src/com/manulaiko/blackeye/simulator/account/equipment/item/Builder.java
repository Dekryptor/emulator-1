package com.manulaiko.blackeye.simulator.account.equipment.item;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.tabitha.Console;

/**
 * Item builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
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
                    rs.getInt("accounts_id"),
                    rs.getInt("items_id"),
                    rs.getInt("levels_id"),
                    rs.getInt("amount")
            );

            this._build(this._item.itemID);
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
     * @para item Item clone
     */
    public Builder(Item item)
    {
        try {
            this._item = new Item(
                    item.id,
                    item.accountID,
                    item.itemID,
                    item.level,
                    item.amount
            );

            this._build(item.itemID);
        } catch(Exception e) {
            Console.println("Couldn't clone item!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the item
     *
     * @param itemID Item id
     */
    private void _build(int itemID)
    {
        try {
            com.manulaiko.blackeye.simulator.item.Item item = GameManager.items.getByID(itemID);

            this._item.setItem(item);
        } catch(Exception e) {
            Console.println("Couldn't build ship!");
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
