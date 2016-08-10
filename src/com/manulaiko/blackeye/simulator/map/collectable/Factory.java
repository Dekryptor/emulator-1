package com.manulaiko.blackeye.simulator.collectable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Collectable factory
 *
 * Used for instance collectable objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.collectable
 */
public class Factory
{
    /**
     * Instanced maps
     */
    private HashMap<Integer, Collectable> _collectables = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given collectable
     *
     * @param id Collectable id
     *
     * @return The collectable
     *
     * @throws NotFound If collectable doesn't exist
     */
    public Collectable getByID(int id) throws NotFound
    {
        if(!this._collectables.containsKey(id)) {
            Collectable c = this.loadByID(id);

            this._collectables.put(id, c);

            return c;
        }

        return this._collectables.get(id);
    }

    /**
     * Returns all maps
     *
     * @return All maps
     */
    public HashMap<Integer, Collectable> getAllCollectables()
    {
        return this._collectables;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._collectables.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a collectable by its ID
     *
     * @param id Collectable id
     *
     * @return The collectable
     *
     * @throws NotFound If collectable doesn't exist in database
     */
    public Collectable loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `collectables` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getCollectable();
            } else {
                throw new NotFound("collectable", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("collectable", "id: "+ id);
        }
    }
    
    /**
     * Loads all maps from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `collectables`");

            java.awt.Point p = new java.awt.Point(100, 100);

            while(result.next()) {
                Builder builder = new Builder(result);

                Collectable m = builder.getCollectable();

                this._collectables.put(m.id, m);
            }
        } catch(Exception e) {
            Console.println("Couldn't load collectable!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////

    /////////////////////////
    // Start clone methods //
    /////////////////////////
    /**
     * Clones given collectable
     *
     * @param collectable Collectable to clone
     *
     * @return Cloned collectable
     */
    public Collectable clone(Collectable collectable)
    {
        Builder b = new Builder(collectable);

        return b.getCollectable();
    }

    /**
     * Clones given collectable by ID
     *
     * @param id Collectable id
     *
     * @return Cloned collectable
     *
     * @throws NotFound If collectable id doesn't exit
     */
    public Collectable cloneByID(int id) throws NotFound
    {
        Collectable collectable = this.getByID(id);

        Builder b = new Builder(collectable);

        return b.getCollectable();
    }
    ///////////////////////
    // End clone methods //
    ///////////////////////
}
