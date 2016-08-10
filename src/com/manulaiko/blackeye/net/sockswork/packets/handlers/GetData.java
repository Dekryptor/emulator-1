package com.manulaiko.blackeye.net.sockswork.packets.handlers;

import com.manulaiko.blackeye.launcher.ServerManager;

/**
 * GetData packet
 *
 * Sets account's data
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class GetData extends com.manulaiko.blackeye.net.sockswork.packets.Packet
{
    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "GetData";
    }

    /**
     * Handles the packet
     *
     * @param connection Connection that received the packet
     */
    public void handle(com.manulaiko.blackeye.net.sockswork.Connection connection)
    {
        int accountID = this._packet.readInt();

        try {
            com.manulaiko.blackeye.simulator.account.Account a = com.manulaiko.blackeye.launcher.GameManager.accounts.getByID(accountID);

            com.manulaiko.blackeye.net.sockswork.packets.commands.SendData response = (com.manulaiko.blackeye.net.sockswork.packets.commands.SendData) ServerManager.sockswork.packetFactory.getCommandByName("SendData");
            response.experience = a.experience;
            response.honor      = a.honor;
            response.credits    = a.credits;
            response.uridium    = a.uridium;
            response.level      = a.levelsID;
            response.jackpot    = a.jackpot;

            connection.send(response.toString());
        } catch(com.manulaiko.tabitha.exceptions.NotFound e) {
            com.manulaiko.tabitha.Console.println(e.getMessage());

            try {
                com.manulaiko.blackeye.net.sockswork.packets.commands.SendData response = (com.manulaiko.blackeye.net.sockswork.packets.commands.SendData) ServerManager.sockswork.packetFactory.getCommandByName("SendData");

                connection.send(response.toString());
            } catch(com.manulaiko.tabitha.exceptions.NotFound e1) {
                com.manulaiko.tabitha.Console.println(e1.getMessage());
            }
        }
    }
}
