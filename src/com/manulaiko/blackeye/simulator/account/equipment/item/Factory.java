package com.manulaiko.blackeye.simulator.account.equipment.item.ship;

import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Ship factory
 *
 * Used for instance ship objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.account.ship
 */
public class Factory
{
    /**
     * Instanced hangars
     */
    private java.util.HashMap<Integer, Ship> _ships = new java.util.HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given ship
     *
     * @param id Ship id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist
     */
    public Ship getByID(int id) throws com.manulaiko.tabitha.exceptions.NotFound
    {
        if(!this._ships.containsKey(id)) {
            Ship s = this.loadByID(id);

            this._ships.put(id, s);

            return s;
        }

        return this._ships.get(id);
    }

    /**
     * Returns all ships
     *
     * @return All ships
     */
    public java.util.HashMap<Integer, Ship> getAllShips()
    {
        return this._ships;
    }

    /**
     * Returns the amount of loaded ships
     *
     * @return Amount of loaded ships
     */
    public int getAmount()
    {
        return this._ships.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a ship by its ID
     *
     * @param id Ship id
     *
     * @return The ship
     *
     * @throws NotFound If ship doesn't exist in database
     */
    public Ship loadByID(int id) throws com.manulaiko.tabitha.exceptions.NotFound
    {
        try {
            java.sql.PreparedStatement ps = com.manulaiko.blackeye.launcher.Main.mysqlManager.prepare("SELECT * FROM `accounts_equipment_ships` WHERE `id`=?");
            ps.setInt(1, id);

            java.sql.ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getShip();
            } else {
                throw new com.manulaiko.tabitha.exceptions.NotFound("ship", "id: " + id);
            }
        } catch(java.sql.SQLException e) {
            throw new com.manulaiko.tabitha.exceptions.NotFound("ship", "id: " + id);
        }
    }

    /**
     * Loads all ships from database
     */
    public void loadAll()
    {
        try {
            java.sql.ResultSet result = com.manulaiko.blackeye.launcher.Main.mysqlManager.query("SELECT * FROM `accounts_equipment_ships`");

            while(result.next()) {
                Builder builder = new Builder(result);

                Ship s = builder.getShip();

                this._ships.put(s.id, s);
            }
        } catch(Exception e) {
            com.manulaiko.tabitha.Console.println("Couldn't load ships!");
            com.manulaiko.tabitha.Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////
}
