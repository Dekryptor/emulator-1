package com.manulaiko.blackeye.simulator.account;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.hangar.Hangar;
import com.manulaiko.blackeye.simulator.account.settings.Settings;
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
    public void build() throws Exception
    {
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
        this._setHangar(this._result.getInt("accounts_equipment_hangars_id"));
        this._setSettings(this._result.getInt("id"));
    }

    /**
     * Sets account's clan.
     *
     * @param id Clan ID.
     *
     * @throws Exception If clan ID does not exist or level failed.
     */
    private void _setClan(int id) throws Exception
    {
        Clan c = (Clan)GameManager.clans.getByID(id);

        ((Account)this._object).setClan(c);
    }

    /**
     * Sets account's level.
     *
     * @param id Level ID.
     *
     * @throws Exception If level ID does not exist or build failed.
     */
    private void _setLevel(int id) throws Exception
    {
        Level l = (Level)GameManager.levels.getByID(id);

        ((Account)this._object).setLevel(l);
    }

    /**
     * Sets account's hangar.
     *
     * @param id Hangar ID.
     *
     * @throws Exception If hangar ID does not exist or build failed.
     */
    private void _setHangar(int id) throws Exception
    {
        Hangar h = (Hangar)GameManager.accounts.hangars.getByID(id);

        ((Account)this._object).setHangar(h);
    }

    /**
     * Sets account's settings.
     *
     * @param id Account ID.
     *
     * @throws Exception if Account ID does not exist or build failed.
     */
    private void _setSettings(int id) throws Exception
    {
        Settings s = GameManager.accounts.settings.getByAccountID(id);

        ((Account)this._object).setSettings(s);
    }
}
