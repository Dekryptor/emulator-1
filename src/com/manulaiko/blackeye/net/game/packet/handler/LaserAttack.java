package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.net.game.Connection;

/**
 * Laser attack handler.
 *
 * Handles laser attacks requests.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class LaserAttack extends com.manulaiko.blackeye.net.utils.Packet
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

        if(connection.account.attack.isAttacking) {
            connection.account.attack.stopLaserAttack();

            return;
        }

        connection.account.attack.startLaserAttack();
    }
}
