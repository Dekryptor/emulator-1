package com.manulaiko.blackeye.net.sockswork;

import java.net.Socket;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;

/**
 * SocksWork server
 *
 * Starts sockswork server
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Server extends com.manulaiko.tabitha.net.Server
{
    /**
     * Packet factory
     */
    public com.manulaiko.blackeye.net.sockswork.packets.Factory packetFactory;

    /**
     * Constructor
     */
    public Server()
    {
        super(Main.configuration.getShort("core.sockswork_port"));

        this.packetFactory = new com.manulaiko.blackeye.net.sockswork.packets.Factory();
    }

    /**
     * Waits for connections and handles them
     */
    public void onRunning()
    {
        try {
            Socket socket = this.acceptConnection();
            this.addConnection(new Connection(socket));

            Console.println("Received connection from "+ socket.getInetAddress().getHostAddress());
        } catch(Exception e) {
            Console.println("Couldn't accept connection!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Stops the server
     *
     * @param timeout Timeout to wait before stopping the server
     */
    public void stop(int timeout)
    {
        try {
            super.stop();
        } catch(java.io.IOException e) {
            //Empty
        }
    }

    /**
     * Finish the connection
     *
     * @param id Connection id
     */
    public void finishConnection(int id)
    {
        if(!this._connections.containsKey(id)) {
            return;
        }

        com.manulaiko.tabitha.net.Connection c = this._connections.get(id);

        c.close();
    }
}
