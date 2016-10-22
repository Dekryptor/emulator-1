package com.manulaiko.blackeye.simulator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Base class for all factories.
 *
 * It contains the basic methods for loading and retrieving objects.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Factory
{
    /**
     * Instanced objects.
     *
     * Map with instanced objects for lazy-load.
     *
     * @var Instanced objects.
     */
    protected HashMap<Integer, Object> _instances = new HashMap<>();

    /**
     * Factory's table.
     *
     * The table which the factory belongs to.
     *
     * @var Table name.
     */
    protected String _table = "";

    /**
     * Constructor.
     *
     * @param table    Table name.
     * @param lazyload `true` if objects should be lazy-loaded, `false` if not.
     */
    public Factory(String table, boolean lazyload)
    {
        this._table = table;

        if(!lazyload) {
            this._instances = this.loadAll();
        }
    }

    /**
     * Returns an object with specified ID.
     *
     * If the object hasn't been loaded yet, it will be loaded.
     *
     * @param id Object id.
     *
     * @return Object with ID.
     *
     * @throws NotFound If given ID does not exist.
     */
    public Object getByID(int id) throws NotFound
    {
        if(this._instances.containsKey(id)) {
            return this._instances.get(id);
        }

        Object obj = this.loadByID(id);
        this._instances.put(id, obj);

        return obj;
    }

    /**
     * Returns all instanced objects.
     *
     * @return Instanced objects.
     */
    public HashMap getAll()
    {
        return this._instances;
    }

    /**
     * Builds and returns an object with specified ID.
     *
     * Instead of retrieving it from the map, it will be loaded directly
     * from the database.
     *
     * @param id Object id.
     *
     * @return Object with ID.
     *
     * @throws NotFound If given ID does not exist.
     */
    public Object loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `?` WHERE `id`=?");
            ps.setString(1, this._table);
            ps.setInt(2, id);

            ResultSet result = ps.executeQuery();

            if(!result.next()) {
                throw new NotFound(this._table, "id: "+ id);
            }

            return this.build(result);
        } catch(SQLException e) {
            throw new NotFound(this._table, "id: "+ id);
        }
    }

    /**
     * Builds and returns all objects from database.
     *
     * @return Database objects.
     */
    public HashMap loadAll()
    {
        HashMap<Integer, Object> objects = new HashMap<>();

        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `?`");
            ps.setString(1, this._table);

            ResultSet result = ps.executeQuery();

            int i = 0;
            while(result.next()) {
                objects.put(i++, this.build(result));
            }
        } catch(SQLException e) {
            Console.println("Couldn't load all objects from `" + this._table + "`");
            Console.println(e.getMessage());
        }

        return objects;
    }

    /**
     * Builds and returns an object.
     *
     * Instances the builder object and returns the object.
     *
     * @param result Query's result.
     */
    public abstract Object build(ResultSet result);
}
