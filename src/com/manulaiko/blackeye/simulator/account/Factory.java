package com.manulaiko.blackeye.simulator.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Factory for the `accounts` table.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.simulator.Factory
{
    /**
     * Hangar's factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory hangars = new com.manulaiko.blackeye.simulator.account.equipment.hangar.Factory();

    /**
     * Item's factory.
     */
    public com.manulaiko.blackeye.simulator.account.equipment.item.Factory items = new com.manulaiko.blackeye.simulator.account.equipment.item.Factory();

    /**
     * Settings's factory.
     */
    public com.manulaiko.blackeye.simulator.account.settings.Factory settings = new com.manulaiko.blackeye.simulator.account.settings.Factory();

    /**
     * Constructor.
     */
    public Factory()
    {
        super("accounts");
    }

    /**
     * Initializes the factory (loads all objects).
     *
     * @throws Exception If query or build failed.
     */
    public void initialize() throws Exception
    {
        this.items.initialize();
        this.hangars.initialize();
        this.settings.initialize();

        super.initialize();
    }

    /**
     * Builds and returns an account.
     *
     * @param rs Query result.
     *
     * @return Account object.
     */
    public Simulator build(ResultSet rs) throws Exception
    {
        Builder b = new Builder(rs);

        Simulator s = b.get();
        s.databaseTable = "accounts";

        return s;
    }

    /**
     * Returns an account by given `session_id`.
     *
     * @param sessionID `session_id` field from database.
     *
     * @return Account object.
     *
     * @throws NotFound If no session ID is found on database.
     */
    public Account getBySessionID(String sessionID) throws NotFound
    {
        for(Map.Entry<Integer, Simulator> account : this._instances.entrySet()) {
            Account a = (Account)account.getValue();

            if(a.sessionID.equalsIgnoreCase(sessionID)) {
                return a;
            }
        }

        Account a = this.loadBySessionID(sessionID);
        this._instances.put(a.id, a);

        return a;
    }

    /**
     * Builds and returns an account by given `session_id`.
     *
     * @param sessionID `session_id` field from database.
     *
     * @return Account object.
     *
     * @throws NotFound If no session ID is found on database.
     */
    public Account loadBySessionID(String sessionID) throws NotFound
    {
        try {
            PreparedStatement ps = Main.database.prepare("SELECT * FROM `accounts` WHERE `session_id`=?");
            ps.setString(0, sessionID);

            ResultSet result = ps.executeQuery();

            if(!result.next()) {
                throw new NotFound("account", "session_id: "+ sessionID);
            }

            return (Account)this.build(result);
        } catch(Exception e) {
            throw new NotFound("account", "session_id: "+ sessionID);
        }
    }
}
