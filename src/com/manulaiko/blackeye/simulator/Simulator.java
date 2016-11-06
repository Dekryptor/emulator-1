package com.manulaiko.blackeye.simulator;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import java.sql.PreparedStatement;
import java.util.HashMap;

/**
 * Base class for all simulators.
 *
 * Contains basic methods like `save` or `update`.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Simulator
{
    /**
     * Table where the simulator belongs to.
     *
     * @var Database table.
     */
    public String databaseTable = "";

    /**
     * Saves the simulator to the database.
     */
    public void save()
    {
        try {
            int id = this._getDatabaseIdentifier();
            int i  = 1;

            HashMap<String, Object> fields = this._getDatabaseFields();
            String query                   = "UPDATE `"+ this.databaseTable +"` SET ";

            for(String key : fields.keySet()) {
                query += "`"+ key +"`=?, ";
            }
            query = query.substring(0, (query.length() - 2));
            query += " WHERE `id`=?";

            PreparedStatement ps = Main.database.prepare(query);

            for(Object value : fields.values()) {
                if(value instanceof Integer) {
                    ps.setInt(i++, Integer.parseInt(value.toString()));
                } else if(value instanceof Boolean) {
                    ps.setBoolean(i++, Boolean.parseBoolean(value.toString()));
                } else {
                    ps.setString(i++, value.toString());
                }
            }

            ps.setInt(i++, id);
            ps.executeUpdate();
        } catch(Exception e) {
            Console.println("Couldn't save simulator for "+ databaseTable +" table!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Returns database ID.
     *
     * @return Database ID.
     */
    protected abstract int _getDatabaseIdentifier();

    /**
     * Returns database fields.
     *
     * @return Database fields.
     */
    protected abstract HashMap<String, Object> _getDatabaseFields();
}
