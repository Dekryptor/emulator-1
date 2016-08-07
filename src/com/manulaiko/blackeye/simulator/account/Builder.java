package com.manulaiko.blackeye.simulator.account;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.blackeye.simulator.account.equipment.hangar.Hangar;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.blackeye.simulator.clan.Clan;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Account builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account
 */
public class Builder
{
    /**
     * Account object
     *
     * The current account we're building
     */
    private Account _account;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._account = new Account(
                    rs.getInt("id"),
                    rs.getString("session_id"),
                    rs.getString("name"),
                    rs.getInt("factions_id"),
                    rs.getInt("clans_id"),
                    rs.getInt("uridium"),
                    rs.getLong("credits"),
                    rs.getDouble("jackpot"),
                    rs.getLong("experience"),
                    rs.getInt("honor"),
                    rs.getInt("levels_id"),
                    rs.getBoolean("is_premium"),
                    rs.getInt("ranks_id"),
                    rs.getInt("rank_points")
            );

            this._build(this._account.id, this._account.clansID, this._account.levelsID);
        } catch(Exception e) {
            Console.println("Couldn't build account!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a account
     *
     * @para account Account to clone
     */
    public Builder(Account account)
    {
        try {
            this._account = new Account(
                    account.id,
                    account.sessionID,
                    account.name,
                    account.factionsID,
                    account.clansID,
                    account.uridium,
                    account.credits,
                    account.jackpot,
                    account.experience,
                    account.honor,
                    account.levelsID,
                    account.isPremium,
                    account.ranksID,
                    account.rankPoints
            );

            this._build(account.id, account.clansID, account.levelsID);
        } catch(Exception e) {
            Console.println("Couldn't clone account!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the account
     *
     * @param id       Account ID
     * @param clansID  Clan ID
     * @param levelsID Level ID
     */
    private void _build(int id, int clansID, int levelsID)
    {
        try {
            Hangar h = GameManager.accounts.hangars.getByID(id);

            this._account.setHangar(h);
        } catch(NotFound e) {
            Console.println("Hangar "+ id +" does not exist!");
        }

        try {
            Level l = GameManager.levels.getByID(levelsID);

            this._account.setLevel(l);
        } catch(NotFound e) {
            Console.println("Level "+ levelsID +" does not exist!");
        }

        if(clansID > 0) {
            try {
                Clan c = GameManager.clans.getByID(clansID);

                this._account.setClan(c);
            } catch(NotFound e) {
                Console.println("Clan " + clansID + " does not exist!");
            }
        } else {
            this._account.setClan(new Clan(-1, "", "", 0));
        }
    }

    /**
     * Returns the account
     *
     * @return The account
     */
    public Account getAccount()
    {
        return this._account;
    }
}
