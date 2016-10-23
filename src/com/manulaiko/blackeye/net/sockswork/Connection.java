package com.manulaiko.blackeye.net.sockswork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.manulaiko.blackeye.launcher.ServerManager;
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
     * Last received packet.
     *
     * @var Last packet.
     */
    public String lastReceivedPacket = "";

    /**
     * Whether the account is running or not.
     *
     * @var Connection status.
     */
    private boolean _isRunning = true;

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

            this._in  = new BufferedReader(new InputStreamReader(this._socket.getInputStream()));
            this._out = new PrintWriter(this._socket.getOutputStream());

            this._thread.start();
        } catch(Exception e) {
            Console.println("Couldn't set in/out streams!");
            Console.println(e.getMessage());
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
        Console.println("Packet sent: "+ str);
    }

    /**
     * Sets in/out streams and starts reading packets.
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
                } else if(!packet.isEmpty()) {
                    packet = new String(packet.getBytes(), "UTF8");
                    this.handle(packet);

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
     * Handles the packet.
     *
     * @param packet Packet to handle.
     */
    public void handle(String packet)
    {
        Console.println("Packet received: "+ packet);

        // TODO Factory and packets
    }

    /**
     * Closes the connection to the client.
     */
    public void finish()
    {
        this._isRunning = false;
        this._thread.stop();

        ServerManager.sockswork.finishConnection(this.id);
    }
}
