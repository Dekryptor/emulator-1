package com.manulaiko.blackeye.simulator.account.handler;

import java.util.concurrent.ThreadLocalRandom;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.packet.command.AttackInfo;
import com.manulaiko.blackeye.net.game.packet.command.DestroyShip;
import com.manulaiko.blackeye.net.game.packet.command.LaserAttack;
import com.manulaiko.blackeye.net.game.packet.command.LootMessage;
import com.manulaiko.blackeye.simulator.Simulator;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.npc.NPC;
import com.manulaiko.blackeye.utils.Updatable;
import com.manulaiko.tabitha.utils.Point;

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

            this._doLaserDamage(attacker, target);

            LaserAttack p = attacker.getLaserAttackCommand();

            p.target = target.id;

            attacker.hangar.ship.map.broadcastPacket(p.toString());
        }
    }

    /**
     * Performs the laser damage.
     *
     * The attacker is an account and is damaging an NPC.
     *
     * @param attacker Attacker account.
     * @param target   Target NPC.
     */
    public void _doLaserDamage(Account attacker, NPC target)
    {
        int damage = attacker.hangar.activeConfiguration.damage;

        damage = ThreadLocalRandom.current()
                                  .nextInt(
                                          (int)(damage * 0.90),
                                          damage + 1
                                  );

        // TODO add different ammunition type (`damage * ammoType`)

        int shieldDamage = (int)(damage * target.shieldAbs) / 100;
        int healthDamage = damage - shieldDamage;

        if(shieldDamage >= target.shield) {
            shieldDamage  = target.shield;
            healthDamage += damage - shieldDamage;

            target.shield = 0;
        }

        target.shield -= shieldDamage;
        target.health -= healthDamage;

        if(target.health <= 0) {
            this._destroy(attacker, target);

            this.stopLaserAttack();
        }

        AttackInfo p = (AttackInfo)ServerManager.game.packetFactory.getCommandByName("AttackInfo");

        p.attacker = attacker.id;
        p.target   = target.id;
        p.shield   = target.shield;
        p.health   = target.health;
        p.damage   = damage;

        attacker.connection.send(p);
    }

    /**
     * Destroys the target.
     *
     * The account has successfully destroyed the target.
     *
     * @param attacker Attacker account.
     * @param target   Target NPC.
     */
    private void _destroy(Account attacker, NPC target)
    {
        DestroyShip p = target.getDestroyShipCommand();

        attacker.hangar.ship.map.broadcastPacket(p.toString());

        this._reward(attacker, target.reward);
        this._respawn(target, attacker.hangar.ship.map.limits);
    }

    /**
     * Awards NPC's reward.
     *
     * @param attacker Attacker to award.
     * @param reward   Reward.
     */
    private void _reward(Account attacker, NPC.Reward reward)
    {
        attacker.experience += reward.experience;
        attacker.honor      += reward.honor;
        attacker.credits    += reward.credits;
        attacker.uridium    += reward.uridium;

        // TODO Create cargo box

        LootMessage p = (LootMessage)ServerManager.game.packetFactory.getCommandByName("LootMessage");
        p.type        = LootMessage.EXPERIENCE;
        p.value       = reward.experience;
        p.newValue    = attacker.experience;
        attacker.connection.send(p);

        p          = (LootMessage)ServerManager.game.packetFactory.getCommandByName("LootMessage");
        p.type     = LootMessage.HONOR;
        p.value    = reward.honor;
        p.newValue = attacker.honor;
        attacker.connection.send(p);

        p          = (LootMessage)ServerManager.game.packetFactory.getCommandByName("LootMessage");
        p.type     = LootMessage.CREDITS;
        p.value    = reward.credits;
        p.newValue = attacker.credits;
        attacker.connection.send(p);

        p          = (LootMessage)ServerManager.game.packetFactory.getCommandByName("LootMessage");
        p.type     = LootMessage.URIDIUM;
        p.value    = reward.uridium;
        p.newValue = attacker.uridium;
        attacker.connection.send(p);
    }

    /**
     * Respawns the target.
     *
     * @param target Target to respawn.
     * @param limits Map limits.
     */
    private void _respawn(NPC target, Point limits)
    {
        target.health   = target.maxHealth;
        target.shield   = target.maxShield;
        target.position = new Point(
                ThreadLocalRandom.current()
                                 .nextInt(
                                         0,
                                         limits.getX()
                                 ),
                ThreadLocalRandom.current()
                                 .nextInt(
                                         0,
                                         limits.getY()
                                 )
        );
    }

    /**
     * Updates Account laser attack.
     */
    private void _updateLaserAttackToAccount()
    {
        Account target = (Account)this.target;
    }
}
