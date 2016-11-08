package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;

import com.manulaiko.blackeye.net.game.packet.command.Move;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Movement handler.
 *
 * Handles received movement packets.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Movement extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        Point newPosition = new Point(this._packet.readInt(), this._packet.readInt());
        Point oldPosition = connection.account.hangar.ship.position;

        Console.println("Old position: "+ oldPosition, " New position: "+ newPosition);

        long time    = this.getDuration(oldPosition, newPosition, connection);
        long endTime = System.currentTimeMillis() + time;

        Move p = (Move) ServerManager.game.packetFactory.getCommandByName("Move");
        p.id   = connection.account.id;
        p.newX = newPosition.getX();
        p.newY = newPosition.getY();
        p.time = time;

        connection.account.hangar.ship.map.broadcastPacket(p.toString(), connection.account.id);

        connection.account.hangar.ship.newPosition = newPosition;
        connection.account.hangar.ship.oldPosition = oldPosition;
        connection.account.hangar.ship.position    = newPosition; // Cuz yolo
        //connection.account.hangar.ship.isMoving    = true;
        connection.account.hangar.ship.time        = time;
        connection.account.hangar.ship.endTime     = endTime;

        com.manulaiko.tabitha.Console.println("Moving time: "+ time, " End time: "+ endTime);
    }

    /**
     * Calculates and returns flight duration
     *
     * @param oldPosition Old position
     * @param newPosition New position
     * @param connection  Connection that's flying
     *
     * @return Duration of the flight in milli seconds
     */
    public long getDuration(Point oldPosition, Point newPosition, Connection connection)
    {
        Point direction = new Point(
                oldPosition.getX() - newPosition.getX(),
                oldPosition.getY() - newPosition.getY()
        );

        double distance = oldPosition.distanceTo(direction);
        long   time     = (long)(distance * 1000.0D) / connection.account.hangar.getSpeed();

        return time;
    }
}
