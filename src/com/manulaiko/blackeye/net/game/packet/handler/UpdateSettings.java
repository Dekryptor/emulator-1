package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;

/**
 * Update Settings handler.
 *
 * Handles Account's update settings packets.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class UpdateSettings extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        String[] category = this._packet.readString().split(",");
        String   newValue = this._packet.readString();

        if(category.length > 1) {
            this._handleResolutionSpecificSettings(connection, category[0], Integer.parseInt(category[1]), newValue);
            connection.account.settings.save();

            return;
        }

        this._handleSettings(connection, category[0], newValue);
        connection.account.settings.save();
    }

    /**
     * Handles resolution specific settings.
     *
     * @param connection Connection that received the packet.
     * @param category   Settings category.
     * @param resolution Settings resolution.
     * @param value      New settings value.
     */
    private void _handleResolutionSpecificSettings(Connection connection, String category, int resolution, String value)
    {
        switch(category)
        {
            case "MINIMAP_SCALE":
                connection.account.settings.addMinimapSize(resolution, Integer.parseInt(value));
                break;

            case "WINDOW_SETTINGS":
                connection.account.settings.addWindowSettings(resolution, value);
                break;

            case "RESIZABLE_WINDOWS":
                connection.account.settings.addResizableWindows(resolution, value);
                break;

            case "SLOTMENU_POSITION":
                connection.account.settings.addSlotMenuPosition(resolution, value);
                break;

            case "SLOTMENU_ORDER":
                connection.account.settings.addSlotMenuOrder(resolution, Integer.parseInt(value));
                break;

            case "MAINMENU_POSITION":
                connection.account.settings.addMainMenuPosition(resolution, value);
                break;
        }
    }

    /**
     * Handles settings.
     *
     * @param connection Connection that received the packet.
     * @param category   Settings category.
     * @param value      New settings value.
     */
    private void _handleSettings(Connection connection, String category, String value)
    {
        switch(category)
        {
            case "QUICKBAR_SLOT":
                connection.account.settings.quickbarSlot = value;
                break;

            case "BAR_STATUS":
                connection.account.settings.barStatus = value;
                break;

            case "AUTO_REFINEMENT":
                connection.account.settings.autoRefinement = Boolean.parseBoolean(value);
                break;

            case "QUICKSLOT_STOP_ATTACK":
                connection.account.settings.quickSlotStopAttack = Boolean.parseBoolean(value);
                break;

            case "DOUBLECLICK_ATTACK":
                connection.account.settings.doubleClickAttack = Boolean.parseBoolean(value);
                break;

            case "AUTO_START":
                connection.account.settings.autoStart = Boolean.parseBoolean(value);
                break;

            case "DISPLAY_NOTIFICATIONS":
                connection.account.settings.display_notification = Boolean.parseBoolean(value);
                break;

            case "SHOW_DRONES":
                connection.account.settings.display_drones = Boolean.parseBoolean(value);
                break;

            case "DISPLAY_WINDOW_BACKGROUND":
                connection.account.settings.display_windowBackground = Boolean.parseBoolean(value);
                break;

            case "PRELOAD_USER_SHIPS":
                connection.account.settings.preloadUserShips = Boolean.parseBoolean(value);
                break;

            case "QUALITY_CUSTOMIZED":
                connection.account.settings.quality_customized = Integer.parseInt(value);
                break;

            case "QUALITY_BACKGROUND":
                connection.account.settings.quality_background = Integer.parseInt(value);
                break;

            case "QUALITY_POIZONE":
                connection.account.settings.quality_poizone = Integer.parseInt(value);
                break;

            case "QUALITY_SHIP":
                connection.account.settings.quality_ship = Integer.parseInt(value);
                break;

            case "QUALITY_ENGINE":
                connection.account.settings.quality_engine = Integer.parseInt(value);
                break;

            case "QUALITY_COLLECTABLE":
                connection.account.settings.quality_collectable = Integer.parseInt(value);
                break;

            case "QUALITY_ATTACK":
                connection.account.settings.quality_attack = Integer.parseInt(value);
                break;

            case "QUALITY_EFFECT":
                connection.account.settings.quality_effect = Integer.parseInt(value);
                break;

            case "QUALITY_EXPLOSION":
                connection.account.settings.quality_explosion = Integer.parseInt(value);
                break;

            case "CLIENT_RESOLUTION":
                String[] params = value.split(",");

                if(this._packet.readInt() == 1) {
                    connection.account.settings.clientResolution = Integer.parseInt(params[0]);
                }
                break;
        }
    }
}
