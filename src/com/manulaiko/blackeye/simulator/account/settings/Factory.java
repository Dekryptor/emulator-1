package com.manulaiko.blackeye.simulator.account.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;

/**
 * Factory for the `accounts_settings` table.
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
        super("accounts_settings");
    }

    /**
     * Builds and returns a setting.
     *
     * @param rs Query result.
     *
     * @return Settings object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = (Simulator)b.get();
        s.databaseTable = "accounts_settings";

        return s;
    }

    /**
     * Returns settings of given account ID.
     *
     * @param id Account ID.
     *
     * @return Account's configurations.
     *
     * @throws Exception In case build failed.
     */
    public Settings getByAccountID(int id) throws Exception
    {
        for(Map.Entry<Integer, Object> settings : this._instances.entrySet()) {
            if(((Settings)settings.getValue()).accountID == id) {
                return (Settings)settings.getValue();
            }
        }

        Settings set = this.loadByAccountID(id);
        if(set == null) {
            return null;
        }

        this._instances.put(set.id, set);

        return set;
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
    public Settings loadByAccountID(int id) throws Exception
    {
        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `accounts_settings` WHERE `accounts_id`=?");
            ps.setInt(0, id);

            ResultSet result = ps.executeQuery();

            int i = 0;
            while(result.next()) {
                return (Settings)this.build(result);
            }
        } catch(SQLException e) {
            Console.println("Couldn't load settings from account " + id);
            Console.println(e.getMessage());
        }

        return null;
    }
}
