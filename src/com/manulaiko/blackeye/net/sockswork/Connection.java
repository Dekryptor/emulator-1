package com.manulaiko.blackeye.net.sockswork;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.manulaiko.blackeye.launcher.ServerManager;

import com.manulaiko.blackeye.net.game.utils.PacketParser;
import com.manulaiko.blackeye.net.sockswork.packets.Packet;

import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.exceptions.NotFound;

/**
 * Connection class
 *
 * Handles client connection and parses packets
 *
 * @author Manulaiko <manulaiko@gmail.com>
 *
 * @package com.manulaiko.blackeye.net.game
 */
public class Connection extends com.manulaiko.tabitha.net.Connection
{
    /**
     * Input stream
     */
    private BufferedReader _in;

    /**
     * Output stream
     */
    private PrintWriter _out;

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

            this._in  = new BufferedReader(new java.io.InputStreamReader(this._socket.getInputStream()));
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
                //If the char isn't null, new line or return char
                if(packetChar[0] != '\u0000' && packetChar[0] != '\n' && packetChar[0] != '\r') {
                    //packet increase it's value with the char
                    //Example:
                    // packet = RD, packetChar[0] = Y;
                    // now packet = RDY;
                    packet += packetChar[0];
                } else if(!packet.isEmpty()) {
                    //If the packet isn't "" we have the complete packet

                    //Concat the packet with UTF8
                    packet = new String(packet.getBytes(), "UTF8");
                    PacketParser p = new PacketParser(packet);

                    this.handle(p);

                    packet = "";
                }
            }

            if(!packet.isEmpty()) {
                //If the packet isn't "" we have the complete packet

                //Concat the packet with UTF8
                packet = new String(packet.getBytes(), "UTF8");
                PacketParser p = new PacketParser(packet);

                this.handle(p);
            }
        } catch(Exception e) {
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

        try {
            Packet p = ServerManager.sockswork.packetFactory.get(packet);
            p.handle(this);
        } catch(NotFound e) {
            Console.println("No packet handler found for "+ packet);
        }
    }
}
