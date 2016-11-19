package com.manulaiko.blackeye.simulator.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.*;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.blackeye.simulator.map.station.Station;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;

import com.manulaiko.blackeye.utils.Updatable;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import org.json.JSONArray;

/**
 * Map class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Map extends Simulator implements Cloneable, Updatable
{
    /**
     * Instances and returns a new Simulator.
     *
     * @return New Simulator object.
     */
    public static Map create()
    {
        Map m           = new Map(0, 0, false, false, "", null);
        m._isInsert     = true;
        m.databaseTable = "maps";

        return m;
    }

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

        GameManager.updaterManager.subscribe(this);
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

        npc.id  = id;
        npc.map = this;

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

        this.sendPortals(account);
        this.sendStations(account);
        this.sendCollectables(account);
        this.sendAccounts(account);
        this.sendNPCs(account);

        this.accounts.put(account.id, account);
        this.update();
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

        account.hangar.ship.nearAccounts.forEach((i, a)->{
            if(account.connection != null) {
                account.connection.send(a.getRemoveShipCommand());
            }
        });
        account.hangar.ship.nearAccounts.clear();

        account.hangar.ship.nearCollectables.forEach((i, c)->{
            if(account.connection != null) {
                account.connection.send(c.getRemoveCollectableCommand());
            }
        });
        account.hangar.ship.nearCollectables.clear();

        account.hangar.ship.nearNPCs.forEach((i, n)->{
            if(account.connection != null) {
                account.connection.send(n.getRemoveShipCommand());
            }
        });
        account.hangar.ship.nearNPCs.clear();

        account.hangar.ship.nearPortals.clear();
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
     * Sends map's portals.
     *
     * @param account Account to send packets.
     */
    public void sendPortals(Account account)
    {
        if(account.connection == null) {
            return;
        }

        this.portals.forEach((key, value) -> {
            CreatePortal packet = value.getCreatePortalCommand();

            account.connection.send(packet);
        });
    }

    /**
     * Sends map's stations
     *
     * @param account Account to send packets
     */
    public void sendStations(Account account)
    {
        if(account.connection == null) {
            return;
        }

        this.stations.forEach((value) -> {
            CreateStation p = value.getCreateStationCommand();

            account.connection.send(p);
        });
    }

    /**
     * Sends map's NPCs.
     *
     * @param account Account to send packets.
     */
    public void sendNPCs(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship  s      = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        this.npcs.forEach((i, n)->{
            // Check that npc is in range and isn't already sent
            if(
                n.position.isInRange(position, maxRange) &&
                !s.nearNPCs.containsKey(i)
            ) {
                s.nearNPCs.put(i, n);
                n.nearAccounts.put(account.id, account);

                c.send(n.getCreateShipCommand());
            }
        });
    }

    /**
     * Updates near NPCs.
     *
     * @param account Account to update.
     */
    public void updateNPCs(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship       s = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        s.nearNPCs.forEach((i, n)->{
            if(
                !n.position.isInRange(position, maxRange) ||
                !this.npcs.containsKey(i)
            ) {
                s.nearNPCs.remove(i);
                n.nearAccounts.remove(account.id);

                c.send(n.getRemoveShipCommand());
            }
        });

        this.sendNPCs(account);
    }

    /**
     * Sends map's Accounts.
     *
     * @param account Account to send packets.
     */
    public void sendAccounts(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship  s      = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        this.accounts.forEach((i, a)->{
            // Check that account is in range and isn't already sent
            if(
                i != account.id                                      &&
                a.hangar.ship.position.isInRange(position, maxRange) &&
                !s.nearAccounts.containsKey(i)
            ) {
                s.nearAccounts.put(i, a);

                CreateShip p = a.getCreateShipCommand();

                if(p.factionID != this.factionsID) {
                    p.warningIcon = this.isStarter;
                }

                c.send(p);
            }
        });
    }

    /**
     * Updates near Accounts.
     *
     * @param account Account to update.
     */
    public void updateAccounts(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship       s = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        s.nearAccounts.forEach((i, a)->{
            if(
                !a.hangar.ship.position.isInRange(position, maxRange) ||
                !this.accounts.containsKey(i)
            ) {
                s.nearAccounts.remove(i);

                c.send(a.getRemoveShipCommand());
            }
        });

        this.sendAccounts(account);
    }

    /**
     * Sends map's collectables.
     *
     * @param account Account to send packets.
     */
    public void sendCollectables(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship       s = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        this.collectables.forEach((i, col)->{
            // Check that npc is in range and isn't already sent
            if(
                col.position.isInRange(position, maxRange) &&
                !s.nearCollectables.containsKey(i)
            ) {
                s.nearCollectables.put(i, col);

                c.send(col.getCreateCollectableCommand());
            }
        });
    }

    /**
     * Updates near collectables.
     *
     * @param account Account to update.
     */
    public void updateCollectables(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship       s = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        s.nearCollectables.forEach((i, col)->{
            if(
                    !col.position.isInRange(position, maxRange) ||
                            !this.collectables.containsKey(i)
                    ) {
                s.nearCollectables.remove(i);

                c.send(col.getRemoveCollectableCommand());
            }
        });

        this.sendCollectables(account);
    }

    /**
     * Updates near portals.
     *
     * @param account Account to update.
     */
    public void updatePortals(Account account)
    {
        if(account.connection == null) {
            return;
        }

        // Shortcuts
        Connection c = account.connection;
        Ship       s = account.hangar.ship;

        Point position = s.position;
        Point maxRange = new Point(
                position.getX() + Main.configuration.getInt("maps.entity_range"),
                position.getY() + Main.configuration.getInt("maps.entity_range")
        );

        s.nearPortals.forEach((i, p)->{
            if(
                !p.position.isInRange(position, maxRange) ||
                !this.portals.containsKey(i)
            ) {
                s.nearPortals.remove(i);
            }
        });

        this.portals.forEach((i, p)->{
            if(
                p.position.isInRange(position, maxRange) &&
                !s.nearPortals.containsKey(i)
            ) {
                s.nearPortals.put(i, p);
            }
        });
    }

    /**
     * Updates the map.
     */
    public void update()
    {
        this.accounts.forEach((i, a)->{
            this.updateAccounts(a);
            this.updateNPCs(a);
            this.updateCollectables(a);
            this.updatePortals(a);
        });

        /*
        this.npcs.forEach((i, n)->{
            n.update();
        });
         */
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

    /**
     * Returns table identifier.
     *
     * @return Table identifier.
     */
    protected int _getDatabaseIdentifier()
    {
        return this.id;
    }

    /**
     * Sets database ID.
     *
     * @param id Database ID.
     */
    protected void _setDatabaseIdentifier(int id)
    {
        this.id = id;
    }

    /**
     * Returns table fields.
     *
     * @return Table fields.
     */
    protected HashMap<String, Object> _getDatabaseFields()
    {
        HashMap<String, Object> fields = new HashMap<>();

        JSONArray limits = new JSONArray();
        limits.put(this.limits.getX());
        limits.put(this.limits.getY());

        fields.put("name", this.name);
        fields.put("limits", limits);
        fields.put("is_pvp", this.isPVP);
        fields.put("is_starter", this.isStarter);
        fields.put("factions_id", this.factionsID);

        return fields;
    }
}
