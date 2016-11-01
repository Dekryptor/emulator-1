package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.sql.ResultSet;

/**
 * Factory for the `accounts_equipment_hangars` table.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.simulator.Factory
{
    /**
     * Ships factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.ship.Factory ships = new com.manulaiko.blackeye.simulator.account.equipment.ship.Factory();

    /**
     * Configurations factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.configuration.Factory configurations = new com.manulaiko.blackeye.simulator.account.equipment.configuration.Factory();

    /**
     * Constructor.
     */
    public Factory()
    {
        super("accounts_equipment_hangars");
    }

    /**
     * Initializes the factory (loads all objects).
     *
     * @throws Exception If query or build failed.
     */
    public void initialize() throws Exception
    {
        this.ships.initialize();
        this.configurations.initialize();

        super.initialize();
    }

    /**
     * Builds and returns an hangar.
     *
     * @param rs Query result.
     *
     * @return Hangar object.
     */
    public Object build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        return b.get();
    }
}
