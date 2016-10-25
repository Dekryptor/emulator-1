package com.manulaiko.blackeye.net.chat;

import java.io.IOException;
import java.net.Socket;

import com.manulaiko.blackeye.launcher.Main;

import com.manulaiko.tabitha.Console;

/**
 * Chat server.
 *
 * Starts chat server on port 9338.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Server extends com.manulaiko.tabitha.net.Server
{
    /**
     * Packet factory.
     */
    public Factory packetFactory;

    /**
     * Constructor.
     */
    public Server()
    {
        super(Main.configuration.getShort("core.chat_port"));
    }

    /**
     * Waits for connections and handles them.
     */
    public void onRunning()
    {
        try {
            Socket socket = this.acceptConnection();
            this.addConnection(new Connection(socket));

            Console.println("Received connection from "+ socket.getInetAddress().getHostAddress());
        } catch(Exception e) {
            //Empty
        }
    }

    /**
     * Initializes packet factory and starts listening.
     */
    public void start() throws IOException
    {
        this.packetFactory = new Factory();
        super.start();
    }

    /**
     * Stops the server.
     *
     * @param timeout Timeout to wait before stopping the server.
     */
    public void stop(int timeout)
    {
        try {
            // TODO Timeout
            super.stop();
        } catch(Exception e) {
            //Empty
        }
    }

    /**
     * Finishes a connection.
     *
     * @param id Connection ID.
     */
    public void finishConnection(int id)
    {
        if(!this._connections.containsKey(id)) {
            return;
        }

        com.manulaiko.tabitha.net.Connection c = this._connections.get(id);

        c.close();

        c = null;

        this._connections.remove(id);
    }
}
