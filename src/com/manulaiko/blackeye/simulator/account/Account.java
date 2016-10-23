package com.manulaiko.blackeye.simulator.account;

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
}
