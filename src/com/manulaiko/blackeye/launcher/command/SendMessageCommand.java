package com.manulaiko.blackeye.launcher.command;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.StandardMessage;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.Map;

/**
 * SendMessage command.
 *
 * Sends an in-game message.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SendMessageCommand implements com.manulaiko.tabitha.utils.ICommand
{
    /**
     * Command name.
     *
     * @var Name.
     */
    private String _name = "sendMessage";

    /**
     * Command description.
     *
     * @var Description.
     */
    private String _description = "sendMessage ([accountID|'all'|'map']) ([mapID]) message: Sends an in-game message." +
                                  "                                                         The parameter can be an integer (`accountID`)" +
                                  "                                                         or a string." +
                                  "                                                         If the parameter is `map` the message will be sent to" +
                                  "                                                         all users on `mapID`, else it will be sent to everyone." +
                                  "                                                         Example:" +
                                  "                                                             sendMessage Closing BlackEye..." +
                                  "                                                             sendMessage map 1 Everyone on map 1 is banned";

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
        if(args.length <= 1) {
            return;
        }

        if(args[1].equals("all")) {
            String message = "";
            for(int i = 2; i < args.length; i++) {
                message += args[i] + " ";
            }

            this._sendMessageToAll(message);

            return;
        } else if(args[1].equals("map")) {
            try {
                int mapID = Integer.parseInt(args[2]);

                Map m = (Map)GameManager.maps.getByID(mapID);

                String message = "";
                for(int i = 2; i < args.length; i++) {
                    message += args[i] + " ";
                }

                this._sendMessageToMap(m, message);
            } catch(Exception e) {
                // Ignore
            }

            return;
        }

        try {
            int id = Integer.parseInt(args[1]);

            Account a = (Account)GameManager.accounts.getByID(id);

            if(a.connection != null) {
                String message = "";
                for(int i = 2; i < args.length; i++) {
                    message += args[i] + " ";
                }

                this._sendMessageToAccount(a.connection, message);
            }
        } catch(NumberFormatException e) {
            String message = "";
            for(int i = 2; i < args.length; i++) {
                message += args[i] + " ";
            }

            this._sendMessageToAll(message);
        } catch(Exception e) {
            // Ignore
        }
    }

    /**
     * Sends a message to everyone.
     *
     * @param message Message to send.
     */
    private void _sendMessageToAll(String message)
    {
        StandardMessage p = (StandardMessage)ServerManager.game.packetFactory.getCommandByName("StandardMessage");

        p.message = message;

        GameManager.accounts.getAll().forEach((i, a)->{
            if(((Account)a).connection != null) {
                ((Account)a).connection.send(p);
            }
        });
    }

    /**
     * Sends a message to an account.
     *
     * @param connection Account connection.
     * @param message    Message to send.
     */
    private void _sendMessageToAccount(Connection connection, String message)
    {
        StandardMessage p = (StandardMessage)ServerManager.game.packetFactory.getCommandByName("StandardMessage");

        p.message = message;

        connection.send(p);
    }

    /**
     * Sends a message to a map.
     *
     * @param map     Map to send the message.
     * @param message Message to send.
     */
    private void _sendMessageToMap(Map map, String message)
    {
        StandardMessage p = (StandardMessage)ServerManager.game.packetFactory.getCommandByName("StandardMessage");

        p.message = message;

        map.broadcastPacket(p.toString());
    }
}
