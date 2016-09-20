package com.manulaiko.blackeye.net.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.manulaiko.blackeye.simulator.account.Account;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.launcher.GameManager;

import com.manulaiko.blackeye.net.game.packets.Packet;
import com.manulaiko.blackeye.net.game.utils.PacketParser;

import com.manulaiko.tabitha.Console;

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
     * Max amount of pings to received before disconnecting user
     * for inactivity
     */
    public static final int MAX_PING_AMOUNT = 5;

    /**
     * Input stream
     */
    private BufferedReader _in;

    /**
     * Output stream
     */
    private PrintWriter _out;

    /**
     * Policy file
     */
    private String _policyFile = "<?xml version=\"1.0\"?>\n" +
                                 "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n" +
                                 "<cross-domain-policy>\n" +
                                 "<allow-access-from domain=\"*\" to-ports=\"*\" />\n" +
                                 "</cross-domain-policy>";

    /**
     * Connection's Account
     */
    public Account account;

    /**
     * Last received packet
     */
    public String lastReceivedPacket = "";

    /**
     * Amount of received pings
     */
    public short pings = 0;

    /**
     * Wether the account is running or not
     */
    private boolean _isRunning = true;

    /**
     * Constructor
     *
     * @param socket Connection socket
     */
    public Connection(Socket socket)
    {
        super(socket);

        try {
            this._thread = new Thread(this);

            this._in  = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
            this._out = new PrintWriter(this._socket.getOutputStream());

            this._thread.start();
        } catch(Exception e) {
            Console.println("Couldn't set in/out streams!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Sends a packet to the client
     *
     * @param p Packet to send
     */
    public void send(PacketParser p)
    {
        this.send(p.toString());
    }

    /**
     * Sends a packet to the client
     *
     * @param str String to send
     */
    public void send(String str)
    {
        this._out.print(str + (char)0x00);
        this._out.flush();
        Console.println("Packet sent: "+ str);
    }

    /**
     * Sets in/out streams and starts reading packets
     */
    public void run()
    {
        try {
            String packet = "";
            char[] packetChar = new char[1];

            //While the socket input stream (call it packet) char isn't -1 process the packet
            while(this._in.read(packetChar, 0, 1) != -1) {
                if(!this._isRunning) {
                    return;
                }
                //If the char isn't null, new line or return char
                if(packetChar[0] != '\u0000' && packetChar[0] != '\n' && packetChar[0] != '\r') {
                    //packet increase it's value with the char
                    //Example:
                    // packet = RD, packetChar[0] = Y;
                    // now packet = RDY;
                    packet += packetChar[0];
                } else if (!packet.isEmpty()) {
                    //If the packet isn't "" we have the complete packet

                    //Concat the packet with UTF8
                    packet = new String(packet.getBytes(), "UTF8");
                    //Assemble the packet
                    //Declaration
                    if(packet.equals("<policy-file-request/>")) {
                        //Send the policy
                        this.send(this._policyFile);
                        Console.println("Policy file sent!");
                    } else {
                        PacketParser p = new PacketParser(packet);

                        this.handle(p);
                    }
                    //Set the packet again to ""
                    this.lastReceivedPacket = packet;
                    packet = "";
                }
            }
        } catch(Exception e) {
            if(!this._isRunning) {
                return;
            }
            Console.println("Couldn't read packet!");
            Console.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the packet
     *
     * @param packet Packet to handle
     */
    public void handle(PacketParser packet)
    {
        Console.println("Packet received: "+ packet);

        Packet p = ServerManager.game.packetFactory.get(packet);

        if(p != null) {
            p.setConnection(this);
            GameManager.queuedPackets.offer(p);
        }
    }

    /**
     * Closes the connection to the client
     */
    public void finish()
    {
        if(this.account != null) {
            this.account.hangar.ship.map.removeAccount(account.id, false);
        }
        this._isRunning = false;
        this._thread.stop();

        ServerManager.game.finishConnection(this.id);
    }
}
