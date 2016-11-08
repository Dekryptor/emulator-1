package com.manulaiko.blackeye.simulator.account.handler;

import com.manulaiko.blackeye.simulator.Simulator;

/**
 * Attack handler.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Attack
{
    /**
     * Target simulator.
     *
     * @var Target.
     */
    public Simulator target;

    /**
     * Whether the handler is attacking or not.
     *
     * @var Attack status.
     */
    public boolean isAttacking;

    /**
     * Stops laser attack.
     */
    public void stopLaserAttack()
    {
        this.isAttacking = false;
    }

    /**
     * Starts laser attack.
     */
    public void startLaserAttack()
    {
        this.isAttacking = true;
    }
}
