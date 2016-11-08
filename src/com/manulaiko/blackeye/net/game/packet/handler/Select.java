package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.SelectShip;
import com.manulaiko.blackeye.simulator.map.Map;

/**
 * Select handler.
 *
 * Handles ship selection requests.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Select extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handlers the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        int id = this._packet.readInt();
        Map m  = connection.account.hangar.ship.map;

        if(id <= 0) {
            this._selectNPC(connection, id, m);

            return;
        }

        this._selectAccount(connection, id, m);
    }

    /**
     * Selects a npc.
     *
     * @param connection Connection that received the packet.
     * @param id         NPC id.
     * @param map        Account's map.
     */
    private void _selectNPC(Connection connection, int id, Map map)
    {
        map.npcs.forEach((i, n)->{
            if(i == id) {
                SelectShip p = n.getSelectShipCommand();

                p.id = id;

                connection.send(p);
                connection.account.attack.target = n;
            }
        });
    }

    /**
     * Selects an account.
     *
     * @param connection Connection that received the packet.
     * @param id         Account id.
     * @param map        Account's map.
     */
    private void _selectAccount(Connection connection, int id, Map map)
    {
        map.accounts.forEach((i, a)->{
            if(i == id) {
                SelectShip p = a.getSelectShipCommand();

                p.id = id;

                connection.send(p);
                connection.account.attack.target = a;
            }
        });
    }
}
