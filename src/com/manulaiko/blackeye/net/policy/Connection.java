package com.manulaiko.blackeye.net.policy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.manulaiko.tabitha.Console;

/**
 * Connection class.
 *
 * Handles client connection and parses packets.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Connection extends com.manulaiko.tabitha.net.Connection
{
    /**
     * Input stream.
     *
     * @var Input.
     */
    private BufferedReader _in;

    /**
     * Output stream.
     *
     * @var Output.
     */
    private PrintWriter _out;

    /**
     * Policy file.
     *
     * @var Policy file response.
     */
    private String _policyFile = "<?xml version=\"1.0\"?>\n" +
                                 "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n" +
                                 "<cross-domain-policy>\n" +
                                 "<allow-access-from domain=\"*\" to-ports=\"*\" />\n" +
                                 "</cross-domain-policy>";

    /**
     * Constructor.
     *
     * @param socket Connection socket.
     */
    public Connection(Socket socket)
    {
        super(socket);

        try {
            this._thread = new Thread(this);

            this._in = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
            this._out = new PrintWriter(this._socket.getOutputStream());

            this._thread.start();
        } catch(Exception e) {
            //empty
        }
    }

    /**
     * Sends a packet to the client.
     *
     * @param str String to send.
     */
    public void send(String str)
    {
        this._out.print(str + (char)0x00);
        this._out.flush();
        Console.println("Policy file sent!");
    }

    /**
     * Starts reading and sends the response.
     */
    public void run()
    {
        try {
            String packet = "";

            int c;
            while(0 < (c = this._in.read())) {
                packet += (char) c;
            }

            this.send(this._policyFile);
        } catch(Exception e) {
            Console.println("Couldn't send packet!");
            Console.println(e.getMessage());
        }
    }
}
