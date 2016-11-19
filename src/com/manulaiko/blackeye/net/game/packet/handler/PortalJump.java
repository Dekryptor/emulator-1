package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.ActivatePortal;
import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Portal jump handler.
 *
 * Handles jump portal's activation.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class PortalJump extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        Console.println("Starting jump...");
        Portal portal = this._findNearestPortal(connection.account.hangar.ship);

        Point p  = connection.account.hangar.ship.position;
        Point mp = new Point(
                p.getX() + Main.configuration.getInt("maps.entity_range"),
                p.getY() + Main.configuration.getInt("maps.entity_range")
        );

        if(
            portal == null                    ||
            !portal.isWorking                 ||
            !portal.position.isInRange(p, mp)
        ) {
            return;
        }

        if(connection.account.level.id < portal.level) {
            // Send message packet
            return;
        }

        try {
            Map m = (Map)GameManager.maps.getByID(portal.targetMapsID);

            if(m == null) {
                return;
            }

            this._jump(connection, m, portal);
        } catch(Exception e) {
            Console.println("Couldn't use portal!");
            Console.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Finds and returns nearest portal.
     *
     * @param ship Ship object.
     *
     * @return Closest portal to `ship`.
     */
    private Portal _findNearestPortal(Ship ship)
    {
        Console.println("Finding closest portal...");
        Portal portal = null;

        if(ship.nearPortals.size() == 0) {
            return portal;
        }

        for(Portal p : ship.nearPortals.values()) {
            if(portal != null) {
                if(ship.position.distanceTo(p.position) < ship.position.distanceTo(portal.position)) {
                    portal = p;
                }
            } else {
                portal = p;
            }
        }

        return portal;
    }

    /**
     * Activates jump portal.
     *
     * @param connection Connection that activated the portal.
     * @param map        Target map.
     * @param portal     Activated portal.
     *
     * @throws Exception If something goes wrong.
     */
    private void _jump(Connection connection, Map map, Portal portal) throws Exception
    {
        Console.println("Activating portal...");
        ActivatePortal p = (ActivatePortal)ServerManager.game.packetFactory.getCommandByName("ActivatePortal");

        p.mapID    = map.id;
        p.portalID = portal.id;

        connection.send(p);
        Thread.sleep(3000);

        connection.account.hangar.ship.map.removeAccount(connection.account);

        connection.account.hangar.ship.map      = map;
        connection.account.hangar.ship.position = portal.targetPosition;

        map.addAccount(connection.account);

        connection.send(connection.account.getShipInitializationCommand());
        connection.send("0|i|"+ map.id);
        Console.println("Jump finished!");
    }
}
