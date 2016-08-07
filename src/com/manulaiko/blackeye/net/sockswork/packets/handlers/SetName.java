package com.manulaiko.blackeye.net.sockswork.packets.handlers;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * SetName packet
 *
 * Sets account's name
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.sockswork.packets.handlers
 */
public class SetName extends com.manulaiko.blackeye.net.sockswork.packets.Packet
{
    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "SetName";
    }

    /**
     * Handles the packet
     *
     * @param connection Connection that received the packet
     */
    public void handle(com.manulaiko.blackeye.net.sockswork.Connection connection)
    {
        int accountID = this._packet.readInt();
        String name   = this._packet.readString();

        try {
            com.manulaiko.blackeye.simulator.account.Account a = com.manulaiko.blackeye.launcher.GameManager.accounts.getByID(accountID);

            a.name = name;

            com.manulaiko.blackeye.net.sockswork.packets.commands.SetNameResponse response = (com.manulaiko.blackeye.net.sockswork.packets.commands.SetNameResponse) ServerManager.sockswork.packetFactory.getCommandByName("SetNameResponse");
            response.status = 1;

            connection.send(response.toString());
        } catch(com.manulaiko.tabitha.exceptions.NotFound e) {
            com.manulaiko.tabitha.Console.println(e.getMessage());

            try {
                com.manulaiko.blackeye.net.sockswork.packets.commands.SetNameResponse response = (com.manulaiko.blackeye.net.sockswork.packets.commands.SetNameResponse) ServerManager.sockswork.packetFactory.getCommandByName("SetNameResponse");
                response.status = 0;

                connection.send(response.toString());
            } catch(com.manulaiko.tabitha.exceptions.NotFound e1) {
                com.manulaiko.tabitha.Console.println(e1.getMessage());
            }
        }
    }
}
