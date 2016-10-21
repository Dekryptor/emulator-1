package com.manulaiko.blackeye.simulator.npc;

import java.sql.ResultSet;

/**
 * Factory for the `npcs` table.
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
        super("npcs", false);
    }

    /**
     * Builds and returns a npc.
     *
     * @param rs Query result.
     *
     * @return NPC object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
