package com.manulaiko.blackeye.simulator.account.handler;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.net.game.packet.command.LaserAttack;
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

        this._nextLaserShoot = System.currentTimeMillis();

        GameManager.updaterManager.subscribe(this);
    }

    /**
     * Updates laser attack status.
     */
    public void update()
    {
        if(System.currentTimeMillis() < this._nextLaserShoot) {
            return;
        }

        Console.println(
                " Next: "+ this._nextLaserShoot,
                " Current: "+ System.currentTimeMillis()
        );

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

            LaserAttack p = attacker.getLaserAttackCommand();

            p.target = target.id;

            attacker.connection.send(p);
            attacker.hangar.ship.nearAccounts.forEach((i, a)->{
                a.connection.send(p);
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
