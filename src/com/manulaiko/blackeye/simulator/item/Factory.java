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
        super("items");
    }

    /**
     * Builds and returns a item.
     *
     * @param rs Query result.
     *
     * @return Item object.
     */
    public Object build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
