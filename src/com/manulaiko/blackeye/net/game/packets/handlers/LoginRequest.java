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
 *
 * @package com.manulaiko.blackeye.net.game.packets.handlers
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
        ShipInitialization p = (ShipInitialization) ServerManager.game.packetFactory.getCommandByName("ShipInitialization");

        p.id         = connection.account.id;
        p.name       = connection.account.name;
        p.shipID     = connection.account.hangar.ship.id;
        p.speed      = connection.account.hangar.getSpeed();
        p.shield     = connection.account.hangar.getShield();
        p.maxShield  = connection.account.hangar.getMaxShield();
        p.health     = connection.account.hangar.getHealth();
        p.maxHealth  = connection.account.hangar.getMaxHealth();
        p.cargo      = connection.account.hangar.getCargo();
        p.maxCargo   = connection.account.hangar.getMaxCargo();
        p.x          = (int)connection.account.hangar.ship.position.getX();
        p.y          = (int)connection.account.hangar.ship.position.getY();
        p.mapID      = connection.account.hangar.ship.mapID;
        p.factionID  = connection.account.factionsID;
        p.clanID     = connection.account.clan.id;
        p.batteries  = connection.account.hangar.getBatteriesAmount();
        p.rockets    = connection.account.hangar.getRocketsAmount();
        p.oState     = connection.account.hangar.getExpansions();
        p.isPremium  = connection.account.isPremium;
        p.experience = connection.account.experience;
        p.honor      = connection.account.honor;
        p.levelID    = connection.account.level.id;
        p.credits    = connection.account.credits;
        p.uridium    = connection.account.uridium;
        p.jackpot    = connection.account.jackpot;
        p.rankID     = connection.account.ranksID;
        p.clanTag    = connection.account.clan.tag;
        p.ggRings    = 4;
        p.useSysFont = 0; //No idea

        connection.send("0|A|SET|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1");
        connection.send(p.toString());
        connection.send("0|m|1");
        connection.send("0|A|ADM|CLI|1");

        Map m = GameManager.maps.getByID(1);
        if(m.accounts.containsKey(connection.account.id)) {
            m.removeAccount(connection.account.id, false);
        }
        m.addAccount(connection);
    }
}
