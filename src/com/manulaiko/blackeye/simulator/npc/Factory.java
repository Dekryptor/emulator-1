package com.manulaiko.blackeye.simulator.npc;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * NPC factory
 *
 * Used for instance npc objects with lazy-load
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.simulator.npc
 */
public class Factory
{
    /**
     * Instanced maps
     */
    private HashMap<Integer, NPC> _npcs = new HashMap<>();

    ///////////////////////
    // Start get methods //
    ///////////////////////
    /**
     * Returns given npc
     *
     * @param id NPC id
     *
     * @return The npc
     *
     * @throws NotFound If npc doesn't exist
     */
    public NPC getByID(int id) throws NotFound
    {
        if(!this._npcs.containsKey(id)) {
            NPC n = this.loadByID(id);

            this._npcs.put(id, n);

            return n;
        }

        return this._npcs.get(id);
    }

    /**
     * Returns given npc
     *
     * @param name NPC name
     *
     * @return The npc
     *
     * @throws NotFound If npc doesn't exist
     */
    public NPC getByName(String name) throws NotFound
    {
        for(Entry<Integer, NPC> n : this._npcs.entrySet()) {
            if(n.getValue().name.equals(name)) {
                return n.getValue();
            }
        }

        NPC n = this.loadByName(name);

        this._npcs.put(n.id, n);

        return n;
    }

    /**
     * Returns all maps
     *
     * @return All maps
     */
    public HashMap<Integer, NPC> getAllNPCs()
    {
        return this._npcs;
    }

    /**
     * Returns the amount of loaded maps
     *
     * @return Amount of loaded maps
     */
    public int getAmount()
    {
        return this._npcs.size();
    }
    /////////////////////
    // End get methods //
    /////////////////////

    ////////////////////////
    // Start load methods //
    ////////////////////////
    /**
     * Builds and returns a npc by its ID
     *
     * @param id NPC id
     *
     * @return The npc
     *
     * @throws NotFound If npc doesn't exist in database
     */
    public NPC loadByID(int id) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `npcs` WHERE `id`=?");
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getNPC();
            } else {
                throw new NotFound("npc", "id: "+ id);
            }
        } catch(SQLException e) {
            throw new NotFound("npc", "id: "+ id);
        }
    }

    /**
     * Builds and returns a npc by its name
     *
     * @param name NPC name
     *
     * @return The npc
     *
     * @throws NotFound If npc doesn't exist in database
     */
    public NPC loadByName(String name) throws NotFound
    {
        try {
            PreparedStatement ps = Main.mysqlManager.prepare("SELECT * FROM `npcs` WHERE `name`=?");
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if(result.next()) {
                Builder builder = new Builder(result);

                return builder.getNPC();
            } else {
                throw new NotFound("npc", name);
            }
        } catch(SQLException e) {
            throw new NotFound("npc", name);
        }
    }

    /**
     * Loads all maps from database
     */
    public void loadAll()
    {
        try {
            ResultSet result = Main.mysqlManager.query("SELECT * FROM `npcs`");

            while(result.next()) {
                Builder builder = new Builder(result);
                
                NPC m = builder.getNPC();

                this._npcs.put(m.id, m);
            }
        } catch(Exception e) {
            Console.println("Couldn't load npc!");
            Console.println(e.getMessage());
        }
    }
    //////////////////////
    // End load methods //
    //////////////////////

    /////////////////////////
    // Start clone methods //
    /////////////////////////
    /**
     * Clones given npc
     *
     * @param npc NPC to clone
     *
     * @return Cloned npc
     */
    public NPC clone(NPC npc)
    {
        Builder b = new Builder(npc);

        return b.getNPC();
    }

    /**
     * Clones given npc by ID
     *
     * @param id NPC id
     *
     * @return Cloned npc
     *
     * @throws NotFound If npc id doesn't exit
     */
    public NPC cloneByID(int id) throws NotFound
    {
        NPC npc = this.getByID(id);

        Builder b = new Builder(npc);

        return b.getNPC();
    }

    /**
     * Clones given npc by name
     *
     * @param name NPC name
     *
     * @return Cloned npc
     *
     * @throws NotFound If npc name doesn't exit
     */
    public NPC cloneByName(String name) throws NotFound
    {
        NPC npc = this.getByName(name);

        Builder b = new Builder(npc);

        return b.getNPC();
    }
    ///////////////////////
    // End clone methods //
    ///////////////////////
}
