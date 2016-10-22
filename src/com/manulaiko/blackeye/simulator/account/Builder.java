package com.manulaiko.blackeye.simulator.account;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.clan.Clan;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Account builder.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder extends com.manulaiko.blackeye.simulator.Builder
{
    /**
     * Constructor.
     *
     * @param rs Query result.
     */
    public Builder(ResultSet rs)
    {
        super(rs);
    }

    /**
     * Builds an account.
     */
    public void build()
    {
        try {
            this._object = new Account(
                    this._result.getInt("id"),
                    this._result.getString("session_id"),
                    this._result.getString("name"),
                    this._result.getInt("factions_id"),
                    this._result.getInt("clans_id"),
                    this._result.getInt("uridium"),
                    this._result.getLong("credits"),
                    this._result.getDouble("jackpot"),
                    this._result.getLong("experience"),
                    this._result.getInt("honor"),
                    this._result.getInt("levels_id"),
                    this._result.getBoolean("is_premium"),
                    this._result.getInt("ranks_id"),
                    this._result.getInt("rank_points")
            );

            if(this._result.getInt("clans_id") != 0) {
                this._setClan(this._result.getInt("clans_id"));
            }

            this._setLevel(this._result.getInt("levels_id"));
        } catch(Exception e) {
            Console.println("Couldn't build npc!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sets account's clan.
     *
     * @param id Clan ID.
     *
     * @throws NotFound If clan ID does not exist.
     */
    private void _setClan(int id) throws NotFound
    {
        Clan c = (Clan)GameManager.clans.getByID(id);

        ((Account)this._object).setClan(c);
    }

    /**
     * Sets account's level.
     *
     * @param id Level ID.
     *
     * @throws NotFound If level ID does not exist.
     */
    private void _setLevel(int id) throws NotFound
    {
        Level l = (Level)GameManager.levels.getByID(id);

        ((Account)this._object).setLevel(l);
    }
}
