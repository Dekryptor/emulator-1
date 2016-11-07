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
     * Instanced Simulator.
     *
     * Map with instanced Simulator for lazy-load.
     *
     * @var Instanced Simulator.
     */
    protected HashMap<Integer, Simulator> _instances = new HashMap<>();

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
            try {
                this.initialize();
            } catch(Exception e) {
                Console.println("Couldn't initialize factory!");
                Console.println(e.getMessage());
            }
        }
    }

    /**
     * Shorter constructor 'cause lazy typing.
     *
     * @param table Table name.
     */
    public Factory(String table)
    {
        this(table, true);
    }

    /**
     * Initializes the factory (loads all Simulator).
     *
     * @throws Exception If query or build failed.
     */
    public void initialize() throws Exception
    {
        this._instances = this.loadAll();
    }

    /**
     * Returns an Simulator with specified ID.
     *
     * If the Simulator hasn't been loaded yet, it will be loaded.
     *
     * @param id Simulator id.
     *
     * @return Simulator with ID.
     *
     * @throws Exception If given ID does not exist or build failed.
     */
    public Simulator getByID(int id) throws Exception
    {
        if(this._instances.containsKey(id)) {
            return this._instances.get(id);
        }

        Simulator obj = this.loadByID(id);
        this._instances.put(id, obj);

        return obj;
    }

    /**
     * Returns all instanced Simulator.
     *
     * @return Instanced Simulator.
     */
    public HashMap<Integer, Simulator> getAll()
    {
        return this._instances;
    }

    /**
     * Builds and returns a Simulator with specified ID.
     *
     * Instead of retrieving it from the map, it will be loaded directly
     * from the database.
     *
     * @param id Simulator id.
     *
     * @return Simulator with ID.
     *
     * @throws Exception If given ID does not exist or build failed.
     */
    public Simulator loadByID(int id) throws Exception
    {
        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `?` WHERE `id`=?");
            ps.setString(0, this._table);
            ps.setInt(1, id);

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
     * Builds and returns all Simulator from database.
     *
     * @return Database Simulator.
     *
     * @throws Exception In case build or query failed.
     */
    public HashMap loadAll() throws Exception
    {
        HashMap<Integer, Simulator> objects = new HashMap<>();

        try {
            // Let's assume the guy implementing the class is a nice guy and doesn't mess with SQLi
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `"+ this._table +"`");

            ResultSet result = ps.executeQuery();

            int i = 1;
            while(result.next()) {
                objects.put(i++, this.build(result));
            }
        } catch(SQLException e) {
            Console.println("Couldn't load all objects from `" + this._table + "`");
            Console.println(e.getMessage());

            // Debug shit.
            throw e;
        }

        this._instances = objects;

        return objects;
    }

    /**
     * Builds and returns a Simulator.
     *
     * Instances the builder object and returns the Simulator.
     *
     * @param result Query's result.
     *
     * @throws Exception In case build failed.
     */
    public abstract Simulator build(ResultSet result) throws Exception;
}
