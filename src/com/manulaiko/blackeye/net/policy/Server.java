package com.manulaiko.blackeye.net.policy;

/**
 * Game server
 *
 * Starts game server
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Server extends com.manulaiko.tabitha.net.Server
{
    /**
     * Constructor
     */
    public Server()
    {
        super((short)843);
    }

    /**
     * Waits for connections and handles them
     */
    public void onRunning()
    {
        try {
            java.net.Socket socket = this.acceptConnection();
            this.addConnection(new Connection(socket));

            com.manulaiko.tabitha.Console.println("Received connection from " + socket.getInetAddress()
                                                                                      .getHostAddress());
        } catch(Exception e) {
            //Empty
        }
    }

    /**
     * Stops the server
     *
     * @param timeout Timeout to wait before stoping the server
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
