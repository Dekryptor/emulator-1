package com.manulaiko.blackeye.simulator.portal;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Portal factory
 *
 * Used for instance portal objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.portal
 */
public class Factory
{
    /**
     * Instanced portals
     */
    private HashMap<Integer, Portal> _portals = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given portal
     *
     * @param id Portal id
     *
     * @return The portal
     *
     * @throws NotFound If portal doesn't exist
     */
    public Portal getByID(int id) throws NotFound
    {
        if(!this._portals.containsKey(id)) {
            Portal m = this.loadByID(id);

            this._portals.put(id, m);

            return m;
        }

        return this._portals.get(id);
    }

    /**
     * Returns given portal
     *
     * This method will only load portals if there's no
     * portal with given maps_id
     *
     * @param id Map id
     *
     * @return The portal
     */
    public HashMap<Integer, Portal> getByMapID(int id) throws NotFound
    {
        HashMap<Integer, Portal> portals = new HashMap<>();

        for(Entry<Integer, Portal> portal : this._portals.entrySet()) {
            if(portal.getValue().mapsID == id) {
                portals.put(portal.getKey(), portal.getValue());
            }
        }

        if(portals.size() == 0) {
            portals = this.loadByMapID(id);
        }

        return portals;
    }

    /**
     * Returns all portals
     *
     * @return All portals
     */
    public HashMap<Integer, Portal> getAllPortals()
    {
        return this._portals;
    }

    /**
     * Returns the amount of loaded portals
     *
     * @return Amount of loaded portals
     */
    public int getAmount()
    {
        return this._portals.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a portal by its ID
     *
     * @param id Portal id
     *
     * @return The portal
     *
     * @throws NotFound If portal doesn't exist in database
     */
    public Portal loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `maps_portals` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getPortal();
            } else {
                throw new NotFound("portal", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("portal", "id: "+ id);
        }
    }

    /**
     * Builds and returns a portal by its maps_id
     *
     * @param id Map id
     *
     * @return The portals with given maps_id
     *
     * @throws NotFound If portal doesn't exist in database
     */
    public HashMap<Integer, Portal> loadByMapID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `maps_portals` WHERE `maps_id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            HashMap<Integer, Portal> portals = new HashMap<>();

            while(result.next()) {
                Builder builder = new Builder(result);

                portals.put(result.getInt("id"), builder.getPortal());
            }

            if(portals.size() > 0) {
                return portals;
            }
        } catch(SQLException e) {
        }

        throw new NotFound("portals", "maps_id: "+ id);
    }

    /**
     * Loads all portals from database
     */
    public void loadAll()
    {
        try {
            ResultSet result = Main.mysqlManager.query("SELECT * FROM `maps_portals`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Portal m = builder.getPortal();

                this._portals.put(m.id, m);
            }
        } catch(Exception e) {
            Console.println("Couldn't load portal!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
