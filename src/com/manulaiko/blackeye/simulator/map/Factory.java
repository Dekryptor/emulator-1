package com.manulaiko.blackeye.simulator.map;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;

/**
 * Factory for the `maps` table.
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
        super("maps");
    }

    /**
     * Builds and returns a map.
     *
     * @param rs Query result.
     *
     * @return Map object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "maps";

        return s;
    }
}
