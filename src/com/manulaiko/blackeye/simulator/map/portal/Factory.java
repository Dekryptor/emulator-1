package com.manulaiko.blackeye.simulator.map.portal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.Console;

/**
 * Factory for the `maps_portals` table.
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
        super("maps_portals");
    }

    /**
     * Builds and returns portal.
     *
     * @param rs Query result.
     *
     * @return Portal object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "maps_portals";

        return s;
    }

    /**
     * Returns all portals of given map ID.
     *
     * @param id Map ID.
     *
     * @return Portals in map ID.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Portal> getByMapID(int id) throws Exception
    {
        HashMap<Integer, Portal> portals = new HashMap<>();

        this.getAll().forEach((i, p)->{
            if(((Portal)p).mapsID == id) {
                portals.put(((Portal)p).id, (Portal)p);
            }
        });

        if(portals.size() == 0) {
            return this.loadByMapID(id);
        }

        return portals;
    }

    /**
     * Builds and returns all portals of given map ID.
     *
     * @param id Map ID.
     *
     * @return Database objects.
     *
     * @throws Exception In case build failed.
     */
    public HashMap<Integer, Portal> loadByMapID(int id) throws Exception
    {
        HashMap<Integer, Portal> portals = new HashMap<>();

        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `maps_portals` WHERE `maps_id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            int i = 0;
            while(result.next()) {
                portals.put(i++, (Portal)this.build(result));
            }
        } catch(SQLException e) {
            Console.println("Couldn't load all portals from map " + id);
            Console.println(e.getMessage());
        }

        return portals;
    }
}
