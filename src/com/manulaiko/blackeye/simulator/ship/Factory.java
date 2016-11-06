package com.manulaiko.blackeye.simulator.ship;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;

/**
 * Factory for the `ships` table.
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
        super("ships");
    }

    /**
     * Builds and returns a item.
     *
     * @param rs Query result.
     *
     * @return Ship object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "ships";

        return s;
    }
}
