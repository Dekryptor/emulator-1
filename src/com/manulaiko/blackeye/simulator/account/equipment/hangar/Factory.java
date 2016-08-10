package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Hangar factory
 *
 * Used for instance hangar objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Instanced hangars
     */
    private HashMap<Integer, Hangar> _hangars = new HashMap<>();

    /**
     * Hangar factory for table `accounts_equipment_ships`
     */
    public com.manulaiko.blackeye.simulator.account.equipment.ship.Factory ships = new com.manulaiko.blackeye.simulator.account.equipment.ship.Factory();

    /**
     * Hangar factory for table `accounts_equipment_configurations`
     */
    public com.manulaiko.blackeye.simulator.account.equipment.configuration.Factory configurations = new com.manulaiko.blackeye.simulator.account.equipment.configuration.Factory();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given hangar
     *
     * @param id Hangar id
     *
     * @return The hangar
     *
     * @throws NotFound If hangar doesn't exist
     */
    public Hangar getByID(int id) throws NotFound
    {
        if(!this._hangars.containsKey(id)) {
            Hangar h = this.loadByID(id);

            this._hangars.put(id, h);

            return h;
        }

        return this._hangars.get(id);
    }

    /**
     * Returns given hangar
     *
     * @param id Account id
     *
     * @return The hangar
     *
     * @throws NotFound If hangar doesn't exist
     */
    public HashMap<Integer, Hangar> getByAccountID(int id) throws NotFound
    {
        final HashMap<Integer, Hangar> hangars = new HashMap<>();

        this._hangars.forEach((Integer i, Hangar h) -> {
            if(h.accountID == id) {
                hangars.put(i, h);
            }
        });

        if(hangars.size() == 0) {
            HashMap<Integer, Hangar> hangarsLoaded = this.loadByAccountID(id);

            if(hangarsLoaded.size() == 0) {
                throw new NotFound("hangars", "accounts_id: "+ id);
            }

            hangarsLoaded.forEach(this._hangars::put);

            return hangarsLoaded;
        }

        return hangars;
    }

    /**
     * Returns all hangars
     *
     * @return All hangars
     */
    public HashMap<Integer, Hangar> getAllHangars()
    {
        return this._hangars;
    }

    /**
     * Returns the amount of loaded hangars
     *
     * @return Amount of loaded hangars
     */
    public int getAmount()
    {
        return this._hangars.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns an hangar by its ID
     *
     * @param id Hangar id
     *
     * @return The hangar
     *
     * @throws NotFound If hangar doesn't exist in database
     */
    public Hangar loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts_equipment_hangars` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getHangar();
            } else {
                throw new NotFound("hangar", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("hangar", "id: "+ id);
        }
    }

    /**
     * Builds and returns a list of hangars by its accounts_id
     *
     * @param id Account id
     *
     * @return The hangar list
     *
     * @throws NotFound If no hangar exists in database
     */
    public HashMap<Integer, Hangar> loadByAccountID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts_equipment_hangars` WHERE `accounts_id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            HashMap<Integer, Hangar> hangars = new HashMap<>();

            while(result.next()) {
                Builder b = new Builder(result);
                Hangar  h = b.getHangar();

                hangars.put(h.id, h);
            }

            if(hangars.size() == 0) {
                throw new NotFound("hangars", "accounts_id: "+ id);
            }

            return hangars;
        } catch(SQLException e) {
            throw new NotFound("hangar", "id: "+ id);
        }
    }

    /**
     * Loads all hangars from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `accounts_equipment_hangars`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Hangar h = builder.getHangar();

                this._hangars.put(h.id, h);
            }
        } catch(Exception e) {
            Console.println("Couldn't load hangars!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
