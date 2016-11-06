package com.manulaiko.blackeye.simulator.item;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;

/**
 * Factory for the `items` table.
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
        super("items");
    }

    /**
     * Builds and returns a item.
     *
     * @param rs Query result.
     *
     * @return Item object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "items";

        return s;
    }
}
