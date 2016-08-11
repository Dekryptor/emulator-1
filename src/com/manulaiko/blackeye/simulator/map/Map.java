package com.manulaiko.blackeye.simulator.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.*;

import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;
import com.manulaiko.blackeye.simulator.map.station.Station;


import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Map class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Map
{
    /**
     * Map ID
     */
    public int id = 0;

    /**
     * Map name
     */
    public String name = "";

    /**
     * Map size
     */
    public Point limits = new Point(0, 0);

    /**
     * Map portals
     */
    public HashMap<Integer, Portal> portals = new HashMap<>();

    /**
     * NPCS
     */
    public HashMap<Integer, NPC> npcs = new HashMap<>();

    /**
     * Collectables
     */
    public HashMap<Integer, Collectable> collectables = new HashMap<>();

    /**
     * Stations
     */
    public ArrayList<Station> stations = new ArrayList<>();

    /**
     * Players on the map
     */
    public HashMap<Integer, Connection> accounts = new HashMap<>();

    /**
     * Whether map is pvp or not
     */
    public boolean isPVP = false;

    /**
     * Whether map is starter map
     */
    public boolean isStarter = false;

    /**
     * Map's owner
     */
    public int factionsID = -1;

    /**
     * NPCs' JSON
     */
    public JSONArray npcsJSON;

    /**
     * Stations' JSON
     */
    public JSONArray stationsJSON;

    /**
     * Collectables' JSON
     */
    public JSONArray collectablesJSON;

    /**
     * Constructor
     *
     * @param id           Map id
     * @param factionsID   Map's owner faction
     * @param isPVP        Whether map is a pvp map or not
     * @param isStarter    Whether map is a starter map or not
     * @param name         Map name
     * @param limits       Map limits
     * @param npcs         NPCs' JSON
     * @param stations     Stations' JSON
     * @param collectables Collectables' JSON
     */
    public Map(
            int id, int factionsID, boolean isPVP, boolean isStarter, String name,
            Point limits, JSONArray npcs, JSONArray stations, JSONArray collectables
    ) {
        this.id               = id;
        this.factionsID       = factionsID;
        this.isPVP            = isPVP;
        this.isStarter        = isStarter;
        this.name             = name;
        this.limits           = limits;
        this.npcsJSON         = npcs;
        this.stationsJSON     = stations;
        this.collectablesJSON = collectables;
    }

    /**
     * Adds a NPC to the array
     *
     * @param npc NPC to add
     */
    public void addNPC(NPC npc)
    {
        int id = -this.npcs.size();

        if(id <= -2147483647) {
            id = 0;
        }

        npc.id = id;

        this.npcs.put(id, npc);
    }

    /**
     * Adds a portal to the array
     *
     * @param portal Portal to add
     */
    public void addPortal(Portal portal)
    {
        this.portals.put(portal.id, portal);
    }

    /**
     * Adds a collectable to the array
     *
     * @param collectable Collectable to add
     */
    public void addCollectable(Collectable collectable)
    {
        int id = -this.collectables.size();

        if(id <= -2147483647) {
            id = 0;
        }

        collectable.id = id;

        this.collectables.put(id, collectable);
    }

    /**
     * Adds a station to the array
     *
     * @param station Station to add
     */
    public void addStation(Station station)
    {
        this.stations.add(station);
    }

    /**
     * Adds an account to the map
     *
     * @param connection Account's connection
     */
    public void addAccount(Connection connection)
    {
        if(this.accounts.containsKey(connection.account.id)) {
            return;
        }

        this.sendNPCs(connection);
        this.sendPortals(connection);
        this.sendStations(connection);
        this.sendAccounts(connection);
        this.sendCollectables(connection);

        this.accounts.put(connection.account.id, connection);

        Console.println("Account "+ connection.account.id +" joined map "+ this.id);
    }

    /**
     * Sends map's NPCs
     *
     * @param connection Connection to send packets
     */
    public void sendNPCs(Connection connection)
    {
        //Point from = connection.account.hangar.ship.position;
        //Point to   = new Point(from.getX() + 1000, from.getY() + 1000);

        connection.account.hangar.ship.nearNPCs.forEach((key, value) -> {
            CreateShip p = value.getCreateShipCommand();

            connection.send(p.toString());
        });
    }

    /**
     * Sends map's Accounts
     *
     * @param connection Connection to send packets
     */
    public void sendAccounts(Connection connection)
    {
        connection.account.hangar.ship.nearAccounts.forEach((key, value) -> {
            CreateShip p = value.getCreateShipCommand();

            // Set warning icon on minimap based on account's factionsID
            if(p.factionID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            connection.send(p.toString());

            p = connection.account.getCreateShipCommand();

            // Set warning icon on minimap based on account's factionsID
            if(p.factionID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            value.connection.send(p.toString());
        });
    }

    /**
     * Sends map's portals
     *
     * @param connection Connection to send packets
     */
    public void sendPortals(Connection connection)
    {
        this.portals.forEach((key, value) -> {
            CreatePortal packet = value.getCreatePortalCommand();

            connection.send(packet.toString());
        });
    }

    /**
     * Sends map's stations
     *
     * @param connection Connection to send packets
     */
    public void sendStations(Connection connection)
    {
        this.stations.forEach((value) -> {
            CreateStation p = value.getCreateStationCommand();

            connection.send(p.toString());
        });
    }

    /**
     * Sends map's collectables
     *
     * @param connection Connection to send packets
     */
    public void sendCollectables(Connection connection)
    {
        this.collectables.forEach((key, value) -> {
            CreateCollectable p = (CreateCollectable) ServerManager.game.packetFactory.getCommandByName("CreateCollectable");

            p.id  = key;
            p.gfx = value.gfx;
            p.x   = (int)value.position.getX();
            p.y   = (int)value.position.getY();

            connection.send(p.toString());
        });
    }

    /**
     * Removes an account from map
     *
     * @param id          Account's ID
     * @param isDestroyed True if send destroy packet
     */
    public void removeAccount(int id, boolean isDestroyed)
    {
        if(!this.accounts.containsKey(id)) {
            return;
        }

        this.accounts.remove(id);

        if(isDestroyed) {
            DestroyShip p = (DestroyShip) ServerManager.game.packetFactory.getCommandByName("DestroyShip");
            p.id = id;
            this.broadcastPacket(p.toString(), id);
        } else {
            RemoveShip p = (RemoveShip) ServerManager.game.packetFactory.getCommandByName("RemoveShip");
            p.id = id;
            this.broadcastPacket(p.toString(), id);
        }
    }

    /**
     * Fallback for `removeAccount`
     *
     * The `isDestroyed` flag is set to true.
     *
     * @param id Account's ID
     */
    public void removeAccount(int id)
    {
        this.removeAccount(id, true);
    }

    /**
     * Removes a NPC from map
     *
     * @param id          NPC's ID
     * @param isDestroyed True if send destroy packet
     */
    public void removeNPC(int id, boolean isDestroyed)
    {
        if(!this.npcs.containsKey(id)) {
            return;
        }

        this.npcs.remove(id);

        if(isDestroyed) {
            DestroyShip p = (DestroyShip) ServerManager.game.packetFactory.getCommandByName("DestroyShip");
            p.id = id;
            this.broadcastPacket(p.toString(), id);
        } else {
            RemoveShip p = (RemoveShip) ServerManager.game.packetFactory.getCommandByName("RemoveShip");
            p.id = id;
            this.broadcastPacket(p.toString(), id);
        }
    }

    /**
     * Fallback for `removeNPC`
     *
     * The `isDestroyed` flag is set to true.
     *
     * @param id NPC's ID
     */
    public void removeNPC(int id)
    {
        this.removeNPC(id, true);
    }

    /**
     * Broadcasts a packet.
     *
     * Sends a packet to all accounts in the map.
     *
     * @param packet Packet to send
     */
    public void broadcastPacket(String packet)
    {
        this.broadcastPacket(packet, -1); // All accounts identifiers are positive integers
    }

    /**
     * Broadcasts a packet.
     *
     * Sends a packet to all accounts in the map except from the specified
     *
     * @param packet Packet to send
     * @param id     Account id that won't receive the packet
     */
    public void broadcastPacket(String packet, int id)
    {
        this.accounts.forEach((key, value) -> {
            if(value.id != id) {
                value.send(packet);
            }
        });
    }

    /**
     * Broadcasts a packet to nearest connections.
     *
     * Sends a packet to all accounts in the map that are between `from` and `to`.
     *
     * Example:
     * Broadcast a packet to all positions between 0/0 and 1000/1000.
     *
     *     map.broadcastPacketInRange("packet", new Point(0, 0), new Point(1000, 1000));
     *
     * @param packet Packet to send
     * @param from   Starting position
     * @param to     How far the packet should be sent away from `from`
     */
    public void broadcastPacketInRange(String packet, Point from, Point to)
    {
        this.accounts.forEach((key, value) -> {
            if(value.account.hangar.ship.position.isInRange(from, to)) {
                value.send(packet);
            }
        });
    }

    /**
     * Updates the map
     */
    public void update()
    {
        this.updateAccounts();
        this.updateNPCs();
    }

    /**
     * Updates accounts' status
     */
    public void updateAccounts()
    {
        this.accounts.forEach((key, value) -> {
            value.account.update();

            this.sendNearAccounts(value.account);
            this.sendNearNPCs(value.account);

            if(value.account.hangar.ship.isMoving) {
                this.checkDMZ(value.account);
            }
        });
    }

    /**
     * Updates NPCs' status
     */
    public void updateNPCs()
    {
        this.npcs.forEach((key, value) -> {

        });
    }

    /**
     * Sends near accounts
     *
     * @param account Main account
     */
    public void sendNearAccounts(Account account)
    {
        Point position = account.hangar.ship.position;
        Point distance = new Point(position.getX() + 1000, position.getY() + 1000);

        this.accounts.forEach((key, value) -> {
            if(key != account.id) {
                if(value.account.hangar.ship.position.isInRange(position, distance)) {
                    if(!(account.hangar.ship.nearAccounts.containsKey(key))) {
                        account.hangar.ship.nearAccounts.put(value.account.id, value.account);

                        account.connection.send(value.account.getCreateShipCommand().toString());
                    }
                } else if(account.hangar.ship.nearAccounts.containsKey(key)) {
                    account.hangar.ship.nearAccounts.remove(key);

                    RemoveShip p = (RemoveShip) ServerManager.game.packetFactory.getCommandByName("RemoveShip");
                    p.id = key;

                    account.connection.send(p.toString());
                }
            }
        });
    }

    /**
     * Sends near NPCs
     *
     * @param account Main account
     */
    public void sendNearNPCs(Account account)
    {
        Point position = account.hangar.ship.position;
        Point distance = new Point(position.getX() + 1000, position.getY() + 1000);

        this.npcs.forEach((key, value) -> {
            if(value.position.isInRange(position, distance)) {
                if(!(account.hangar.ship.nearNPCs.containsKey(key))) {
                    account.hangar.ship.nearNPCs.put(key, value);

                    CreateShip p = value.getCreateShipCommand();
                    p.id = key;

                    account.connection.send(p.toString());
                }
            } else if(account.hangar.ship.nearNPCs.containsKey(key)) {
                account.hangar.ship.nearNPCs.remove(key);

                RemoveShip p = (RemoveShip) ServerManager.game.packetFactory.getCommandByName("RemoveShip");
                p.id = key;

                account.connection.send(p.toString());
            }
        });
    }

    /**
     * Checks if the account is in a demilitarized zone
     *
     * @param account Main account
     */
    public void checkDMZ(Account account)
    {
        Point   position = account.hangar.ship.position;
        Point   distance = new Point(position.getX() + 1000, position.getY() + 1000);
        boolean isInDMZ  = false;
        boolean canEquip = false;

        for(Portal p : this.portals.values()) {
            if(p.position.isInRange(position, distance)) {
                isInDMZ = true;
            }
        }

        if(!isInDMZ) {
            for(Station s : this.stations) {
                if(s.position.isInRange(position, distance)) {
                    isInDMZ  = true;
                    canEquip = true;

                    break;
                }
            }
        }

        account.hangar.ship.isInDMZ  = isInDMZ;
        account.hangar.ship.canEquip = canEquip;

        if(isInDMZ) {
            // TODO Send packets
        }
    }
}
