package com.manulaiko.blackeye.simulator.clan;

import java.sql.ResultSet;

import com.manulaiko.blackeye.simulator.Simulator;
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
        super("clans");
    }

    /**
     * Builds and returns clan.
     *
     * @param rs Query result.
     *
     * @return Clan object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "clans";

        return s;
    }
}
