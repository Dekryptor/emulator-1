package com.manulaiko.blackeye.simulator.map;

import java.sql.ResultSet;

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
        super("collectables", false);
    }

    /**
     * Builds and returns a collectable.
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
