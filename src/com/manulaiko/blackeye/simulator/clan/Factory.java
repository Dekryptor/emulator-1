package com.manulaiko.blackeye.simulator.clan;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.map.Builder;

/**
 * Factory for the `clans` table.
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
        super("clans", false);
    }

    /**
     * Builds and returns clan.
     *
     * @param rs Query result.
     *
     * @return Clan object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
