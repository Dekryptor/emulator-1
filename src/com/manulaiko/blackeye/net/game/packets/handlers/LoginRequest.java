package com.manulaiko.blackeye.net.game.packets.handlers;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.*;

import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.Map;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * LoginRequest packet
 *
 * Handles a login request packet
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LoginRequest extends com.manulaiko.blackeye.net.game.packets.Packet
{
    /**
     * Handles the packet
     *
     * @param connection Connection object
     */
    public void handle(Connection connection)
    {
        int    accountID = this._packet.readInt();
        String sessionID = this._packet.readString();

        try {
            Account account = GameManager.accounts.getByID(accountID);

            if(account.sessionID.equals(sessionID) || sessionID.equalsIgnoreCase("test")) {
                connection.account = account;
                account.connection = connection;

                try {
                    this.sendLoginData(connection);
                } catch(Exception e) {
                    Console.println("Couldn't send login data!");
                    Console.println(e.getMessage());
                    e.printStackTrace();
                }
            } else {
                connection.send(
                        ServerManager.game.packetFactory.getByName("InvalidSession")
                                                        .toString()
                );
            }
        } catch(NotFound e) {
            Console.println("Account with ID " + accountID + " does not exist");
        }
    }

    /**
     * Sends login data
     *
     * @param connection Connection to send data
     */
    public void sendLoginData(Connection connection) throws NotFound
    {
        connection.send("0|A|SET|1|1|1|1|1|1|1|1|1|1|1|0|0|1|1|1|1|1|1|0|0|0|0|0|1");
        connection.send(connection.account.getShipInitializationCommand().toString());
        connection.send("0|m|1");
        connection.send("0|A|ADM|CLI|1");

        Map m = GameManager.maps.getByID(1);
        if(m.accounts.containsKey(connection.account.id)) {
            m.removeAccount(connection.account.id, false);
        }
        m.addAccount(connection);
    }
}
