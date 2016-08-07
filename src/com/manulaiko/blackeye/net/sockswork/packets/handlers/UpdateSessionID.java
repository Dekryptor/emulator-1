package com.manulaiko.blackeye.net.sockswork.packets.handlers;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.simulator.account.Account;

import com.manulaiko.blackeye.net.sockswork.Connection;
import com.manulaiko.blackeye.net.sockswork.packets.Packet;
import com.manulaiko.blackeye.net.sockswork.packets.commands.UpdateSessionIDResponse;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * UpdateSessionID packet
 *
 * Updates given account's sessionID
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.sockswork.packets.handlers
 */
public class UpdateSessionID extends Packet
{
    /**
     * Returns packet name
     *
     * @return Packet name
     */
    public String getName()
    {
        return "UpdateSessionID";
    }

    /**
     * Handles the packet
     *
     * @param connection Connection that received the packet
     */
    public void handle(Connection connection)
    {
        int    accountID = this._packet.readInt();
        String sessionID = this._packet.readString();

        try {
            UpdateSessionIDResponse response = (UpdateSessionIDResponse) ServerManager.sockswork.packetFactory.getCommandByName("UpdateSessionIDResponse");
            response.status = 1;

            try {
                Account a = GameManager.accounts.getByID(accountID);
                a.sessionID = sessionID;

                Console.println("SessionID updated for account with ID "+ accountID);
            } catch(NotFound e) {
                Console.println("Account "+ accountID +" does not exists!");
                response.status = 0;
            }

            connection.send(response.toString());
        } catch(NotFound e) {
            //empty
        }
    }
}
