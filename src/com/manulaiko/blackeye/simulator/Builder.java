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
     * Object we're building.
     *
     * @var The building object.
     */
    protected Object _object;

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
     * Builds the object.
     */
    public abstract void build();

    /**
     * Returns the object.
     *
     * @return The object.
     */
    public Object get()
    {
        if(this._object == null) {
            this.build();
        }

        return this._object;
    }
}
