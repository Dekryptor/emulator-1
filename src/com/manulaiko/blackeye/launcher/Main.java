package com.manulaiko.blackeye.launcher;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.Configuration;
import com.manulaiko.tabitha.configuration.IniConfiguration;
import com.manulaiko.tabitha.database.Connection;
import com.manulaiko.tabitha.exceptions.database.ConnectionFailed;
import com.manulaiko.tabitha.utils.CommandPrompt;

/**
 * Main class.
 *
 * Sets up everything to start BlackEye servers.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Main
{
    /**
     * Emulator version
     */
    private static final String _version = "V 1.6.3.17 BUILD 181550";

    /**
     * Configuration object
     */
    public static IniConfiguration configuration;

    /**
     * Database connection object
     */
    public static Connection mysqlManager;

    /**
     * Application's entry point.
     *
     * It will load configuration file, establish a connection to the database,
     * load the database and start the GUI.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        Console.println("BlackEye emulator ", Main._version, " by Manulaiko.");
        Console.println(Console.LINE_EQ);
        Console.println("Setting everything before starting servers...");
        Console.println();

        //Load configuration file
        Console.println("Reading configuration file...");
        Console.println(Console.LINE_MINUS);
        if(!setConfiguration()) {
            Console.println("Couldn't read configuration file!");
            Console.println("Be sure it exists and it's correct.");
            Console.readLine();
            System.exit(0);
        }
        Console.println("Configuration file loaded!");
        Console.println(Console.LINE_EQ+"\n");

        //Connect to database
        Console.println("Connecting to MySQL database...");
        Console.println(Console.LINE_MINUS);
        if(!_connectToDatabase()) {
            Console.println("Couldn't connect to MySQL database!");
            Console.println("Be sure that MySQL server is running and the configuration file matches MySQL database login settings!");
            Console.readLine();
            System.exit(0);
        }
        Console.println("Connected to MySQL server!");
        Console.println(Console.LINE_EQ+"\n");

        //Initialize GameManager
        Console.println("Initializing GameManager...");
        if(!GameManager.initialize()) {
            Console.println("Couldn't load Database!");
            Console.println("Be sure that the database has all tables and rows.");
            Console.readLine();
            System.exit(0);
        }
        Console.println("GameManager initialized!");
        Console.println(Console.LINE_EQ+"\n");

        Console.println("Finished bootstrapping BlackEye!");
        Console.println(Console.LINE_EQ+"\n");

        //Start command prompt
        CommandPrompt cp = new CommandPrompt();
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.StartCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.StopCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.RestartCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.StatusCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.ReloadConfigurationCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.ReinitializeGameManagerCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.ExitCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.SaveCommand());
        cp.addCommand(new com.manulaiko.blackeye.launcher.commands.SendCommand());

        cp.start();
    }

    /**
     * Reads configuration file.
     *
     * @return True if configuration object was successfully instantiated, false if not.
     */
    public static boolean setConfiguration()
    {
        try {
            Main.configuration = Configuration.loadIni("config.ini");

            return true;
        } catch(Exception e) {
            Console.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Starts a connection to the MySQL database.
     *
     * @return True if connection succeeded, false if not.
     */
    private static boolean _connectToDatabase()
    {
        try {
            Main.mysqlManager = new Connection(
                    Main.configuration.getString("database.host"),
                    Main.configuration.getShort("database.port"),
                    Main.configuration.getString("database.username"),
                    Main.configuration.getString("database.password"),
                    Main.configuration.getString("database.name")
            );

            return true;
        } catch(ConnectionFailed e) {
            return false;
        }
    }
}
