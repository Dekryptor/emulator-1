package com.manulaiko.blackeye.simulator.account.handler;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.utils.Updatable;
import com.manulaiko.tabitha.Console;

/**
 * Attack handler.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Attack implements Updatable
{
    /**
     * Target simulator.
     *
     * @var Target.
     */
    public Simulator target;

    /**
     * Attacker simulator.
     *
     * @var Attacker.
     */
    public Simulator attacker;

    /**
     * Attacker ID.
     *
     * This way we avoid useless castings.
     *
     * @var Attacker ID.
     */
    private int _id;

    /**
     * Whether the handler is attacking or not.
     *
     * @var Attack status.
     */
    public boolean isAttacking;

    /**
     * Last laser shoot.
     *
     * @var Last laser shoot.
     */
    private long _lastLaserShoot = 0;

    /**
     * Next laser shoot.
     *
     * @var Next laser shoot.
     */
    private long _nextLaserShoot = 0;

    /**
     * Stops laser attack.
     */
    public void stopLaserAttack()
    {
        this.isAttacking = false;

        GameManager.updaterManager.unsubscribe(this);
    }

    /**
     * Starts laser attack.
     */
    public void startLaserAttack()
    {
        if(this.attacker instanceof NPC) {
            this._id = ((NPC)this.attacker).id;
        } else if(this.attacker instanceof Account) {
            this._id = ((Account)this.attacker).id;
        } else {
            return;
        }

        this.isAttacking = true;

        this._lastLaserShoot = System.currentTimeMillis();
        this._nextLaserShoot = this._lastLaserShoot - 1;

        GameManager.updaterManager.subscribe(this);
    }

    /**
     * Updates laser attack status.
     */
    public void update()
    {
        if(this._lastLaserShoot < this._nextLaserShoot) {
            return;
        }

        Console.println(
                "Last: "+ this._lastLaserShoot,
                " Next: "+ this._nextLaserShoot,
                " Current: "+ System.currentTimeMillis()
        );

        this._lastLaserShoot  = this._nextLaserShoot;
        this._nextLaserShoot += 1000;

        if(this.target instanceof NPC) {
            this._updateLaserAttackToNPC();
        } else if(this.target instanceof Account) {
            this._updateLaserAttackToAccount();
        }
    }

    /**
     * Updates NPC laser attack.
     */
    private void _updateLaserAttackToNPC()
    {
        NPC target = (NPC)this.target;

        if(this.attacker instanceof Account) {
            Account attacker = (Account)this.attacker;

            attacker.connection.send("0|a|" + this._id + "|" + target.id + "|1|0|0");
            attacker.hangar.ship.nearAccounts.forEach((i, a)->{
                a.connection.send("0|a|" + this._id + "|" + target.id + "|1|0|0");
            });
        }
    }

    /**
     * Updates Account laser attack.
     */
    private void _updateLaserAttackToAccount()
    {
        Account target = (Account)this.target;
    }
}
