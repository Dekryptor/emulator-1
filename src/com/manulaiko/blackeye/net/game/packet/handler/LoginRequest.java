package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.utils.Packet;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.tabitha.Console;

/**
 * Login request.
 *
 * Handles the LoginRequest packet.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LoginRequest extends Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        int id           = this._packet.readInt();
        String sessionID = this._packet.readString();
        Account account  = this._loadAccount(id, sessionID);

        if(account == null) {
            // Send error
            connection.send("ERR|2");
        }

        account.connection = connection;
        connection.account = account;

        connection.send("0|A|SET|1|1|1|1|1|1|1|1|1|1|1|0|0|1|1|1|1|1|1|0|0|0|0|0|1");
        connection.send(account.getShipInitializationCommand());
        connection.send("0|m|"+ account.hangar.ship.mapID);

        if(account.rankID >= 21) {
            connection.send("0|A|ADM|CLI|1");
        }

        account.settings.getCommands().forEach((c)->{
            connection.send(c);
        });

        account.hangar.ship.map.addAccount(account);
    }

    /**
     * Loads the account.
     *
     * @param id Account ID.
     * @param sessionID Session ID.
     *
     * @return Account.
     */
    private Account _loadAccount(int id, String sessionID)
    {
        Account account;

        try {
            if(sessionID.equalsIgnoreCase("test")) {
                account = (Account)GameManager.accounts.getByID(id);
            } else {
                account = GameManager.accounts.getBySessionID(sessionID);
            }
        } catch(Exception e) {
            Console.println("Account with ID "+ id +" or SessionID "+ sessionID +" does not exist!");

            return null;
        }

        // Check that id and session id belongs to the account.
        if(
            !sessionID.equalsIgnoreCase("test") &&
            !account.sessionID.equalsIgnoreCase(sessionID) &&
            account.id != id
        ) {
            return null;
        }

        return account;
    }
}
