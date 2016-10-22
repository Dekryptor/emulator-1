package com.manulaiko.blackeye.simulator.account.equipment.configuration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;
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
        super("accounts_equipment_configurations", false);
    }

    /**
     * Builds and returns an item.
     *
     * @param rs Query result.
     *
     * @return Item object.
     */
    public Object build(ResultSet rs)
    {
        Builder b = new Builder(rs);

        return b.get();
    }

    /**
     * Returns all configurations of given ship ID.
     *
     * @param id Ship ID.
     *
     * @return Ship's configurations.
     */
    public HashMap<Integer, Configuration> getByShipID(int id)
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
     */
    public HashMap<Integer, Configuration> loadByShipID(int id)
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
