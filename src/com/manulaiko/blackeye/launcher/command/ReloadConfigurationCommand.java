package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.configuration.IConfiguration;
import com.manulaiko.tabitha.Configuration;

/**
 * Reload configuration command.
 *
 * Reloads configuration file.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ReloadConfigurationCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    public String _name = "reload_configuration";

    /**
     * Command description
     */
    public String _description =
            "reload_configuration: Reloads the configuration.\n"+
            "                      This command helps you reload configuration file\n"+
            "                      without restarting BlackEye.\n"+
            "                      Some changes won't work until you restart BlackEye.";

    /**
     * Checks whether this command can execute 'name' command
     *
     * @param name Command name to check
     *
     * @return Whether this command can execute 'name'
     */
    public boolean canExecute(String name)
    {
        return this._name.equalsIgnoreCase(name);
    }

    /**
     * Returns command name
     *
     * @return Command name
     */
    public String getName()
    {
        return this._name;
    }

    /**
     * Returns command description
     *
     * @return Command description
     */
    public String getDescription()
    {
        return this._description;
    }

    /**
     * Executes the command
     *
     * @param args Command arguments
     */
    public void execute(String[] args)
    {
        Console.println("Reloading configuration file...");
        Console.println(Console.LINE_MINUS);
        if(this.setConfiguration()) {
            Console.println("Configuration file reinitialized!");

            return;
        }

        Console.println("Couldn't reload configuration file! Old settings will be used.");
        Console.println("Be sure it exists and it's correct.");
    }

    /**
     * Sets the configuration.
     *
     * @return `true` if configuration was properly updated, `false` if not.
     */
    public boolean setConfiguration()
    {
        try {
            IConfiguration configuration = Configuration.load(Main.configurationFileLocation);

            Main.configuration = configuration;
        } catch(Exception e) {
            return false;
        }

        return true;
    }
}
