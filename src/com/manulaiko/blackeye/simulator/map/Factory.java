package com.manulaiko.blackeye.simulator.map;

import java.sql.ResultSet;

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
        super("maps", false);
    }

    /**
     * Builds and returns a map.
     *
     * @param rs Query result.
     *
     * @return Map object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
