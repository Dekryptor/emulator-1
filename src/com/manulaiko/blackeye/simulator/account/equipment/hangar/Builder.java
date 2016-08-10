package com.manulaiko.blackeye.simulator.account.equipment.hangar;

import java.sql.ResultSet;

import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.blackeye.simulator.account.equipment.ship.Ship;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Hangar builder class
 *
 * Implements the builder design pattern
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Builder
{
    /**
     * Hangar object
     *
     * The current hangar we're building
     */
    private Hangar _hangar;

    /**
     * Constructor
     *
     * @param rs Query result
     */
    public Builder(ResultSet rs)
    {
        try {
            this._hangar = new Hangar(
                    rs.getInt("id"),
                    rs.getInt("accounts_id"),
                    rs.getString("resources")
            );

            this._build(rs.getInt("accounts_equipment_ships_id"));
        } catch(Exception e) {
            Console.println("Couldn't build hangar!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Cloning constructor
     *
     * Use this constructor for cloning a hangar
     *
     * @para hangar Hangar to clone
     */
    public Builder(Hangar hangar)
    {
        try {
            this._hangar = new Hangar(
                    hangar.id,
                    hangar.accountID,
                    hangar.resourcesJSON
            );

        } catch(Exception e) {
            Console.println("Couldn't clone hangar!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Builds the hangar
     *
     * @param accounts_equipment_ships_id Equipped ship ID
     */
    private void _build(int accounts_equipment_ships_id)
    {
        try {
            Ship s = GameManager.accounts.hangars.ships.getByID(accounts_equipment_ships_id);

            this._hangar.setShip(s);
            this._hangar.setConfigurations(GameManager.accounts.hangars.configurations.getByShipID(s.id));

            this._hangar.changeConfiguration(s.activeConfiguration);
        } catch(NotFound e) {
            Console.println("Ship "+ accounts_equipment_ships_id +" does not exist!");
        }
    }

    /**
     * Returns the hangar
     *
     * @return The hangar
     */
    public Hangar getHangar()
    {
        return this._hangar;
    }
}
