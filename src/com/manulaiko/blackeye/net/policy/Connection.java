package com.manulaiko.blackeye.net.policy;

/**
 * Connection class
 *
 * Handles client connection and parses packets
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Connection extends com.manulaiko.tabitha.net.Connection
{
    /**
     * Input stream
     */
    private java.io.BufferedReader _in;

    /**
     * Output stream
     */
    private java.io.PrintWriter _out;

    /**
     * Policy file
     */
    private String _policyFile = "<?xml version=\"1.0\"?>\n" +
                                 "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n" +
                                 "<cross-domain-policy>\n" +
                                 "<allow-access-from domain=\"*\" to-ports=\"*\" />\n" +
                                 "</cross-domain-policy>";

    /**
     * Constructor
     *
     * @param socket Connection socket
     */
    public Connection(java.net.Socket socket)
    {
        super(socket);

        try {
            this._in = new java.io.BufferedReader(new java.io.InputStreamReader(this._socket.getInputStream()));
            this._out = new java.io.PrintWriter(this._socket.getOutputStream());
        } catch(Exception e) {
            //empty
        }
    }

    /**
     * Sends a packet to the client
     *
     * @param str String to send
     */
    public void send(String str)
    {
        this._out.print(str);
        com.manulaiko.tabitha.Console.println("Packet sent: "+ str);
    }

    /**
     * Sets in/out streams and starts reading packets
     */
    public void run()
    {
        try {
            String packet = this._in.readLine();


            if(packet != null) {
                this.send(this._policyFile);
            }
        } catch(Exception e) {
            com.manulaiko.tabitha.Console.println("Couldn't read packet!");
            com.manulaiko.tabitha.Console.println(e.getMessage());
        }
    }
}
