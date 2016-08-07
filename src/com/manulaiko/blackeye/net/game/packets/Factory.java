package com.manulaiko.blackeye.net.game.packets;

import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.net.game.utils.PacketParser;

import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.reflection.Instantiation;

/**
 * Packet factory
 *
 * Factory for building packets and retrieving handlers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.game.packets
 */
public class Factory
{
    /**
     * Available packet handlers
     */
    private HashMap<String, String> _packets = new HashMap<>();

    /**
     * Available packet commands
     */
    private HashMap<String, String> _commands = new HashMap<>();

    /**
     * Constructor
     *
     * Sets available packets and their handlers
     */
    public Factory()
    {
        Console.println("Loading game packet handlers and commands...");

        ///////////////////////////
        // Start Packet Handlers //
        ///////////////////////////
        this._packets.put("LOGIN", "LoginRequest");
        this._packets.put("0", "EchoPacket");
        this._packets.put("PNG", "Ping");
        this._packets.put("1", "Movement");
        /////////////////////////
        // End Packet Handlers //
        /////////////////////////

        ///////////////////////////
        // Start Packet Commands //
        ///////////////////////////
        this._commands.put("I", "ShipInitialization");
        this._commands.put("C", "CreateShip");
        this._commands.put("p", "CreatePortal");
        this._commands.put("c", "CreateCollectable");
        this._commands.put("s", "CreateStation");
        this._commands.put("R", "RemoveShip");
        this._commands.put("K", "DestroyShip");
        this._commands.put("1", "Move");
        /////////////////////////
        // End Packet Commands //
        /////////////////////////

        Console.println(this._packets.size() + " packet handlers and commands loaded!");
    }

    /**
     * Finds and returns given packet handler
     *
     * @param packet Packet to find handler
     *
     * @return Packet's handler
     */
    public Packet get(PacketParser packet)
    {
        String id = packet.readString();

        if(!this._packets.containsKey(id)) {
            Console.println("Packet "+ id +" doesn't exists!");
            return null;
        }

        Packet p = this.getByID(id);
        p.setPacket(packet);

        return p;
    }

    /**
     * Finds and returns given packet handler
     *
     * @param id Packet to find handler
     *
     * @return Packet's handler
     */
    public Packet getByID(String id)
    {
        if(!this._packets.containsKey(id)) {
            Console.println("Packet "+ id +" doesn't exists!");
            return null;
        }

        return this.getByName(this._packets.get(id));
    }

    /**
     * Finds and returns given packet handler by its name
     *
     * @param name Packet name
     *
     * @return Packet's handler
     */
    public Packet getByName(String name)
    {
        for(Entry<String, String> packet : this._packets.entrySet()) {
            if(packet.getValue().equalsIgnoreCase(name)) {
                return this._instanceHandler(name);
            }
        }

        Console.println("Packet "+ name +" doesn't exists!");
        return null;
    }

    /**
     * Instances and returns a packet handler
     *
     * @param name Handler's name
     *
     * @return Instanced handler
     */
    private Packet _instanceHandler(String name)
    {
        Packet p = (Packet) Instantiation.instance("com.manulaiko.blackeye.net.game.packets.handlers."+ name);

        return p;
    }

    /**
     * Finds and returns given packet handler
     *
     * @param id Packet to find handler
     *
     * @return Packet's handler
     */
    public Packet getCommandByID(String id)
    {
        if(!this._commands.containsKey(id)) {
            Console.println("Command "+ id +" doesn't exists!");
            return null;
        }

        return this.getByName(this._commands.get(id));
    }

    /**
     * Finds and returns given packet handler by its name
     *
     * @param name Packet name
     *
     * @return Packet's handler
     */
    public Command getCommandByName(String name)
    {
        for(Entry<String, String> command : this._commands.entrySet()) {
            if(command.getValue().equalsIgnoreCase(name)) {
                return this._instanceCommand(name);
            }
        }

        Console.println("Command "+ name +" doesn't exists!");
        return null;
    }

    /**
     * Instances and returns a packet handler
     *
     * @param name Handler's name
     *
     * @return Instanced handler
     */
    private Command _instanceCommand(String name)
    {
        Command c = (Command) Instantiation.instance("com.manulaiko.blackeye.net.game.packets.commands."+ name);

        return c;
    }
}
