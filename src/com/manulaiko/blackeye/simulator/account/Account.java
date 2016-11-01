package com.manulaiko.blackeye.simulator.account;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.CreateShip;
import com.manulaiko.blackeye.net.game.packet.command.ShipInitialization;
import com.manulaiko.blackeye.simulator.account.equipment.hangar.Hangar;
import com.manulaiko.blackeye.simulator.clan.Clan;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.tabitha.Console;

/**
 * Account class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Account implements Cloneable
{
    /**
     * Account ID.
     */
    public int id;

    /**
     * Session ID.
     */
    public String sessionID;

    /**
     * Account name.
     */
    public String name;

    /**
     * Faction id.
     */
    public int factionsID;

    /**
     * Clan ID.
     */
    public int clansID;

    /**
     * Clan object.
     */
    public Clan clan;

    /**
     * Uridium.
     */
    public int uridium;

    /**
     * Credits.
     */
    public long credits;

    /**
     * Jackpot.
     */
    public double jackpot;

    /**
     * Experience points.
     */
    public long experience;

    /**
     * Honor points.
     */
    public int honor;

    /**
     * Level object.
     */
    public Level level;

    /**
     * Levels id.
     *
     * @var Level ID
     */
    public int levelID;

    /**
     * Whether the account is premium or not.
     *
     * @var Premium status.
     */
    public boolean isPremium;

    /**
     * Rank ID.
     *
     * @var Rank ID.
     */
    public int rankID;

    /**
     * Rank points.
     *
     * @var Rank points.
     */
    public int rankPoints;

    /**
     * Hangar object.
     *
     * @var Hangar.
     */
    public Hangar hangar;

    /**
     * Connection object.
     *
     * @var Connection.
     */
    public Connection connection;

    /**
     * Constructor.
     *
     * @param id         Account ID.
     * @param sessionID  Session ID.
     * @param name       Account name.
     * @param factionsID Faction ID.
     * @param uridium    Uridium.
     * @param credits    Credits.
     * @param jackpot    Jackpot.
     * @param experience Experience points.
     * @param honor      Honor points.
     * @param levelID    Level ID.
     * @param isPremium  Whether is premium or not.
     * @param rankID     Rank ID.
     * @param rankPoints Rank points.
     */
    public Account(
            int id, String sessionID, String name, int factionsID, int clansID, int uridium, long credits,
            double jackpot, long experience, int honor, int levelID, boolean isPremium, int rankID, int rankPoints
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
        this.levelID    = levelID;
        this.isPremium  = isPremium;
        this.rankID     = rankID;
        this.rankPoints = rankPoints;
    }

    /**
     * Sets clan object.
     *
     * @param clan Clan object.
     */
    public void setClan(Clan clan)
    {
        this.clan = clan;
    }

    /**
     * Sets level object.
     *
     * @param level Level object.
     */
    public void setLevel(Level level)
    {
        this.level = level;
    }

    /**
     * Sets hangar object.
     *
     * @param hangar Hangar object.
     */
    public void setHangar(Hangar hangar)
    {
        this.hangar = hangar;
    }

    /**
     * Clones the object.
     *
     * @return Cloned object.
     */
    public Account clone()
    {
        try {
            Account a = (Account)super.clone();

            a.setClan(this.clan.clone());
            a.setLevel(this.level.clone());

            return a;
        } catch(CloneNotSupportedException e) {
            Console.println("Couldn't clone account!");
            Console.println(e.getMessage());

            return null;
        }
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
        p.shipID     = this.hangar.ship.ship.id;
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
        p.clanID     = (this.clan == null) ? 0 : this.clan.id;
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
        p.rankID     = this.rankID;
        p.clanTag    = (this.clan == null) ? "" : this.clan.tag;
        p.ggRings    = 4;
        p.useSysFont = 1; //No idea

        return p;
    }

    /**
     * Builds and returns the CreateShip command.
     *
     * @return CreateShip command.
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
        p.rankID        = this.rankID;
        p.warningIcon   = false;
        p.clanDiplomacy = 0;
        p.ggRings       = 0;
        p.isNPC         = false;
        p.isCloaked     = false;

        return p;
    }
}
