package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.sql.ResultSet;

/**
 * Factory for the `accounts_equipment_ships` table.
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
        super("accounts_equipment_ships", false);
    }

    /**
     * Builds and returns a ship.
     *
     * @param rs Query result.
     *
     * @return Ship object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
