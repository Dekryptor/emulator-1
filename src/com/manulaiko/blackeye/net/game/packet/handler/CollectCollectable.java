package com.manulaiko.blackeye.net.game.packet.handler;

import com.manulaiko.blackeye.launcher.GameManager;
import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.game.Connection;
import com.manulaiko.blackeye.net.game.packet.command.LogMessage;
import com.manulaiko.blackeye.simulator.account.Account;
import com.manulaiko.blackeye.simulator.item.Item;
import com.manulaiko.blackeye.simulator.level.Level;
import com.manulaiko.blackeye.simulator.map.Map;
import com.manulaiko.blackeye.simulator.map.collectable.Collectable;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.utils.Point;
import com.manulaiko.tabitha.utils.Tools;

/**
 * Collect collectable handler.
 *
 * Handles collectable's collection.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class CollectCollectable extends com.manulaiko.blackeye.net.utils.Packet
{
    /**
     * Handles the packet.
     *
     * @param connection Connection that received the packet.
     */
    public void handle(Connection connection)
    {
        int id = this._packet.readInt();
        Map m  = connection.account.hangar.ship.map;

        if(!m.collectables.containsKey(id)) {
            return;
        }

        Point p  = connection.account.hangar.ship.position;
        Point mP = new Point(
                Main.configuration.getInt("maps.entity_range"),
                Main.configuration.getInt("maps.entity_range")
        );

        Collectable c = m.collectables.get(id);
        if(!c.position.isInRange(p, mP)) {
            return;
        }

        this._reward(c, connection.account);

        m.collectables.remove(id);
    }

    /**
     * Adds collectable's rewards to account.
     *
     * @param collectable Collectable to reward.
     * @param account     Account that collected the collectable.
     */
    private void _reward(Collectable collectable, Account account)
    {
        collectable.rewards.forEach((r)->{
            if((Tools.r.nextDouble() * 100) < r.probability) {
                if(collectable.gfx < 2) {
                    this._addResource(r.itemsID, r.amount, account);
                } else if(r.itemsID < 0) {
                    this._addCurrency(r.itemsID, r.amount, account);
                } else {
                    this._addItem(r.itemsID, r.amount, account);
                }
            }
        });
    }

    /**
     * The reward is a resource.
     *
     * @param id      Resource ID.
     * @param amount  Amount to add.
     * @param account Account to add resources.
     */
    private void _addResource(int id, int amount, Account account)
    {
        int resources = account.hangar.resources.get(id);

        account.hangar.resources.put(id, (resources + amount));

        LogMessage p = (LogMessage)ServerManager.game.packetFactory.getCommandByName("LogMessage");

        p.type     = LogMessage.ORE;
        p.value    = amount;
        p.newValue = id;

        account.connection.send(p);
    }

    /**
     * The reward is a currency (exp, hon, jackpot, credits, uri ...).
     *
     * @param id      Currency ID.
     * @param amount  Amount to add.
     * @param account Account to add currency.
     */
    private void _addCurrency(int id, int amount, Account account)
    {
        LogMessage p = (LogMessage)ServerManager.game.packetFactory.getCommandByName("LogMessage");

        switch(+(id))
        {
            case 1:
                account.credits += amount;

                p.type     = LogMessage.CREDITS;
                p.value    = amount;
                p.newValue = account.credits;

                account.connection.send(p);
                break;

            case 2:
                account.uridium += amount;

                p.type     = LogMessage.URIDIUM;
                p.value    = amount;
                p.newValue = account.uridium;

                account.connection.send(p);
                break;

            case 3:
                account.jackpot += amount;

                p.type     = LogMessage.JACKPOT;
                p.value    = amount;
                p.newValue = (int)account.jackpot;

                account.connection.send(p);
                break;

            case 4:
                account.experience += amount;
                //account.checkLevel();

                p.type     = LogMessage.EXPERIENCE;
                p.value    = amount;
                p.newValue = account.experience;

                account.connection.send(p);
                break;

            case 5:
                account.honor += amount;

                p.type     = LogMessage.HONOR;
                p.value    = amount;
                p.newValue = account.honor;

                account.connection.send(p);
                break;

            // TODO gg spins, jump vouchers...
        }
    }

    /**
     * The reward is an item.
     *
     * @param id      Item ID.
     * @param amount  Amount to add.
     * @param account Account to add resources.
     */
    private void _addItem(int id, int amount, Account account)
    {
        try {
            Item i = (Item)GameManager.items.getByID(id);

            com.manulaiko.blackeye.simulator.account.equipment.item.Item item = com.manulaiko.blackeye.simulator.account.equipment.item.Item.create();

            item.accountID = account.id;
            item.amount    = amount;
            item.item      = i;
            item.itemID    = i.id;
            item.levelID   = 1;
            item.level     = (Level)GameManager.levels.getByID(1);

            item.save();

            account.addItem(item);

            LogMessage p = (LogMessage)ServerManager.game.packetFactory.getCommandByName("LogMessage");

            p.type     = LogMessage.ITEM;
            p.value    = i.lootID;
            p.newValue = amount;

            account.connection.send(p);
        } catch(Exception e) {
            Console.println("Couldn't add item!");
            Console.println(e.getMessage());
        }
    }
}
