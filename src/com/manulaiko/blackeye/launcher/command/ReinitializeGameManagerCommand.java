package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.tabitha.Console;

/**
 * Reinitialize GameManager command.
 *
 * Reinitializes GameManager.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ReinitializeGameManagerCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    private String _name = "reinitialize_gameManager";

    /**
     * Command description.
     *
     * @var Description.
     */
    private String _description =
            "reinitialize_gameManager: Reinitialize GameManager.\n"+
            "                          This command helps you reinitialize GameManager\n"+
            "                          without restarting BlackEye.\n";

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
        Console.println("Reinitializing GameManager...");
        Console.println(com.manulaiko.tabitha.Console.LINE_MINUS);

        try {
            if(!GameManager.initialize()) {
                Console.println("Some data couldn't be loaded, check that database was properly imported.");
                Console.println("BlackEye will continue executing, however it may (and probably will) fail. Good luck ;)");
            }

            Console.println("GameManager reinitialized!");
        } catch(Exception e) {
            Console.println("Couldn't reinitialize GameManager!");
            Console.println(e.getMessage());
        }
    }
}
