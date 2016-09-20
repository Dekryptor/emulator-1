package com.manulaiko.blackeye.launcher.commands;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.tabitha.Console;

/**
 * Send command.
 *
 * Send packets to the game/chat server
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SendCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name
     */
    public String _name = "send";

    /**
     * Command description
     */
    public String _description = "-send [server] [type] [id] packet: Sends a packet to specified id in specified server." +
                                 "                                   The `server` parameter can be `game`, `chat` or `all` (defaults to `all`)" +
                                 "                                   The `type` parameter is where to send the packet, possible values are `user` (or `account`), `map` and `all` (defaults to `all`)" +
                                 "                                   The `id` parameter is the identifier of the `type` parameter";

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
     * Pls, don't look :/
     *
     * @param args Command arguments
     */
    public void execute(String[] args)
    {
        if(args.length == 1) {
            Console.println(this.getDescription());

            return;
        }

        String server = "all";
        String type   = "all";
        String id     = "";
        String packet = "";

        if(args.length == 2) {
            packet = args[1];
        } else if(args.length == 3) {
            boolean isNumeric = false;
            try {
                Integer.parseInt(args[1]);
                isNumeric = true;
            } catch(Exception e) {
                // Le pro coda
            }

            if(
                args[1].equalsIgnoreCase("game") ||
                args[1].equalsIgnoreCase("chat")
            ) {
                server = args[1];
            } else if(
                isNumeric ||
                args[1].equalsIgnoreCase("all")
            ) {
                id = args[1];
            }

            packet = args[2];
        } else if(args.length == 4) {
            boolean isNumeric = false;
            try {
                Integer.parseInt(args[1]);
                isNumeric = true;
            } catch(Exception e) {
                // Le pro coda
            }

            if(
                args[1].equalsIgnoreCase("game") ||
                args[1].equalsIgnoreCase("chat")
            ) {
                server = args[1];
            } else if(
                isNumeric ||
                args[1].equalsIgnoreCase("all")
            ) {
                id = args[1];
            }

            if(
                args[2].equalsIgnoreCase("game") ||
                args[2].equalsIgnoreCase("chat")
            ) {
                server = args[2];
            } else if(
                isNumeric ||
                args[2].equalsIgnoreCase("all")
            ) {
                id = args[2];
            }

            packet = args[3];
        } else if(args.length >= 5) {
            server = args[1];
            type   = args[2];
            id     = args[3];
            packet = args[4];
        }

        if(server.equalsIgnoreCase("game")) {
            sendToGame(type, id, packet);
        } else if(server.equalsIgnoreCase("chat")) {
            //sendToChat(type, id, packet);
        } else {
            sendToGame(type, id, packet);
            //sendToChat(type, id, packet);
        }
    }

    /**
     * Sends a packet to the game server.
     *
     * @param type   What kind of identifier `id` is.
     * @param id     `type` identifier.
     * @param packet Packet to send.
     */
    public void sendToGame(String type, String id, String packet)
    {
        if(type.equalsIgnoreCase("map")) {
            if(id.equalsIgnoreCase("all")) {
                GameManager.maps.getAllMaps().forEach((i, map)->{
                    map.broadcastPacket(packet);
                });
            } else {
                try {
                    int mapID = Integer.parseInt(id);

                    Map m = GameManager.maps.getByID(mapID);
                    m.broadcastPacket(packet);
                } catch(Exception e) {
                    Console.println("Map with id "+ id +" does not exist!");
                }
            }
        } else if(
            type.equalsIgnoreCase("user") ||
            type.equalsIgnoreCase("account")
        ) {
            if(id.equalsIgnoreCase("all")) {
                GameManager.accounts.getAllAccounts().forEach((i, account)->{
                    if(account.connection != null) {
                        account.connection.send(packet);
                    }
                });
            } else {
                try {
                    int userID = Integer.parseInt(id);

                    Account a = GameManager.accounts.getByID(userID);
                    a.connection.send(packet); // Null exception will be handled down
                } catch(Exception e) {
                    Console.println("Account with id "+ id +" does not exist or isn't connected!");
                }
            }
        } else {
            GameManager.maps.getAllMaps().forEach((i, map)->{
                map.broadcastPacket(packet);
            });
        }
    }
}
