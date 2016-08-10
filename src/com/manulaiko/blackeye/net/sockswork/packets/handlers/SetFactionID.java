package com.manulaiko.blackeye.net.sockswork.packets.handlers;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.simulator.account.Account;

import com.manulaiko.blackeye.net.sockswork.Connection;
import com.manulaiko.blackeye.net.sockswork.packets.Packet;
import com.manulaiko.blackeye.net.sockswork.packets.commands.SetFactionIDResponse;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * SetFactionID packet
 *
 * Sets account's factionID
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SetFactionID extends Packet
{
    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "SetFactionID";
    }

    /**
     * Handles the packet
     *
     * @param connection Connection that received the packet
     */
    public void handle(Connection connection)
    {
        int accountID = this._packet.readInt();
        int factionID = this._packet.readInt();

        try {
            Account a = GameManager.accounts.getByID(accountID);

            a.factionsID = factionID;

            SetFactionIDResponse response = (SetFactionIDResponse) ServerManager.sockswork.packetFactory.getCommandByName("SetFactionIDResponse");
            response.status = 1;

            connection.send(response.toString());
        } catch(NotFound e) {
            Console.println(e.getMessage());

            try {
                SetFactionIDResponse response = (SetFactionIDResponse) ServerManager.sockswork.packetFactory.getCommandByName("SetFactionIDResponse");
                response.status = 0;

                connection.send(response.toString());
            } catch(NotFound e1) {
                Console.println(e1.getMessage());
            }
        }
    }
}
