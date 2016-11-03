package com.manulaiko.blackeye.net.game;

import com.manulaiko.tabitha.Console;

/**
 * Packet factory
 *
 * Factory for building packets and retrieving handlers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.net.Factory
{
    /**
     * Constructor.
     *
     * Sets available packets and their handlers.
     */
    public Factory()
    {
        super("com.manulaiko.blackeye.net.game.packet.handler", "com.manulaiko.blackeye.net.game.packet.command");

        Console.println("Loading game packet handlers and commands...");

        ///////////////////////////
        // Start Packet Handlers //
        ///////////////////////////
        this._packets.put("LOGIN", "LoginRequest");
        this._packets.put("0", "EchoPacket");
        this._packets.put("PNG", "Ping");
        //this._packets.put("1", "Movement");
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
        this._commands.put("SET", "SETCommand");
        /////////////////////////
        // End Packet Commands //
        /////////////////////////

        Console.println(this._packets.size() + " packet handlers and "+ this._commands.size() +"commands loaded!");
    }
}
