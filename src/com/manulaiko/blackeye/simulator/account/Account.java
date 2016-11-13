package com.manulaiko.blackeye.simulator.account;

import java.util.HashMap;
import java.util.Map;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.*;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.account.equipment.configuration.Configuration;
import com.manulaiko.blackeye.simulator.account.equipment.hangar.Hangar;
import com.manulaiko.blackeye.simulator.account.equipment.item.Item;
import com.manulaiko.blackeye.simulator.account.handler.Attack;
import com.manulaiko.blackeye.simulator.account.settings.Settings;
import com.manulaiko.blackeye.simulator.clan.Clan;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.tabitha.Console;

/**
 * Account class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Account extends Simulator implements Cloneable
{
    /**
     * Instances and returns a new Simulator.
     *
     * @return New Simulator object.
     */
    public static Account create()
    {
        Account s       = new Account(0, "", "", 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0);
        s._isInsert     = true;
        s.databaseTable = "accounts";

        return s;
    }

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
     * Settings object.
     *
     * @var Settings.
     */
    public Settings settings;

    /**
     * Account items.
     *
     * @var Items.
     */
    public HashMap<Integer, Item> items = new HashMap<>();

    /**
     * Connection object.
     *
     * @var Connection.
     */
    public Connection connection;

    /**
     * Attack handler.
     *
     * @var Attack handler.
     */
    public Attack attack = new Attack();

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

        this.attack.attacker = this;
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
     * Sets settings object.
     *
     * @param settings Settings object.
     */
    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    /**
     * Adds an item to the array.
     *
     * @param item Item to add.
     */
    public void addItem(Item item)
    {
        this.items.put(item.id, item);
    }

    /**
     * Sets account items.
     *
     * @var items Items map.
     */
    public void setItems(HashMap<Integer, Item> items)
    {
        this.items = items;
    }

    /**
     * Returns item with given loot ID.
     *
     * @param id Loot ID.
     *
     * @return Item with given loot ID.
     */
    public Item getItemByLootID(String id)
    {
        for(Map.Entry<Integer, Item> item : this.items.entrySet()) {
            if(item.getValue().item.lootID.equalsIgnoreCase(id)) {
                return item.getValue();
            }
        }

        return null;
    }

    /**
     * Destroys the ship by an NPC.
     *
     * @param npc NPC that destroyed the account.
     */
    public void destroy(NPC npc)
    {
        // Maybe I can make something cool like digievolving the NPC when it
        // reaches certain amount of accounts killed :D
        this._destroy();
    }

    /**
     * Destroys the ship by another Account.
     *
     * @param account Account that destroyed the account.
     */
    public void destroy(Account account)
    {
        this._destroy();

        account.experience += this.hangar.ship.ship.reward.experience;
        account.honor      += this.hangar.ship.ship.reward.honor;

        if(account.connection == null) {
            return;
        }

        LogMessage p = (LogMessage)ServerManager.game.packetFactory.getCommandByName("LogMessage");
        p.type        = LogMessage.EXPERIENCE;
        p.value       = this.hangar.ship.ship.reward.experience;
        p.newValue    = account.experience;
        account.connection.send(p);

        p          = (LogMessage)ServerManager.game.packetFactory.getCommandByName("LogMessage");
        p.type     = LogMessage.HONOR;
        p.value    = this.hangar.ship.ship.reward.honor;
        p.newValue = account.honor;
        account.connection.send(p);
    }

    /**
     * Sends destroy command to near accounts.
     */
    private void _destroy()
    {
        this.hangar.ship.nearAccounts.forEach((i, a)->{
            if(a.connection != null) {
                a.connection.send(this.getDestroyShipCommand());
            }
        });

        if(this.connection == null) {
            return;
        }

        this.connection.send(this.getDestroyShipCommand());
    }

    /**
     * Returns all items with given loot ID.
     *
     * @param id Loot ID.
     *
     * @return Items with given loot ID.
     */
    public HashMap<Integer, Item> getItemsByLootID(String id)
    {
        HashMap<Integer, Item> items = new HashMap<>();

        this.items.forEach((i, item)->{
            if(item.item.lootID.equalsIgnoreCase(id)) {
                items.put(i, item);
            }
        });

        return items;
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
            a.setHangar(this.hangar.clone());
            a.setSettings(this.settings.clone());

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
        p.clanTag       = (this.clan == null) ? "" : this.clan.tag;
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

    /**
     * Builds and returns the BatteriesInitialization command.
     *
     * @return BatteriesInitialization command.
     */
    public BatteriesInitialization getBatteriesInitializationCommand()
    {
        BatteriesInitialization p = (BatteriesInitialization) ServerManager.game.packetFactory.getCommandByName("BatteriesInitialization");

        p.lcb10  = 0; //this.getItemByLootID("lcb_10").amount;
        p.mcb25  = 0; //this.getItemByLootID("mcb_25").amount;
        p.mcb50  = 0; //this.getItemByLootID("mcb_50").amount;
        p.ucb100 = 0; //this.getItemByLootID("ucb_100").amount;
        p.sab50  = 0; //this.getItemByLootID("sab_50").amount;
        p.rsb75  = 0; //this.getItemByLootID("rsb_75").amount;

        return p;
    }

    /**
     * Builds and returns the RocketsInitialization command.
     *
     * @return RocketsInitialization command.
     */
    public RocketsInitialization getRocketsInitializationCommand()
    {
        RocketsInitialization p = (RocketsInitialization) ServerManager.game.packetFactory.getCommandByName("RocketsInitialization");

        // TODO add rockets to initial database items
        p.r310    = 0;
        p.plt2026 = 0;
        p.plt2021 = 0;
        p.plt3031 = 0;
        p.pld8    = 0;
        p.wiz     = 0;
        p.dcr     = 0;
        p.acm     = 0;
        p.ish     = 0;
        p.smb     = 0;
        p.pem     = 0;
        p.mpem    = 0;
        p.ddm     = 0;
        p.sabm    = 0;

        return p;
    }

    /**
     * Builds and returns the RemoveShip command.
     *
     * @return RemoveShip command.
     */
    public RemoveShip getRemoveShipCommand()
    {
        RemoveShip p = (RemoveShip)ServerManager.game.packetFactory.getCommandByName("RemoveShip");

        p.id = this.id;

        return p;
    }

    /**
     * Builds and returns the DestroyShip command.
     *
     * @return DestroyShip command.
     */
    public DestroyShip getDestroyShipCommand()
    {
        DestroyShip p = (DestroyShip)ServerManager.game.packetFactory.getCommandByName("DestroyShip");

        p.id = this.id;

        return p;
    }

    /**
     * Returns table identifier.
     * 
     * @return Table ID.
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

        fields.put("id", this.id);
        fields.put("session_id", this.sessionID);
        fields.put("name", this.name);
        fields.put("factions_id", this.factionsID);
        fields.put("clans_id", this.clansID);
        fields.put("uridium", this.uridium);
        fields.put("credits", this.credits);
        fields.put("jackpot", this.jackpot);
        fields.put("experience", this.experience);
        fields.put("honor", this.honor);
        fields.put("levels_id", this.levelID);
        fields.put("is_premium", this.isPremium);
        fields.put("ranks_id", this.rankID);
        fields.put("rank_points", this.rankPoints);
        fields.put("accounts_equipment_hangars_id", this.hangar.id);
        
        return fields;
    }

    /**
     * Builds and returns SelectShip command.
     *
     * @return SelectShip command.
     */
    public SelectShip getSelectShipCommand()
    {
        SelectShip p = (SelectShip)ServerManager.game.packetFactory.getCommandByName("SelectShip");

        p.shipID    = this.hangar.ship.ship.id;
        p.health    = this.hangar.ship.health;
        p.maxHealth = this.hangar.ship.ship.health;
        p.shield    = this.hangar.ship.shield;
        p.maxShield = this.hangar.activeConfiguration.shield;

        return p;
    }

    /**
     * Builds and returns LaserAttack command.
     *
     * @return LaserAttack command.
     */
    public LaserAttack getLaserAttackCommand()
    {
        LaserAttack p = (LaserAttack)ServerManager.game.packetFactory.getCommandByName("LaserAttack");
        Configuration c = this.hangar.activeConfiguration;

        int gfx = 0;

        if(c.lasers.size() == c.eliteLasers) {
            gfx = 1;
        }

        p.attacker = this.id;
        p.gfx      = gfx;
        //p.laserSkill = this.profile.hasLaserSkillGfx();

        return p;
    }
}
