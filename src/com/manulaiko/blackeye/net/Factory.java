package com.manulaiko.blackeye.net;

import java.util.HashMap;
import java.util.Map.Entry;

import com.manulaiko.blackeye.net.utils.Command;
import com.manulaiko.blackeye.net.utils.Packet;
import com.manulaiko.blackeye.net.utils.PacketParser;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.reflection.Instantiation;

/**
 * Packet factory
 *
 * Factory for building packets and retrieving handlers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory
{
    /**
     * Available packet handlers.
     *
     * @var Packet handlers.
     */
    protected HashMap<String, String> _packets = new HashMap<>();

    /**
     * Available packet commands.
     *
     * @var Packet commands.
     */
    protected HashMap<String, String> _commands = new HashMap<>();

    /**
     * Package where handlers are located.
     *
     * @var Handler package.
     */
    private String _handlerPackage = "com.manulaiko.blackeye.net.game.packets.handlers";

    /**
     * Package where handlers are located.
     *
     * @var Handler package.
     */
    private String _commandPackage = "com.manulaiko.blackeye.net.game.packets.commands";

    /**
     * Constructor.
     *
     * @param handlerPackage Handler package.
     */
    public Factory(String handlerPackage, String commandPackage)
    {
        this._handlerPackage = handlerPackage;
        this._commandPackage = commandPackage;
    }

    /**
     * Finds and returns given packet handler.
     *
     * @param packet Packet to find handler.
     *
     * @return Packet's handler.
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
     * Finds and returns given packet handler.
     *
     * @param id Packet to find handler.
     *
     * @return Packet's handler.
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
     * Finds and returns given packet handler by its name.
     *
     * @param name Packet name.
     *
     * @return Packet's handler.
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
     * Instances and returns a packet handler.
     *
     * @param name Handler's name.
     *
     * @return Instanced handler.
     */
    private Packet _instanceHandler(String name)
    {
        Packet p = (Packet) Instantiation.instance(this._handlerPackage +"."+ name);

        return p;
    }

    /**
     * Finds and returns given packet handler.
     *
     * @param id Packet to find handler.
     *
     * @return Packet's handler.
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
     * Finds and returns given packet handler by its name.
     *
     * @param name Packet name.
     *
     * @return Packet's handler.
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
     * Instances and returns a packet handler.
     *
     * @param name Handler's name.
     *
     * @return Instanced handler.
     */
    private Command _instanceCommand(String name)
    {
        Command c = (Command) Instantiation.instance(this._commandPackage +"."+ name);

        return c;
    }
}
