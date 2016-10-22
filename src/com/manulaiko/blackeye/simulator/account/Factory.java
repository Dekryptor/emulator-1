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
     * Constructor.
     */
    public Factory()
    {
        super("accounts", false);
    }

    /**
     * Builds and returns an account.
     *
     * @param rs Query result.
     *
     * @return Account object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
