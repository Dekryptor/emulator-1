package com.manulaiko.blackeye.simulator.account.equipment.item;

import java.sql.ResultSet;

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
        super("accounts_equipment_items", false);
    }

    /**
     * Builds and returns an item.
     *
     * @param rs Query result.
     *
     * @return Item object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
