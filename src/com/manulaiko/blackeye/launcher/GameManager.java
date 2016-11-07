package com.manulaiko.blackeye.launcher;

import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.map.Map;
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
    /**
     * Optimal time on which the game should be updated
     */
    public static final double OPTIMAL_TIME = 1000000000 / 60;

    //////////////////////////
    // Start Game Factories //
    //////////////////////////
    /**
     * Accounts table.
     *
     * @var Factory for `accounts` table.
     */
    public static com.manulaiko.blackeye.simulator.account.Factory accounts = new com.manulaiko.blackeye.simulator.account.Factory();

    /**
     * Levels table.
     *
     * @var Factory for `levels` table.
     */
    public static com.manulaiko.blackeye.simulator.level.Factory levels = new com.manulaiko.blackeye.simulator.level.Factory();

    /**
     * Clans table.
     *
     * @var Factory for `clans` table.
     */
    public static com.manulaiko.blackeye.simulator.clan.Factory clans = new com.manulaiko.blackeye.simulator.clan.Factory();

    /**
     * Maps table.
     *
     * @var Factory for `maps` table.
     */
    public static com.manulaiko.blackeye.simulator.map.Factory maps = new com.manulaiko.blackeye.simulator.map.Factory();

    /**
     * NPCs table.
     *
     * @var Factory for `npcs` table.
     */
    public static com.manulaiko.blackeye.simulator.npc.Factory npcs = new com.manulaiko.blackeye.simulator.npc.Factory();

    /**
     * Portals table.
     *
     * @var Factory for `maps_portals` table.
     */
    public static com.manulaiko.blackeye.simulator.map.portal.Factory portals = new com.manulaiko.blackeye.simulator.map.portal.Factory();

    /**
     * Collectables table.
     *
     * @var Factory for `collectables` table.
     */
    public static com.manulaiko.blackeye.simulator.map.collectable.Factory collectables = new com.manulaiko.blackeye.simulator.map.collectable.Factory();

    /**
     * Ships table.
     *
     * @var Factory for `ships` table.
     */
    public static com.manulaiko.blackeye.simulator.ship.Factory ships = new com.manulaiko.blackeye.simulator.ship.Factory();

    /**
     * Items table.
     *
     * @var Factory for `items` table.
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
    public static boolean initialize() throws Exception
    {
        boolean ret = true;

        Console.println("Initializing GameManager...");
        GameManager.loadNecessaryData();

        try {
            GameManager.loadAdditionalData();
        } catch(Exception e) {
            Console.println("Additional data couldn't be loaded!");
            if(Main.configuration.getBoolean("core.debug")) {
                Console.println("Exception message: "+ e.getMessage());
            }
            ret = false;
        }

        Console.println("Initializing updater thread...");
        Thread t = new Thread(new GameManager());
        t.setName("GameManager Updater");
        t.start();
        Console.println("Updater thread initialized!");

        Console.println("GameManager initialized!");

        return ret;
    }

    /**
     * Initializes factories with necessary data.
     *
     * @throws Exception If any of the factories couldn't be loaded.
     */
    public static void loadNecessaryData() throws Exception
    {
        Console.println("Loading necessary data...");
        Console.println(Console.LINE_EQ);

        Console.println("Loading levels...");
        GameManager.levels.initialize();
        Console.println(GameManager.levels.getAll().size() +" levels loaded!");
        Console.println(Console.LINE_MINUS);

        Console.println("Loading items...");
        GameManager.items.initialize();
        Console.println(GameManager.items.getAll().size() +" items loaded!");
        Console.println(Console.LINE_MINUS);

        Console.println("Loading ships...");
        GameManager.ships.initialize();
        Console.println(GameManager.ships.getAll().size() +" ships loaded!");
        Console.println(Console.LINE_MINUS);

        Console.println("Loading NPCs...");
        GameManager.npcs.initialize();
        Console.println(GameManager.npcs.getAll().size() +" NPCs loaded!");
        Console.println(Console.LINE_MINUS);

        Console.println("Loading maps...");
        GameManager.maps.initialize();
        Console.println(GameManager.maps.getAll().size() +" maps loaded!");
        Console.println(Console.LINE_MINUS);
    }

    /**
     * Initializes factories with additional data.
     *
     * @throws Exception If any of the factories couldn't be loaded.
     */
    public static void loadAdditionalData() throws Exception
    {
        Console.println("Loading additional data...");
        Console.println(Console.LINE_EQ);

        String[] additional = Main.configuration.getString("core.load_additional_data")
                                                .split(" ");
        for(String data : additional) {
            if(data.equalsIgnoreCase("accounts")) {
                Console.println("Loading accounts...");
                GameManager.accounts.initialize();
                Console.println(GameManager.accounts.getAll().size() +" accounts loaded!");
                Console.println(Console.LINE_MINUS);
            } else if(data.equalsIgnoreCase("clans")) {
                Console.println("Loading clans...");
                GameManager.clans.initialize();
                Console.println(GameManager.clans.getAll().size() +" clans loaded!");
                Console.println(Console.LINE_MINUS);
            } else if(data.equalsIgnoreCase("collectables")) {
                Console.println("Loading collectables...");
                GameManager.collectables.initialize();
                Console.println(GameManager.collectables.getAll().size() +" collectables loaded!");
                Console.println(Console.LINE_MINUS);
            }
        }
    }

    /**
     * Updates database's tables.
     */
    public static void save()
    {
        Console.println("Updating `accounts` table...");
        GameManager.accounts.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts` table successfully updated!");
        Console.println(Console.LINE_MINUS);

        Console.println("Updating `accounts_settings` table");
        GameManager.accounts.settings.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts_settings` table successfully updated!");
        Console.println(Console.LINE_MINUS);

        Console.println("Updating `accounts_equipment_configurations` table");
        GameManager.accounts.hangars.configurations.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts_equipment_configurations` table successfully updated!");
        Console.println(Console.LINE_MINUS);

        Console.println("Updating `accounts_equipment_ships` table");
        GameManager.accounts.hangars.ships.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts_equipment_ships` table successfully updated!");
        Console.println(Console.LINE_MINUS);

        Console.println("Updating `accounts_equipment_hangars` table");
        GameManager.accounts.hangars.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts_equipment_hangars` table successfully updated!");
        Console.println(Console.LINE_MINUS);

        Console.println("Updating `accounts_equipment_items` table");
        GameManager.accounts.items.getAll().forEach((i, s)->{
            ((Simulator)s).save();
        });
        Console.println("`accounts_equipment_items` table successfully updated!");
        Console.println(Console.LINE_MINUS);
    }

    /**
     * Updates the game
     */
    public void run()
    {
        boolean isRunning = true;
        double  delta     = 0D;

        long lastTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long lastTickDuration = now - lastTime;

            delta += lastTickDuration / GameManager.OPTIMAL_TIME;
            lastTime = now;

            while (delta >= 1) {
                this.update();
                delta--;
            }
        }
    }

    /**
     * Performs the update
     */
    public void update()
    {
        GameManager.maps.getAll().forEach((i, m) -> {
            //Console.println("Updating map "+ ((Map)m).id);
            Thread t = new Thread((Map)m);

            t.start();
        });
    }
}
