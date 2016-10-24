package com.manulaiko.blackeye.simulator.account;

import java.sql.ResultSet;

/**
 * Factory for the `accounts` table.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.simulator.Factory
{
    /**
     * Hangar's factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory hangars = new com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory();

    /**
     * Item's factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.item.Factory items = new com.manulaiko.blackeye.simulator.account.equipment.item.Factory();

    /**
     * Constructor.
     */
    public Factory()
    {
        super("accounts");
    }

    /**
     * Builds and returns an account.
     *
     * @param rs Query result.
     *
     * @return Account object.
     */
    public Object build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
