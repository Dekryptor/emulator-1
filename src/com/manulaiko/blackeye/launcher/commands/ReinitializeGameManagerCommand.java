package com.manulaiko.blackeye.launcher.commands;

/**
 * Reload configuration command.
 *
 * Start specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.launcher.commands
 */
public class ReinitializeGameManagerCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    private String _name = "reinitialize_gameManager";

    /**
     * Command description
     */
    private String _description = "-reinitialize_gameManager: Reinitializes GameManager.\n"+
                                  "                           This command helps you reinitialize GameManager\n"+
                                  "                           without restarting BlackEye.\n";

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
        com.manulaiko.tabitha.Console.println("Reinitializing GameManager...");
        com.manulaiko.tabitha.Console.println(com.manulaiko.tabitha.Console.LINE_MINUS);
        if(!com.manulaiko.blackeye.launcher.GameManager.initialize()) {
            com.manulaiko.tabitha.Console.println("Couldn't load Database!");
            com.manulaiko.tabitha.Console.println("Be sure that the database has all tables and rows.");
        } else {
            com.manulaiko.tabitha.Console.println("GameManager reinitialized!");
        }
    }
}
