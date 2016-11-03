package com.manulaiko.blackeye.simulator.account.equipment.item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;

/**
 * Factory for the `accounts_equipment_items` table.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.simulator.Factory
{
    /**
     * Constructor.
     */
    public Factory()
    {
        super("accounts_equipment_items");
    }

    /**
     * Builds and returns an item.
     *
     * @param rs Query result.
     *
     * @return Item object.
     */
    public Object build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        return b.get();
    }

    /**
     * Returns items of given account ID.
     *
     * @param id Account ID.
     *
     * @return Account's items.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Item> getByAccountID(int id) throws Exception
    {
        HashMap<Integer, Item> items = new HashMap<>();

        for(Map.Entry<Integer, Object> item : this._instances.entrySet()) {
            if(((Item)item.getValue()).accountID == id) {
                items.put(((Item)item.getValue()).id, (Item)item.getValue());
            }
        }

        if(items.size() > 0) {
            return items;
        }

        items = this.loadByAccountID(id);
        if(items.size() == 0) {
            return items;
        }

        items.forEach((i, item)->{
            this._instances.put(i, item);
        });

        return items;
    }

    /**
     * Builds and returns all items of given account ID.
     *
     * @param id Account ID.
     *
     * @return Database objects.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Item> loadByAccountID(int id) throws Exception
    {
        HashMap<Integer, Item> items = new HashMap<>();

        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `accounts_equipment_items` WHERE `accounts_id`=?");
            ps.setInt(0, id);

            ResultSet result = ps.executeQuery();

            int i = 0;
            while(result.next()) {
                Item item = (Item)this.build(result);

                items.put(item.id, item);
            }
        } catch(SQLException e) {
            Console.println("Couldn't load items from account " + id);
            Console.println(e.getMessage());
        }

        return items;
    }
}
