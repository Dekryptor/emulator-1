package com.manulaiko.blackeye.launcher.commands;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * Restart command.
 *
 * Restarts specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.launcher.commands
 */
public class RestartCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    public String _name = "restart";

    /**
     * Command description
     */
    public String _description = "-restart [servers] [timeout]: Restarts specified server(s).\n" +
                                "                              Possible values for 'servers' are:\n" +
                                "                                  * game: Game server.\n" +
                                "                                  * chat: Chat server.\n" +
                                "                                  * sockswork: SocksWork server.\n" +
                                "                              If no server name is specified it will restart all servers.\n" +
                                "                              The timeout is the amount of milliseconds to wait for restarting\n" +
                                "                              the server, if it's omitted it will restart server immediately.\n" +
                                "                              A server can't be restarted if it's not running.\n" +
                                "                              Example:\n" +
                                "                                  restart game 2000 chat 2000\n" +
                                "                                  restart sockswork\n" +
                                "                                  restart 2000";

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
            ServerManager.start("all");
        } else {
            try {
                int timeout = Integer.parseInt(args[1]);

                ServerManager.stop("all", timeout);
                ServerManager.start("all");
            } catch(Exception e) {
                for(int i = 1; i < args.length; i++) {
                    try {
                        int timeout = Integer.parseInt(args[(i + 1)]);
                        
                        ServerManager.stop(args[i], timeout);
                        ServerManager.start(args[i]);
                        i++;
                    } catch(Exception e1) {
                        ServerManager.stop(args[i]);
                        ServerManager.start(args[i]);
                    }
                }
            }
        }
    }
}
