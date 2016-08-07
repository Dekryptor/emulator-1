package com.manulaiko.blackeye.simulator.ship;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Ship factory
 *
 * Used for instance ship objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.ship
 */
public class Factory
{
    /**
     * Instanced ships
     */
    private HashMap<Integer, Ship> _ships = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given ship
     *
     * @param id Ship id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist
     */
    public Ship getByID(int id) throws NotFound
    {
        if(!this._ships.containsKey(id)) {
            Ship s = this.loadByID(id);

            this._ships.put(id, s);

            return s;
        }

        return this._ships.get(id);
    }

    /**
     * Returns given ship
     *
     * @param id Item id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist
     */
    public Ship getByItemID(int id) throws NotFound
    {
        for(Entry<Integer, Ship> s : this._ships.entrySet()) {
            if(s.getValue().itemsID == id) {
                return s.getValue();
            }
        }

        Ship s = this.loadByItemID(id);

        this._ships.put(s.id, s);

        return s;
    }

    /**
     * Returns all ships
     *
     * @return All ships
     */
    public HashMap<Integer, Ship> getAllShips()
    {
        return this._ships;
    }

    /**
     * Returns the amount of loaded ships
     *
     * @return Amount of loaded ships
     */
    public int getAmount()
    {
        return this._ships.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a ship by its ID
     *
     * @param id Ship id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist in database
     */
    public Ship loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `ships` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getShip();
            } else {
                throw new NotFound("ship", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("ship", "id: "+ id);
        }
    }

    /**
     * Builds and returns a ship by its item id
     *
     * @param id Item id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist in database
     */
    public Ship loadByItemID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `ships` WHERE `items_id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getShip();
            } else {
                throw new NotFound("ship", "items_id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("ship", "items_id: "+ id);
        }
    }

    /**
     * Loads all ships from database
     */
    public void loadAll()
    {
        try {
            ResultSet result = Main.mysqlManager.query("SELECT * FROM `ships`");

            while(result.next()) {
                Builder builder = new Builder(result);
                
                Ship s = builder.getShip();

                this._ships.put(s.id, s);
            }
        } catch(Exception e) {
            Console.println("Couldn't load ship!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
