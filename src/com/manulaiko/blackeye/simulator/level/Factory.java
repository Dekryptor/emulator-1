package com.manulaiko.blackeye.simulator.level;

import java.sql.ResultSet;

/**
 * Factory for the `levels` table.
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
        super("levels", false);
    }

    /**
     * Builds and returns a level.
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
