package com.manulaiko.blackeye.launcher;

import com.manulaiko.tabitha.Console;

/**
 * Game Manager
 *
 * Loads database and manages the data used by the servers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class GameManager
{
    ///////////////////////////
    // Start Collection Maps //
    ///////////////////////////
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
    /////////////////////////
    // End Collection Maps //
    /////////////////////////

    /**
     * Loads database data.
     *
     * First loads the necessary tables and the loads the additional
     * tables specified in configuration file by `core.load_additional_tables`.
     *
     * Necessary tables:
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
     * @return True if all data loaded successfully, false if not.
     */
    public static boolean initialize()
    {
        try {
            Console.println("Loading database...");
            Console.println(Console.LINE_MINUS);

            Console.println("Loading data: Items...");
            GameManager.items.loadAll();
            Console.println(GameManager.items.getAmount() +" items loaded!");

            Console.println("Loading data: Levels...");
            GameManager.levels.loadAll();
            Console.println(GameManager.levels.getAmount() +" levels loaded!");

            Console.println("Loading data: Ships...");
            GameManager.ships.loadAll();
            Console.println(GameManager.ships.getAmount() +" ships loaded!");

            Console.println("Loading data: NPCs...");
            GameManager.npcs.loadAll();
            Console.println(GameManager.npcs.getAmount() + " NPCs loaded!");

            Console.println("Loading data: Portals...");
            GameManager.portals.loadAll();
            Console.println(GameManager.portals.getAmount() + " Portals loaded!");

            Console.println("Loading data: Collectables...");
            GameManager.collectables.loadAll();
            Console.println(GameManager.collectables.getAmount() +" collectables loaded!");

            Console.println("Loading data: Maps...");
            GameManager.maps.loadAll();
            Console.println(GameManager.maps.getAmount() +" maps loaded!");

            //Load additional data from configuration file
            String[] additional = Main.configuration.getString("core.load_additional_data")
                                                    .split(" ");
            for(String data : additional) {
                if(data.equalsIgnoreCase("accounts")) {
                    Console.println("Loading additional data: Accounts...");
                    GameManager.accounts.loadAll();
                    Console.println(GameManager.accounts.getAmount() +" accounts loaded!");
                } else if(data.equalsIgnoreCase("clans")) {
                    Console.println("Loading additional data: Clans...");
                    GameManager.clans.loadAll();
                    Console.println(GameManager.clans.getAmount() +" clans loaded!");
                }
            }
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Updates database's tables
     */
    public static void save()
    {
        Console.println("TODO, complete the GameManager.save method");
    }
}
