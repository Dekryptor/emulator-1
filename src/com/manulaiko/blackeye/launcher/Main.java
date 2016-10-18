package com.manulaiko.blackeye.launcher;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.Configuration;
import com.manulaiko.tabitha.configuration.IConfiguration;
import com.manulaiko.tabitha.database.Connection;
import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.exceptions.database.ConnectionFailed;
import com.manulaiko.tabitha.utils.CommandPrompt;

/**
 * Main class.
 *
 * Contains `public static void main(String[] args)` which
 * will bootstrap BlackEye.
 *
 * Bootstrap flow:
 *  - Read configuration file.
 *  - Database connection.
 *  - Initialize GameManager.
 *  - Start CLI.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Main
{
    /**
     * Emulator version.
     *
     * Used for printing welcome message (and add more cool and useless
     * commits while creating new releases with gitflow).
     *
     * @var Emulator version.
     */
    public static final String version = "0.0.0";

    /**
     * Configuration file location.
     *
     * The relative path of the configuration file being the location
     * where the emulator was launched the starting point.
     *
     * By default it's `config.ini`.
     *
     * @var Configuration file location.
     */
    public static final String configurationFileLocation = "config.ini";

    /**
     * Configuration object.
     *
     * Instance of `com.manulaiko.tabitha.configuration.IConfiguration` with the
     * configuration file.
     *
     * @var Configuration object.
     */
    public static IConfiguration configuration;

    /**
     * Database connection.
     *
     * Connection object used for interacting with the database.
     *
     * @var Database connection.
     */
    public static Connection database;

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        Console.println("BlackEye "+ Main.version +" by Manulaiko");
        Console.println(Console.LINE_EQ);

        // 1st stage: Configuration file
        try {
            Console.println("Stage 1: Reading configuration file...");

            Main.configuration = Configuration.load(Main.configurationFileLocation);
        } catch(NotFound e) {
            Main.exit("Be sure that the configuration file is located in `"+ Main.configurationFileLocation +"`");
        } catch(Exception e) {
            Console.println("There was a problem reading configuration file.");
            Main.exit(e.getMessage());
        }

        // 2nd stage: Database
        try {
            Console.println("Stage 2: Connecting to the database...");

            Main.database = new Connection(
                    Main.configuration.getString("database.host"),
                    Main.configuration.getShort("database.port"),
                    Main.configuration.getString("database.username"),
                    Main.configuration.getString("database.password"),
                    Main.configuration.getString("database.name")
            );
        } catch(ConnectionFailed e) {
            Main.exit(e.getMessage());
        }

        // 3rd stage: GameManager
        try {
            Console.println("Stage 3: Initializing GameManager...");

            // TODO make GameManager class
            if(!GameManager.initialize()) {
                Console.println("Some data couldn't be loaded, check that database was properly imported.");
                Console.println("BlackEye will continue executing, however it may (and proably will) fail. Good luck ;)");
            }
        } catch(Exception e) {
            Main.exit(e.getMessage());
        }

        // 4th stage: CLI
        CommandPrompt cp = new CommandPrompt();
        // TODO add commands, obviusly
        cp.start();
    }

    /**
     * Terminates remaining actions and finishes execution.
     *
     * @param message Message to print before exit.
     * @param level   Exit level.
     */
    public static void exit(String message, int level)
    {
        Console.println(message);
        Console.readLine();
        System.exit(level);
    }

    /**
     * Shortcut of `exit`.
     *
     * @param message Message to print before exit.
     */
    public static void exit(String message)
    {
        Main.exit(message, 0);
    }
}
