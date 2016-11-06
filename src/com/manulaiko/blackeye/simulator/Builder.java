package com.manulaiko.blackeye.simulator;

import java.sql.ResultSet;

/**
 * Base class for all builders.
 *
 * The child class must implement the `build` method.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Builder
{
    /**
     * Simulator we're building.
     *
     * @var The building simulator.
     */
    protected Simulator _object;

    /**
     * Query result set.
     *
     * @var Query.
     */
    protected ResultSet _result;

    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        this._result = rs;
    }

    /**
     * Builds the Simulator.
     *
     * @throws Exception In case the build failed.
     */
    public abstract void build() throws Exception;

    /**
     * Returns the Simulator.
     *
     * @return The Simulator.
     *
     * @throws Exception In case `build` method failed.
     */
    public Simulator get() throws Exception
    {
        if(this._object == null) {
            this.build();
        }

        return this._object;
    }
}
