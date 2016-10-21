package com.manulaiko.blackeye.simulator.item;

import java.sql.ResultSet;

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
        super("items", false);
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
