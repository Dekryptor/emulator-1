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

        // Send settings
        account.settings.getCommands().forEach((c)->{
            //connection.send(c);
        });
        
        // For the sole propose of testing, I promis I'll clean this :/
        connection.send("0|TX|S|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0");
        connection.send("0|A|SET|1|1|1|1|1|1|1|1|1|1|1|0|0|1|1|1|2|1|1|0|0|1|0|1|1");
        connection.send("0|7|AUTO_START|1");
        connection.send("0|7|SHOW_DRONES|1");
        connection.send("0|7|SLOTMENU_ORDER,0|0");
        connection.send("0|7|CLIENT_RESOLUTION|0,820,600");
        connection.send("0|7|DISPLAY_ENGINE_WASTE|0");
        connection.send("0|7|MAINMENU_POSITION,0|320,502");
        connection.send("0|7|MAINMENU_POSITION,1|349,480");
        connection.send("0|7|MAINMENU_POSITION,2|394,622");
        connection.send("0|7|SEL_HST|9");
        connection.send("0|7|DOUBLECLICK_ATTACK|1");
        connection.send("0|7|MAINMENU_POSITION,3|514,623");
        connection.send("0|7|WINDOW_SETTINGS,3|0,444,-1,0,1,1057,329,1,20,39,530,0,3,1021,528,1,5,-10,-6,0,24,463,15,0,10,101,307,0,36,100,400,0,13,315,122,0,23,1067,132,0");
        connection.send("0|7|WINDOW_SETTINGS,2|0,263,2,1,1,488,1,1,15,724,5,1,3,1016,458,0,5,5,5,0,24,500,61,0,10,-2,155,1,20,-1,382,1,13,187,50,0,23,838,213,1");
        connection.send("0|7|SIMPLE_SHIPS|0");
        connection.send("0|7|BAR_STATUS|32,1,34,0,35,0,23,1,24,1,25,1,26,1,27,1,39,0");
        connection.send("0|7|RESIZABLE_WINDOWS,3|5,240,150,20,321,171");
        connection.send("0|7|DISPLAY_WINDOW_BACKGROUND|0");
        connection.send("0|7|RESIZABLE_WINDOWS,2|5,240,150,20,308,246");
        connection.send("0|7|PRELOAD_USER_SHIPS|1");
        connection.send("0|7|SLOTMENU_POSITION,3|478,593");
        connection.send("0|7|RESIZABLE_WINDOWS,0|5,251,142,20,303,217");
        connection.send("0|7|AUTO_REFINEMENT|0");
        connection.send("0|7|SLOTMENU_POSITION,1|312,451");
        connection.send("0|7|WINDOW_SETTINGS,1|0,271,38,0,1,481,4,0,24,500,61,0,3,1016,458,1,5,-3,-9,0,10,-2,155,1,20,2,390,1,13,187,50,0,23,835,201,1");
        connection.send("0|7|SLOTMENU_POSITION,2|358,592");
        connection.send("0|7|WINDOW_SETTINGS,0|0,18,9,1,1,244,14,1,24,173,63,0,3,558,406,1,5,7,5,0,10,89,198,0,20,-10,370,1,13,85,62,0,23,604,135,1");
        connection.send("0|7|SLOTMENU_POSITION,0|284,477");
        connection.send("0|7|QUICKBAR_SLOT|3,46,39,6,7,50,12,13,23,-1");
        connection.send("0|7|ALWAYS_DRAGGABLE_WINDOWS|1");
        connection.send("0|7|QUICKSLOT_STOP_ATTACK|1");
        

        // Send ship initialization
        connection.send(account.getShipInitializationCommand());
        connection.send(account.getBatteriesInitializationCommand());
        connection.send(account.getRocketsInitializationCommand());
        connection.send(account.hangar.getResourcesInitializationCommand());

        // Additional info
        connection.send(account.hangar.activeConfiguration.getChangeConfigurationCommand());
        if(account.rankID >= 21) {
            connection.send("0|A|ADM|CLI|1");
        }

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
