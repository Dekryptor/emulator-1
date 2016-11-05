package com.manulaiko.blackeye.simulator.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.*;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.blackeye.simulator.map.station.Station;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;

/**
 * Map class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Map implements Cloneable
{
    /**
     * Map ID.
     *
     * @var ID.
     */
    public int id = 0;

    /**
     * Map name.
     *
     * @var Name.
     */
    public String name = "";

    /**
     * Map size.
     *
     * @var Map size.
     */
    public Point limits = new Point(0, 0);

    /**
     * Map portals.
     *
     * @var Portals on map.
     */
    public HashMap<Integer, Portal> portals = new HashMap<>();

    /**
     * NPCS.
     *
     * @var NPCS on map.
     */
    public HashMap<Integer, NPC> npcs = new HashMap<>();

    /**
     * Collectables.
     *
     * @var Collectables on map.
     */
    public HashMap<Integer, Collectable> collectables = new HashMap<>();

    /**
     * Stations.
     *
     * @var Stations on map.
     */
    public ArrayList<Station> stations = new ArrayList<>();

    /**
     * Accounts.
     *
     * @var Accounts on map.
     */
    public HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * Whether map is pvp or not.
     *
     * @var PVP status.
     */
    public boolean isPVP = false;

    /**
     * Whether map is starter map.
     *
     * Used to show where are the enemies in the minimap.
     *
     * @var Starter status.
     */
    public boolean isStarter = false;

    /**
     * Map's owner.
     *
     * @var Owner's ID.
     */
    public int factionsID = -1;

    /**
     * Constructor.
     *
     * @param id         Map id.
     * @param factionsID Map's owner faction.
     * @param isPVP      Whether map is a pvp map or not.
     * @param isStarter  Whether map is a starter map or not.
     * @param name       Map name.
     * @param limits     Map limits.
     */
    public Map(int id, int factionsID, boolean isPVP, boolean isStarter, String name, Point limits)
    {
        this.id         = id;
        this.factionsID = factionsID;
        this.isPVP      = isPVP;
        this.isStarter  = isStarter;
        this.name       = name;
        this.limits     = limits;
    }

    /**
     * Adds a NPC to the array.
     *
     * @param npc NPC to add.
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
     * Adds a portal to the array.
     *
     * @param portal Portal to add.
     */
    public void addPortal(Portal portal)
    {
        this.portals.put(portal.id, portal);
    }

    /**
     * Adds a collectable to the array.
     *
     * @param collectable Collectable to add.
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
     * Adds a station to the array.
     *
     * @param station Station to add.
     */
    public void addStation(Station station)
    {
        this.stations.add(station);
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Map clone()
    {
        try {
            Map m = (Map)super.clone();

            this.npcs.forEach((i, n)->{
                m.addNPC(n.clone());
            });
            this.stations.forEach((s)->{
                m.addStation(s.clone());
            });
            this.portals.forEach((i, p)->{
                m.addPortal(p.clone());
            });
            this.collectables.forEach((i, c)->{
                m.addCollectable(c.clone());
            });

            return m;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone map!");
            Console.println(e.getMessage());

            return null;
        }
    }

    /**
     * Adds an account to the map.
     *
     * @param account Account to add.
     */
    public void addAccount(Account account)
    {
        if(this.accounts.containsKey(account.id)) {
            this.removeAccount(account);
        }

        this.setNearEntities(account);

        this.sendNPCs(account.connection);
        this.sendPortals(account.connection);
        this.sendStations(account.connection);
        this.sendCollectables(account.connection);
        this.sendAccounts(account.connection);

        this.accounts.put(account.id, account);
    }

    /**
     * Sets an account's near entities (npcs, other players, collectables...)
     *
     * @param account Account to set.
     */
    public void setNearEntities(Account account)
    {
        Point position = account.hangar.ship.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        // Collectables
        this.collectables.forEach((i, c)->{
            if(c.position.isInRange(position, maxRange)) {
                account.hangar.ship.nearCollectables.put(i, c);
            }
        });

        // NPCs
        this.npcs.forEach((i, n)->{
            if(n.position.isInRange(position, maxRange)) {
                account.hangar.ship.nearNPCs.put(i, n);
            }
        });

        // Accounts
        this.accounts.forEach((i, a)->{
            if(
                i != account.id && // Just in case someone decides to call this method twice (by someone I mean my future me ¬.¬)
                a.hangar.ship.position.isInRange(position, maxRange)
            ) {
                account.hangar.ship.nearAccounts.put(i, a);
            }
        });
    }

    /**
     * Removes an account from the map.
     *
     * @param account      Account to remove.
     * @param isDestroyed `true` to send destroy packet, `false` to send remove packet.
     */
    public void removeAccount(Account account, boolean isDestroyed)
    {
        if(!this.accounts.containsKey(account.id)) {
            return;
        }

        this.accounts.remove(account.id);

        if(isDestroyed) {
            DestroyShip p = (DestroyShip) ServerManager.game.packetFactory.getCommandByName("DestroyShip");
            p.id = id;

            account.hangar.ship.nearAccounts.forEach((i, a)->{
                if(a.connection != null) {
                    a.connection.send(p);
                }
            });
        } else {
            RemoveShip p = (RemoveShip) ServerManager.game.packetFactory.getCommandByName("RemoveShip");
            p.id = id;

            account.hangar.ship.nearAccounts.forEach((i, a)->{
                if(a.connection != null) {
                    a.connection.send(p);
                }
            });
        }
    }

    /**
     * Alias of `removeAccount(account, false)`.
     *
     * @param account Account to remove.
     */
    public void removeAccount(Account account)
    {
        this.removeAccount(account, false);
    }

    /**
     * Sends map's NPCs.
     *
     * @param connection Connection to send packets.
     */
    public void sendNPCs(Connection connection)
    {
        connection.account.hangar.ship.nearNPCs.forEach((key, value) -> {
            CreateShip p = value.getCreateShipCommand();

            connection.send(p);
        });
    }

    /**
     * Sends map's Accounts.
     *
     * @param connection Connection to send packets.
     */
    public void sendAccounts(Connection connection)
    {
        connection.account.hangar.ship.nearAccounts.forEach((key, value) -> {
            CreateShip p = value.getCreateShipCommand();

            // Set warning icon on minimap based on account's factionsID
            if(p.factionID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            connection.send(p);

            p = connection.account.getCreateShipCommand();

            // Set warning icon on minimap based on account's factionsID
            if(p.factionID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            value.connection.send(p);
        });
    }

    /**
     * Sends map's portals.
     *
     * @param connection Connection to send packets.
     */
    public void sendPortals(Connection connection)
    {
        this.portals.forEach((key, value) -> {
            CreatePortal packet = value.getCreatePortalCommand();

            connection.send(packet);
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

            connection.send(p);
        });
    }

    /**
     * Sends map's collectables.
     *
     * @param connection Connection to send packets.
     */
    public void sendCollectables(Connection connection)
    {
        this.collectables.forEach((key, value) -> {
            CreateCollectable p = value.getCreateCollectableCommand();

            connection.send(p);
        });
    }

    /**
     * Broadcasts a packet.
     *
     * Sends a packet to all accounts in the map.
     *
     * @param packet Packet to send.
     */
    public void broadcastPacket(String packet)
    {
        this.broadcastPacket(packet, -1); // All accounts identifiers are positive integers
    }

    /**
     * Broadcasts a packet.
     *
     * Sends a packet to all accounts in the map except from the specified.
     *
     * @param packet Packet to send.
     * @param id     Account id that won't receive the packet.
     */
    public void broadcastPacket(String packet, int id)
    {
        this.accounts.forEach((key, value) -> {
            if(value.id != id) {
                value.connection.send(packet);
            }
        });
    }
}
