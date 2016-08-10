package com.manulaiko.blackeye.net.game.packets.handlers;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.*;

import com.manulaiko.tabitha.utils.Point;

/**
 * Movement handler
 *
 * Handles received movement packets
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Movement extends com.manulaiko.blackeye.net.game.packets.Packet
{
    /**
     * Handles the packet
     *
     * @param connection Connection that received the packet
     */
    public void handle(Connection connection)
    {
        int speed = connection.account.hangar.getSpeed();

        com.manulaiko.tabitha.utils.Point oldPosition = new com.manulaiko.tabitha.utils.Point(this._packet.readInt(), this._packet.readInt());
        com.manulaiko.tabitha.utils.Point newPosition = new com.manulaiko.tabitha.utils.Point(this._packet.readInt(), this._packet.readInt());

        com.manulaiko.tabitha.utils.Point direction = new com.manulaiko.tabitha.utils.Point(
                oldPosition.getX() - newPosition.getX(),
                oldPosition.getY() - newPosition.getY()
        );

        double distance = oldPosition.distanceTo(direction);
        double time     = (distance / (speed * 0.84412)) * 1000;

        Move p = (Move) ServerManager.game.packetFactory.getCommandByName("Move");
        p.id   = connection.account.id;
        p.newX = (int)newPosition.getX();
        p.newY = (int)newPosition.getY();
        p.time = time;

        connection.account.hangar.ship.map.broadcastPacket(p.toString(), connection.account.id);
        connection.account.hangar.ship.position = new Point((int)newPosition.getX(), (int)newPosition.getY());
    }
}
