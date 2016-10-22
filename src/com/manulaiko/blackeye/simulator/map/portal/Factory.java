package com.manulaiko.blackeye.simulator.map.portal;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.map.Builder;

/**
 * Factory for the `maps_portals` table.
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
        super("maps_portals", false);
    }

    /**
     * Builds and returns portal.
     *
     * @param rs Query result.
     *
     * @return Portal object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
