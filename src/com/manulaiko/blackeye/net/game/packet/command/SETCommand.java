package com.manulaiko.blackeye.net.game.packet.command;

/**
 * SET command.
 *
 * Builds the SET command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SETCommand extends com.manulaiko.blackeye.net.utils.Command
{
    /////////////////////
    // Start Variables //
    /////////////////////
    public boolean boosten = false, dsplyDamage = false, dsplyAllLas = false, dsplyExplo = false,
        dsplyPlayerName = false, dsplyFirmIcon = false, dsplyAlphaBg = false, ignoreRES = false, ignoreBOX = false,
        convertGates = false, convertOppo = false, soundOnOff = false, bgmusicOnOff = false, dsplyStatus = false,
        dsplyBubble = false, selectedLaser = false, selectedRocket = false, dsplyDigits = false, dsplyChat = false,
        dsplyDrones = false, showStarsystem = false, ignoreCARGO = false, ignoreHostileCARGO = false, autochangeAmmo = false,
        enableFastBuy = false;
    ///////////////////
    // End Variables //
    ///////////////////

    /**
     * Returns packet as string.
     *
     * @return Packet as string.
     */
    public String toString()
    {
        this.add("SET");
        this.add(this.boosten);
        this.add(this.dsplyDamage);
        this.add(this.dsplyAllLas);
        this.add(this.dsplyExplo);
        this.add(this.dsplyPlayerName);
        this.add(this.dsplyFirmIcon);
        this.add(this.dsplyAlphaBg);
        this.add(this.ignoreRES);
        this.add(this.ignoreBOX);
        this.add(this.convertGates);
        this.add(this.convertOppo);
        this.add(this.soundOnOff);
        this.add(this.bgmusicOnOff);
        this.add(this.dsplyStatus);
        this.add(this.dsplyBubble);
        this.add(this.selectedLaser);
        this.add(this.selectedRocket);
        this.add(this.dsplyDigits);
        this.add(this.dsplyChat);
        this.add(this.dsplyDrones);
        this.add(this.showStarsystem);
        this.add(this.ignoreCARGO);
        this.add(this.ignoreHostileCARGO);
        this.add(this.autochangeAmmo);
        this.add(this.enableFastBuy);

        return super.toString();
    }
}
