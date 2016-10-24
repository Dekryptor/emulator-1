package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * Start command.
 *
 * Start specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class StartCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    public String _name = "start";

    /**
     * Command description.
     *
     * @var Description.
     */
    public String _description =
            "start [servers]: Starts specified server(s).\n" +
            "                 Possible values for 'servers' are:\n" +
            "                  * game: Game server.\n" +
            "                  * chat: Chat server.\n" +
            "                  * sockswork: SocksWork server.\n" +
            "                 If no server name is specified it will start all servers.\n" +
            "                 A server can't be started if it's already running.\n" +
            "                 Example:\n" +
            "                     start game chat\n" +
            "                     start sockswork\n" +
            "                     start\n";

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
        if(args.length != 1) {
            for(int i = 1; i < args.length; i++) {
                ServerManager.start(args[i]);
            }

            return;
        }

        ServerManager.start("all");
    }
}
