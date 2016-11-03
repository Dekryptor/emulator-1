package com.manulaiko.blackeye.simulator.account.settings;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.account.equipment.item.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Settings builder.
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
     * Builds a settings.
     */
    public void build() throws Exception
    {
        JSONObject set = new JSONObject(this._result.getString("settings"));
        
        this._object = new Settings(
                this._result.getInt("id"),
                this._result.getInt("accounts_id"),

                this._result.getBoolean("display_chat"),
                this._result.getBoolean("display_drones"),
                this._result.getBoolean("display_notification"),
                this._result.getBoolean("display_playerNames"),
                this._result.getBoolean("display_windowBackground"),
                this._result.getBoolean("play_music"),
                this._result.getBoolean("play_sfx"),

                this._result.getInt("quality_attack"),
                this._result.getInt("quality_background"),
                this._result.getInt("quality_collectable"),
                this._result.getInt("quality_customized"),
                this._result.getInt("quality_effect"),
                this._result.getInt("quality_engine"),
                this._result.getInt("quality_explosion"),
                this._result.getInt("quality_poizone"),
                this._result.getInt("quality_presseting"),
                this._result.getInt("quality_ship"),

                this._result.getBoolean("alwaysDraggableWindows"),
                this._result.getBoolean("autoRefinement"),
                this._result.getBoolean("autoStart"),
                this._result.getBoolean("doubleClickAttack"),
                this._result.getBoolean("preloadUserShips"),
                this._result.getBoolean("quickSlotStopAttack"),

                this._result.getString("barStatus"),
                this._result.getString("clientResolution"),
                this._result.getString("mainmenuPosition"),
                this._result.getString("minimapSize"),
                this._result.getString("quickbarSlot"),
                this._result.getString("resizableWindows"),
                this._result.getString("slotmenu_order"),
                this._result.getString("slotmenu_position"),
                this._result.getString("windowSettings")
        );

        this._setSet(set);
    }

    /**
     * Sets the SET class.
     *
     * @param set JSONObject.
     *
     * @throws JSONException If couldn't parse the JSON.
     */
    private void _setSet(JSONObject set) throws JSONException
    {
        ((Settings)this._object).setSet(
                set.getBoolean("boosten"),
                set.getBoolean("dsplyDamage"),
                set.getBoolean("dsplyAllLas"),
                set.getBoolean("dsplyExplo"),
                set.getBoolean("dsplyPlayerName"),
                set.getBoolean("dsplyFirmIcon"),
                set.getBoolean("dsplyAlphaBg"),
                set.getBoolean("ignoreRES"),
                set.getBoolean("ignoreBOX"),
                set.getBoolean("convertGates"),
                set.getBoolean("convertOppo"),
                set.getBoolean("soundOnOff"),
                set.getBoolean("bgmusicOnOff"),
                set.getBoolean("dsplyStatus"),
                set.getBoolean("dsplyBubble"),
                set.getBoolean("selectedLaser"),
                set.getBoolean("selectedRocket"),
                set.getBoolean("dsplyDigits"),
                set.getBoolean("dsplyChat"),
                set.getBoolean("dsplyDrones"),
                set.getBoolean("showStarsystem"),
                set.getBoolean("ignoreCARGO"),
                set.getBoolean("ignoreHostileCARGO"),
                set.getBoolean("autochangeAmmo"),
                set.getBoolean("enableFastBuy")
        );
    }
}
