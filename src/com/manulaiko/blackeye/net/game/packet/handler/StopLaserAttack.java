package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;

/**
 * Stop Laser attack handler.
 *
 * Stop laser attacks.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class StopLaserAttack extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handlers the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        if(connection.account.attack.target == null) {
            return;
        }

        if(!connection.account.attack.isAttacking) {
            return;
        }

        connection.account.attack.stopLaserAttack();
    }
}
