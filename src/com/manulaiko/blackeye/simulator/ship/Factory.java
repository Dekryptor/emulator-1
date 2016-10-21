package com.manulaiko.blackeye.simulator.ship;

import java.sql.ResultSet;

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
        super("ships", false);
    }

    /**
     * Builds and returns a item.
     *
     * @param rs Query result.
     *
     * @return Collectable object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
