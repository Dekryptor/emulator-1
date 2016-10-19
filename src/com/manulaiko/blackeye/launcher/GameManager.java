package com.manulaiko.blackeye.launcher;

import com.manulaiko.tabitha.Console;

/**
 * Game Manager.
 *
 * Loads the factories that will load from the database.
 *
 * Essential data.
 *  - Levels.
 *  - Maps.
 *  - Items.
 *  - Ships.
 *
 * Alternative data.
 *  - NPCS.
 *  - Collectables.
 *  - Portals.
 *  - Clans.
 *  - Accounts.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class GameManager extends Thread
{
    //////////////////////////
    // Start Game Factories //
    //////////////////////////
    /**
     * Accounts table
     */
    public static com.manulaiko.blackeye.simulator.account.Factory accounts = new com.manulaiko.blackeye.simulator.account.Factory();

    /**
     * Levels table
     */
    public static com.manulaiko.blackeye.simulator.level.Factory levels = new com.manulaiko.blackeye.simulator.level.Factory();

    /**
     * Clans table
     */
    public static com.manulaiko.blackeye.simulator.clan.Factory clans = new com.manulaiko.blackeye.simulator.clan.Factory();

    /**
     * Maps table
     */
    public static com.manulaiko.blackeye.simulator.map.Factory maps = new com.manulaiko.blackeye.simulator.map.Factory();

    /**
     * NPCs table
     */
    public static com.manulaiko.blackeye.simulator.npc.Factory npcs = new com.manulaiko.blackeye.simulator.npc.Factory();

    /**
     * Portals table
     */
    public static com.manulaiko.blackeye.simulator.map.portal.Factory portals = new com.manulaiko.blackeye.simulator.map.portal.Factory();

    /**
     * Collectables table
     */
    public static com.manulaiko.blackeye.simulator.map.collectable.Factory collectables = new com.manulaiko.blackeye.simulator.map.collectable.Factory();

    /**
     * Ships table
     */
    public static com.manulaiko.blackeye.simulator.ship.Factory ships = new com.manulaiko.blackeye.simulator.ship.Factory();

    /**
     * Items table
     */
    public static com.manulaiko.blackeye.simulator.item.Factory items = new com.manulaiko.blackeye.simulator.item.Factory();
    ////////////////////////
    // End Game Factories //
    ////////////////////////

    /**
     * Loads database data.
     *
     * First loads the necessary tables and the loads the additional
     * tables specified in configuration file by `core.load_additional_tables`.
     *
     * Necessary tables:
     *  * levels
     *  * items
     *  * ships
     *  * maps
     *
     * Additional tables:
     *  * accounts
     *  * npcs
     *  * clans
     *  * collectables
     *
     * @return `true` if additional data was successfully loaded, `false` if not.
     *
     * @throws Exception If essential data couldn't be loaded.
     */
    public static boolean initialize()
    {
        boolean ret = true;

        // TODO Factories and flow

        return ret;
    }

    /**
     * Updates database's tables
     */
    public static void save()
    {
        // TODO update database with changes
    }
}
