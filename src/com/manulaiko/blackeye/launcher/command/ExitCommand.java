package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.tabitha.Console;

/**
 * Exit command.
 *
 * Start specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ExitCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    private String _name = "exit";

    /**
     * Command description.
     *
     * @var Description.
     */
    private String _description = "exit: Closes BlackEye.";

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
        Console.println("Stopping servers...");
        ServerManager.stop("all");
        Console.println("Updating database...");
        GameManager.save();
        System.exit(0);
    }
}
