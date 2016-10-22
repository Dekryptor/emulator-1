package com.manulaiko.blackeye.simulator.account;

import com.manulaiko.blackeye.simulator.clan.Clan;
import com.manulaiko.blackeye.simulator.level.Level;

/**
 * Account class.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Account
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
     */
    public int levelsID;

    /**
     * Whether the account is premium or not.
     */
    public boolean isPremium;

    /**
     * Rank ID.
     */
    public int ranksID;

    /**
     * Rank points.
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
     * @param isPremium  Whether is premium or not.
     * @param ranksID    Rank ID.
     * @param rankPoints Rank points.
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
     * Sets level object
     *
     * @param level Level object
     */
    public void setLevel(Level level)
    {
        this.level = level;
    }
}
