package com.manulaiko.blackeye.net.game.packets.handlers;

import java.awt.Point;

import com.manulaiko.tabitha.utils.Vector;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.*;

/**
 * Movement handler
 *
 * Handles received movement packets
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.game.packets.handlers
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

        Vector oldPosition = new Vector(this._packet.readInt(), this._packet.readInt());
        Vector newPosition = new Vector(this._packet.readInt(), this._packet.readInt());

        Vector direction = new Vector(
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
