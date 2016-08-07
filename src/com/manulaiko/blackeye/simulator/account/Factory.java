package com.manulaiko.blackeye.simulator.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Account factory
 *
 * Used for instance account objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account
 */
public class Factory
{
    /**
     * Instanced accounts
     */
    private HashMap<Integer, Account> _accounts = new HashMap<>();

    /**
     * Hangar factory for table `accounts_hangars`
     */
    public com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory hangars = new com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory();

    ///////////////////////
    // Start get methods //
    ///////////////////////

    /**
     * Returns given account
     *
     * @param id Account id
     *
     * @return The account
     *
     * @throws NotFound If account doesn't exist
     */
    public Account getByID(int id) throws NotFound
    {
        if(!this._accounts.containsKey(id)) {
            Account a = this.loadByID(id);

            this._accounts.put(id, a);

            return a;
        }

        return this._accounts.get(id);
    }

    /**
     * Returns given account
     *
     * @param name Account name
     *
     * @return The account
     *
     * @throws NotFound If account doesn't exist
     */
    public Account getByName(String name) throws NotFound
    {
        for(Entry<Integer, Account> a : this._accounts.entrySet()) {
            if(a.getValue().name.equals(name)) {
                return a.getValue();
            }
        }

        Account a = this.loadByName(name);

        this._accounts.put(a.id, a);

        return a;
    }

    /**
     * Returns all items
     *
     * @return All items
     */
    public HashMap<Integer, Account> getAllAccounts()
    {
        return this._accounts;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._accounts.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a account by its ID
     *
     * @param id Account id
     *
     * @return The account
     *
     * @throws NotFound If account doesn't exist in database
     */
    public Account loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getAccount();
            } else {
                throw new NotFound("account", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("account", "id: "+ id);
        }
    }

    /**
     * Builds and returns an account by its name
     *
     * @param name Account name
     *
     * @return The Account
     *
     * @throws NotFound If map doesn't exist in database
     */
    public Account loadByName(String name) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `accounts` WHERE `name`=?");
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getAccount();
            } else {
                throw new NotFound("account", name);
            }
        } catch(SQLException e) {
            throw new NotFound("account", name);
        }
    }

    /**
     * Loads all items from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = Main.mysqlManager.query("SELECT * FROM `accounts`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Account a = builder.getAccount();

                this._accounts.put(a.id, a);
            }
        } catch(Exception e) {
            Console.println("Couldn't load account!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
