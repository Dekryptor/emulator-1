package com.manulaiko.blackeye.simulator.account.settings;

import java.sql.ResultSet;

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
        JSONObject set              = new JSONObject(this._result.getString("settings"));
        JSONArray minimapSize       = new JSONArray(this._result.getString("minimapSize"));
        JSONArray windowSettings    = new JSONArray(this._result.getString("windowSettings"));
        JSONArray slotMenu_position = new JSONArray(this._result.getString("slotmenu_position"));
        JSONArray resizableWindows  = new JSONArray(this._result.getString("resizableWindows"));
        JSONArray mainMenuPosition  = new JSONArray(this._result.getString("mainmenuPosition"));
        JSONArray slotMenu_order    = new JSONArray(this._result.getString("slotmenu_order"));

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
                this._result.getInt("clientResolution"),
                this._result.getString("quickbarSlot")
        );

        this._setSet(set);
        this._setMinimapSize(minimapSize);
        this._setWindowSettings(windowSettings);
        this._setSlotMenuPosition(slotMenu_position);
        this._setMainMenuPosition(mainMenuPosition);
        this._setResizableWindows(resizableWindows);
        this._setSlotMenuOrder(slotMenu_order);
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
                set.getInt("selectedLaser"),
                set.getInt("selectedRocket"),
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

    /**
     * Sets minimap sizes.
     *
     * @param sizes JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setMinimapSize(JSONArray sizes) throws JSONException
    {
        for(int i = 0; i < sizes.length(); i++) {
            ((Settings)this._object).addMinimapSize(i, sizes.getInt(i));
        }
    }

    /**
     * Sets windows settings.
     *
     * @param windows JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setWindowSettings(JSONArray windows) throws JSONException
    {
        for(int i = 0; i < windows.length(); i++) {
            ((Settings)this._object).addWindowSettings(i, windows.getString(i));
        }
    }

    /**
     * Sets slot menu positions.
     *
     * @param positions JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setSlotMenuPosition(JSONArray positions) throws JSONException
    {
        for(int i = 0; i < positions.length(); i++) {
            ((Settings)this._object).addSlotMenuPosition(i, positions.getString(i));
        }
    }

    /**
     * Sets main menu positions.
     *
     * @param positions JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setMainMenuPosition(JSONArray positions) throws JSONException
    {
        for(int i = 0; i < positions.length(); i++) {
            ((Settings)this._object).addMainMenuPosition(i, positions.getString(i));
        }
    }

    /**
     * Sets resizable windows.
     *
     * @param windows JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setResizableWindows(JSONArray windows) throws JSONException
    {
        for(int i = 0; i < windows.length(); i++) {
            ((Settings)this._object).addResizableWindows(i, windows.getString(i));
        }
    }

    /**
     * Sets slot menu orders.
     *
     * @param orders JSONArray
     *
     * @throws JSONException If couldn't parse the JSON
     */
    private void _setSlotMenuOrder(JSONArray orders) throws JSONException
    {
        for(int i = 0; i < orders.length(); i++) {
            ((Settings)this._object).addSlotMenuOrder(i, orders.getInt(i));
        }
    }
}
