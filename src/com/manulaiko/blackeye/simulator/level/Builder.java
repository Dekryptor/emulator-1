package com.manulaiko.blackeye.simulator.level;

import java.sql.ResultSet;

import com.manulaiko.tabitha.Console;

/**
 * Level builder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder extends com.manulaiko.blackeye.simulator.Builder
{
    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        super(rs);
    }

    /**
     * Builds and returns a level.
     */
    public void build() throws Exception
    {
        this._object = new Level(
                this._result.getInt("id"),
                this._result.getLong("accounts"),
                this._result.getInt("pets"),
                this._result.getShort("drones"),
                this._result.getDouble("shield"),
                this._result.getDouble("damage")
        );
    }
}
