package com.manulaiko.blackeye.launcher.commands;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * Status command.
 *
 * Shows current status of specified server(s), read description for more.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.launcher.commands
 */
public class StatusCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    public String _name = "status";

    /**
     * Command description
     */
    public String _description = "-status [server]: Shows current status of specified server(s).\n" +
                                "                  Possible values for 'servers' are:\n" +
                                "                      * game: Game server.\n" +
                                "                      * chat: Chat server.\n" +
                                "                      * sockswork: SocksWork server.\n" +
                                "                  If no server name is specified it will print status of all servers.\n" +
                                "                  Example:\n" +
                                "                      status game chat\n" +
                                "                      status sockswork\n" +
                                "                      status";

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
        if(args.length == 0) {
            ServerManager.showStatus("all");
        } else {
            for(int i = 0; i < args.length; i++) {
                ServerManager.showStatus(args[i]);
            }
        }
    }
}
