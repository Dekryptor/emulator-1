package com.manulaiko.blackeye.simulator;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
     * Whether the save method will execute a
     * `UPDATE` query or a `INSERT INTO` query.
     *
     * @var Query type.
     */
    protected boolean _isInsert = false;

    /**
     * Saves the simulator to the database.
     */
    public void save()
    {
        try {
            if(this._isInsert) {
                this._insert();
                this._isInsert = false;

                return;
            }

            this._update();
        } catch(Exception e) {
            Console.println("Couldn't update simulator for "+ databaseTable +" table!");
            Console.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates simulator's info.
     *
     * @throws Exception If something goes wrong.
     */
    protected void _update() throws Exception
    {
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
    }

    /**
     * Inserts simulator's info.
     *
     * @throws Exception If something goes wrong.
     */
    protected void _insert() throws Exception
    {
        int i  = 1;

        HashMap<String, Object> fields = this._getDatabaseFields();
        String query                   = "INSERT INTO `"+ this.databaseTable +"` ";
        String keys                    = "(";
        String values                  = "(";

        for(String key : fields.keySet()) {
            keys   += "`"+ key +"`, ";
            values += "?, ";
        }
        keys   = keys.substring(0, (keys.length() - 2)) + ")";
        values = values.substring(0, (values.length() - 2)) + ")";

        query = query + keys + " VALUES "+ values;

        Console.println(query);

        PreparedStatement ps = Main.database.prepare(query, Statement.RETURN_GENERATED_KEYS);

        for(Object value : fields.values()) {
            if(value instanceof Integer) {
                ps.setInt(i++, Integer.parseInt(value.toString()));
            } else if(value instanceof Boolean) {
                ps.setBoolean(i++, Boolean.parseBoolean(value.toString()));
            } else {
                ps.setString(i++, value.toString());
            }
        }

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            this._setDatabaseIdentifier(rs.getInt(1));
        }
    }

    /**
     * Returns database ID.
     *
     * @return Database ID.
     */
    protected abstract int _getDatabaseIdentifier();

    /**
     * Sets database ID.
     *
     * @param id Database ID.
     */
    protected abstract void _setDatabaseIdentifier(int id);

    /**
     * Returns database fields.
     *
     * @return Database fields.
     */
    protected abstract HashMap<String, Object> _getDatabaseFields();
}
