package com.manulaiko.blackeye.simulator.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.*;

import com.manulaiko.blackeye.simulator.portal.Portal;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.collectable.Collectable;
import com.manulaiko.blackeye.simulator.station.Station;
import com.manulaiko.blackeye.simulator.account.Account;

import com.manulaiko.tabitha.Console;

/**
 * Map class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator
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

        this.npcs.put(id--, npc);
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

        this.collectables.put(id--, collectable);
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
        this.npcs.forEach((key, value) -> {
            CreateShip p = (CreateShip) ServerManager.game.packetFactory.getCommandByName("CreateShip");

            p.id            = key;
            p.shipID        = value.gfx;
            p.expansion     = 0;
            p.clanTag       = "";
            p.name          = value.name;
            p.x             = (int)value.position.getX();
            p.y             = (int)value.position.getY();
            p.factionID     = 0;
            p.clanID        = 0;
            p.rankID        = 0;
            p.warningIcon   = false;
            p.clanDiplomacy = 0;
            p.ggRings       = 0;
            p.isNPC         = true;
            p.isCloaked     = true;

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
        this.accounts.forEach((key, value) -> {
            Account a = value.account;

            CreateShip p = (CreateShip) ServerManager.game.packetFactory.getCommandByName("CreateShip");

            p.id            = a.id;
            p.shipID        = a.hangar.ship.ship.id;
            p.expansion     = a.hangar.getExpansions();
            p.clanTag       = a.clan.tag;
            p.name          = a.name;
            p.x             = (int)a.hangar.ship.position.getX();
            p.y             = (int)a.hangar.ship.position.getY();
            p.factionID     = a.factionsID;
            p.clanID        = a.clansID;
            p.rankID        = a.ranksID;
            p.warningIcon   = false;
            p.clanDiplomacy = 0;
            p.ggRings       = 0;
            p.isNPC         = false;
            p.isCloaked     = a.hangar.ship.isCloaked;

            // Set warning icon on minimap based on account's factionsID
            if(a.factionsID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            connection.send(p.toString());

            // Now send hero's accounts to the other user
            a = connection.account;

            p.id            = a.id;
            p.shipID        = a.hangar.ship.ship.id;
            p.expansion     = a.hangar.getExpansions();
            p.clanTag       = a.clan.tag;
            p.name          = a.name;
            p.x             = (int)a.hangar.ship.position.getX();
            p.y             = (int)a.hangar.ship.position.getY();
            p.factionID     = a.factionsID;
            p.clanID        = a.clansID;
            p.rankID        = a.ranksID;
            p.warningIcon   = false;
            p.clanDiplomacy = 0;
            p.ggRings       = 0;
            p.isNPC         = false;
            p.isCloaked     = a.hangar.ship.isCloaked;

            // Set warning icon on minimap based on account's factionsID
            if(a.factionsID != this.factionsID) {
                p.warningIcon = this.isStarter;
            }

            value.send(p.toString());
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
            CreatePortal packet = (CreatePortal) ServerManager.game.packetFactory.getCommandByName("CreatePortal");

            packet.id     = key;
            packet.gfx    = 1;
            packet.ptarid = 1;
            packet.x      = (int)value.position.getX();
            packet.y      = (int)value.position.getY();

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
        for(Station s : this.stations) {
            CreateStation p = (CreateStation) ServerManager.game.packetFactory.getCommandByName("CreateStation");

            p.id      = 0;
            p.name    = s.name;
            p.type    = s.type;
            p.faction = s.factionsID;
            p.isDMZ   = true;
            p.x       = (int)s.position.getX();
            p.y       = (int)s.position.getY();

            connection.send(p.toString());
        }
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
}
