package com.manulaiko.blackeye.simulator.map.collectable;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;

/**
 * Factory for the `collectables` table.
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
        super("collectables");
    }

    /**
     * Builds and returns a collectable.
     *
     * @param rs Query result.
     *
     * @return Collectable object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "collectables";

        return s;
    }
}
