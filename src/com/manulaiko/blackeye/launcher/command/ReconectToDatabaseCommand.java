package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.database.Connection;

/**
 * Reconnect to Database command.
 *
 * Reconnects to database, read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ReconectToDatabaseCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    public String _name = "reconnect_to_database";

    /**
     * Command description.
     *
     * @var Description.
     */
    public String _description =
            "reconnect_to_database [reinitialize GameManager]: Reconnects to the database server.\n"+
            "                                                  This command helps you reconnect to the database\n"+
            "                                                  without restarting BlackEye.\n"+
            "                                                  If a parameter is specified, GameManager will be reinitialized.\n";

    /**
     * Checks whether this command can execute 'name' command.
     *
     * @param name Command name to check.
     *
     * @return Whether this command can execute 'name'.
     */
    public boolean canExecute(String name)
    {
        return this._name.equalsIgnoreCase(name);
    }

    /**
     * Returns command name.
     *
     * @return Command name.
     */
    public String getName()
    {
        return this._name;
    }

    /**
     * Returns command description.
     *
     * @return Command description.
     */
    public String getDescription()
    {
        return this._description;
    }

    /**
     * Executes the command.
     *
     * @param args Command arguments.
     */
    public void execute(String[] args)
    {
        Console.println("Reconnecting to database...");
        Console.println(Console.LINE_MINUS);
        if(!this.connectToDatabase()) {
            Console.println("Couldn't reconnect to database! Old connection will be used.");
            Console.println("Be sure it exists and it's correct.");

            return;
        }

        Console.println("Reconnected to database!");

        if(args.length > 1) {
            ReinitializeGameManagerCommand c = new ReinitializeGameManagerCommand();
            c.execute(args);
        }
    }

    /**
     * Reconnects to the database.
     *
     * @return `true` if connection succeeded, `false` if not.
     */
    public boolean connectToDatabase()
    {
        try {
            Connection database = new Connection(
                    Main.configuration.getString("database.host"),
                    Main.configuration.getShort("database.port"),
                    Main.configuration.getString("database.username"),
                    Main.configuration.getString("database.password"),
                    Main.configuration.getString("database.name")
            );

            Main.database = database;
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
