package com.manulaiko.blackeye.simulator.account.equipment.ship;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;

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
        super("accounts_equipment_ships");
    }

    /**
     * Builds and returns a ship.
     *
     * @param rs Query result.
     *
     * @return Ship object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "accounts_equipment_ships";

        return s;
    }
}
