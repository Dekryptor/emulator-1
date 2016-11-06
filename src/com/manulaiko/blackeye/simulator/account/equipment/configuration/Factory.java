package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;

/**
 * Factory for the `accounts_equipment_configurations` table.
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
        super("accounts_equipment_configurations");
    }

    /**
     * Builds and returns a configuration.
     *
     * @param rs Query result.
     *
     * @return Configuration object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "accounts_equipment_configurations";

        return s;
    }

    /**
     * Returns all configurations of given ship ID.
     *
     * @param id Ship ID.
     *
     * @return Ship's configurations.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Configuration> getByShipID(int id) throws Exception
    {
        HashMap<Integer, Configuration> configurations = new HashMap<>();

        this.getAll().forEach((i, c)->{
            if(((Configuration)c).shipID == id) {
                configurations.put(((Configuration)c).id, (Configuration)c);
            }
        });

        if(configurations.size() == 0) {
            return this.loadByShipID(id);
        }

        return configurations;
    }


    /**
     * Builds and returns all configurations of given ship ID.
     *
     * @param id Ship ID.
     *
     * @return Database objects.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Configuration> loadByShipID(int id) throws Exception
    {
        HashMap<Integer, Configuration> configurations = new HashMap<>();

        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `accounts_equipment_configurations` WHERE `accounts_equipment_ships_id`=?");
            ps.setInt(0, id);

            ResultSet result = ps.executeQuery();

            int i = 0;
            while(result.next()) {
                configurations.put(i++, (Configuration)this.build(result));
            }
        } catch(SQLException e) {
            Console.println("Couldn't load all configurations from ship " + id);
            Console.println(e.getMessage());
        }

        return configurations;
    }
}
