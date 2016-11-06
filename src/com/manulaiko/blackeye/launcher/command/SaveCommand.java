package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.tabitha.Console;

/**
 * Save command.
 *
 * Updates database, read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SaveCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    private String _name = "save";

    /**
     * Command description.
     *
     * @var Description.
     */
    private String _description = "save: Updates DataBase with GameManager current data.";

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
        GameManager.save();
    }
}
