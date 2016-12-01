package com.manulaiko.blackeye.net.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.manulaiko.blackeye.launcher.ServerManager;
import com.manulaiko.blackeye.net.utils.PacketParser;

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
     * @param p Packet to send.
     */
    public void send(PacketParser p)
    {
        this.send(p.toString());
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
     * Reads from input stream.
     */
    public void run()
    {
        try {
            String packet = "";
            while(
                (packet = this._in.readLine()) != null &&
                this._isRunning
            ) {
                this.lastReceivedPacket = packet;
                this.handle(new PacketParser(packet));
            }
        } catch(Exception e) {
            if(!this._isRunning) {
                return;
            }

            Console.println("Couldn't read packet!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Handles the packet.
     *
     * @param packet Packet to handle.
     */
    public void handle(PacketParser packet)
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

        ServerManager.chat.finishConnection(this.id);
    }
}
