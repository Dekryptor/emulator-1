package com.manulaiko.blackeye.launcher.commands;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * Stop command.
 *
 * Stops specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.launcher.commands
 */
public class StopCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    public String _name = "stop";

    /**
     * Command description
     */
    public String _description = "-stop [servers] [timeout]: Stops specified server(s).\n" +
                                "                           Possible values for 'servers' are:\n" +
                                "                               * game: Game server.\n" +
                                "                               * chat: Chat server.\n" +
                                "                               * sockswork: SocksWork server.\n" +
                                "                           If no server name is specified it will stop all servers.\n" +
                                "                           The timeout is the amount of milliseconds to wait for closing.\n" +
                                "                           the server, if it's omitted it will stop server immediately\n" +
                                "                           A server can't be stopped if it's not running.\n" +
                                "                           Example:\n" +
                                "                               stop game 2000 chat 2000\n" +
                                "                               stop sockswork\n" +
                                "                               stop 2000";

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

        if(args.length == 1) {
            ServerManager.stop("all");
        } else {
            try {
                int timeout = Integer.parseInt(args[1]);

                ServerManager.stop("all", timeout);
            } catch(Exception e) {
                for(int i = 1; i < args.length; i++) {
                    try {
                        int timeout = Integer.parseInt(args[(i + 1)]);

                        ServerManager.stop(args[i], timeout);
                        i++;
                    } catch(Exception e1) {
                        ServerManager.stop(args[i]);
                    }
                }
            }
        }
    }
}
