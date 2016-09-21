package com.manulaiko.blackeye.simulator.account;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packets.commands.CreateShip;
import com.manulaiko.blackeye.net.game.packets.commands.Move;
import com.manulaiko.blackeye.net.game.packets.commands.ShipInitialization;

import com.manulaiko.blackeye.simulator.account.equipment.hangar.Hangar;
import com.manulaiko.blackeye.simulator.clan.Clan;
import com.manulaiko.blackeye.simulator.level.Level;

/**
 * Account class
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Account
{
    /**
     * Account ID
     */
    public int id;

    /**
     * Session ID
     */
    public String sessionID;

    /**
     * Hangar object
     */
    public Hangar hangar;

    /**
     * Account name
     */
    public String name;

    /**
     * Faction id
     */
    public int factionsID;

    /**
     * Clan ID
     */
    public int clansID;

    /**
     * Clan object
     */
    public Clan clan;

    /**
     * Uridium
     */
    public int uridium;

    /**
     * Credits
     */
    public long credits;

    /**
     * Jackpot
     */
    public double jackpot;

    /**
     * Experience points
     */
    public long experience;

    /**
     * Honor points
     */
    public int honor;

    /**
     * Level object
     */
    public Level level;

    /**
     * Levels id
     */
    public int levelsID;

    /**
     * Whether the account is premium or not
     */
    public boolean isPremium;

    /**
     * Rank ID
     */
    public int ranksID;

    /**
     * Rank points
     */
    public int rankPoints;

    /**
     * Connection object
     */
    public Connection connection = null;

    /**
     * Next movement ping.
     */
    private long _nextMovementPing = 0;

    /**
     * Constructor
     *
     * @param id         Account ID
     * @param sessionID  Session ID
     * @param name       Account name
     * @param factionsID Faction ID
     * @param uridium    Uridium
     * @param credits    Credits
     * @param jackpot    Jackpot
     * @param experience Experience
     * @param honor      Honor
     * @param isPremium  Whether is premium or not
     * @param ranksID    Rank ID
     * @param rankPoints Rank points
     */
    public Account(
            int id, String sessionID, String name, int factionsID, int clansID, int uridium, long credits,
            double jackpot, long experience, int honor, int levelsID, boolean isPremium, int ranksID, int rankPoints
    ) {
        this.id         = id;
        this.sessionID  = sessionID;
        this.name       = name;
        this.factionsID = factionsID;
        this.clansID    = clansID;
        this.uridium    = uridium;
        this.credits    = credits;
        this.jackpot    = jackpot;
        this.experience = experience;
        this.honor      = honor;
        this.levelsID   = levelsID;
        this.isPremium  = isPremium;
        this.ranksID    = ranksID;
        this.rankPoints = rankPoints;
    }

    /**
     * Sets clan object
     *
     * @param clan Clan object
     */
    public void setClan(Clan clan)
    {
        this.clan = clan;
    }

    /**
     * Sets hangar object
     *
     * @param hangar Hangar object
     */
    public void setHangar(Hangar hangar)
    {
        this.hangar = hangar;
    }

    /**
     * Sets level object
     *
     * @param level Level object
     */
    public void setLevel(Level level)
    {
        this.level = level;
    }

    /**
     * Builds and returns the ShipInitialization packet
     *
     * @return Ship initialization packet
     */
    public ShipInitialization getShipInitializationCommand()
    {
        ShipInitialization p = (ShipInitialization) ServerManager.game.packetFactory.getCommandByName("ShipInitialization");

        p.id         = this.id;
        p.name       = this.name;
        p.shipID     = this.hangar.ship.id;
        p.speed      = this.hangar.getSpeed();
        p.shield     = this.hangar.getShield();
        p.maxShield  = this.hangar.getMaxShield();
        p.health     = this.hangar.getHealth();
        p.maxHealth  = this.hangar.getMaxHealth();
        p.cargo      = this.hangar.getCargo();
        p.maxCargo   = this.hangar.getMaxCargo();
        p.x          = this.hangar.ship.position.getX();
        p.y          = this.hangar.ship.position.getY();
        p.mapID      = this.hangar.ship.mapID;
        p.factionID  = this.factionsID;
        p.clanID     = this.clan.id;
        p.batteries  = this.hangar.getBatteriesAmount();
        p.rockets    = this.hangar.getRocketsAmount();
        p.oState     = this.hangar.getExpansions();
        p.isPremium  = this.isPremium;
        p.experience = this.experience;
        p.honor      = this.honor;
        p.levelID    = this.level.id;
        p.credits    = this.credits;
        p.uridium    = this.uridium;
        p.jackpot    = this.jackpot;
        p.rankID     = this.ranksID;
        p.clanTag    = this.clan.tag;
        p.ggRings    = 4;
        p.useSysFont = 1; //No idea

        return p;
    }

    /**
     * Builds and returns CreateShip packet
     *
     * @return Create ship packet
     */
    public CreateShip getCreateShipCommand()
    {
        CreateShip p = (CreateShip) ServerManager.game.packetFactory.getCommandByName("CreateShip");

        p.id            = this.id;
        p.shipID        = this.hangar.ship.ship.id;
        p.expansion     = this.hangar.getExpansions();
        p.clanTag       = this.clan.tag;
        p.name          = this.name;
        p.x             = this.hangar.ship.position.getX();
        p.y             = this.hangar.ship.position.getY();
        p.factionID     = this.factionsID;
        p.clanID        = this.clansID;
        p.rankID        = this.ranksID;
        p.warningIcon   = false;
        p.clanDiplomacy = 0;
        p.ggRings       = 0;
        p.isNPC         = false;
        p.isCloaked     = this.hangar.ship.isCloaked;

        return p;
    }

    /**
     * Updates the account
     */
    public void update()
    {
        if(this.hangar.ship.isMoving) {
            if(System.currentTimeMillis() >= this._nextMovementPing) {
                this._nextMovementPing = System.currentTimeMillis() + 1000;

                Move packet = (Move)ServerManager.game.packetFactory.getCommandByName("Move");

                packet.id   = this.id;
                packet.newX = this.hangar.ship.position.getX();
                packet.newY = this.hangar.ship.position.getY();
                packet.time = 1000;

                this.hangar.ship.map.broadcastPacket(packet.toString(), this.id);
            }
        }

        this.hangar.ship.update();

        // TODO Update attack
    }
}
