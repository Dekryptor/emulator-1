package com.manulaiko.blackeye.launcher;

import java.io.IOException;

import com.manulaiko.tabitha.Console;

/**
 * ServerManager class.
 *
 * Manages socket servers for game, chat and sockswork.
 *
 * TODO All mighty servers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class ServerManager
{
    /**
     * Game server.
     *
     * Runs on port 8080 and handles in-game connections.
     *
     * @var Game server.
     */
    public static com.manulaiko.blackeye.net.game.Server game = new com.manulaiko.blackeye.net.game.Server();

    /**
     * Policy server.
     *
     * Runs on port 843 and handles flash policy file requests.
     *
     * @var Policy server.
     */
    public static com.manulaiko.blackeye.net.policy.Server policy = new com.manulaiko.blackeye.net.policy.Server();

    /**
     * Chat server.
     *
     * Runs on port 9338 and handles chat connections.
     *
     * @var Chat server.
     */
    public static com.manulaiko.blackeye.net.chat.Server chat = new com.manulaiko.blackeye.net.chat.Server();

    /**
     * SocksWork server.
     *
     * Runs on port 4301 and handles SocksWork connections.
     *
     * @var SocksWork server.
     */
    public static com.manulaiko.blackeye.net.sockswork.Server sockswork = new com.manulaiko.blackeye.net.sockswork.Server();

    /**
     * Shows status of given server.
     *
     * @param server Server name (game|chat|sockswork).
     */
    public static void showStatus(String server)
    {
        if(server.contains("game") || server.contains("all")) {
            Console.println("Game server");
            Console.println(Console.LINE_EQ);
            ServerManager.game.showStatus();
        }

        if(server.contains("chat") || server.contains("all")) {
            Console.println("Chat server");
            Console.println(Console.LINE_EQ);
            ServerManager.chat.showStatus();
        }

        if(server.contains("sockswork") || server.contains("all")) {
            Console.println("SocksWork server");
            Console.println(Console.LINE_EQ);
            ServerManager.sockswork.showStatus();
        }

        if(server.contains("policy") || server.contains("all")) {
            Console.println("Policy server");
            Console.println(Console.LINE_EQ);
            ServerManager.policy.showStatus();
        }
    }

    /**
     * Starts specified server.
     *
     * @param server Server name (game|chat|sockswork).
     */
    public static void start(String server)
    {
        try {
            if(server.contains("game") || server.contains("all")) {
                Console.println("Starting game server...");
                ServerManager.game.start();
                Console.println("Game server started!");
            }

            if(server.contains("chat") || server.contains("all")) {
                Console.println("Starting chat server...");
                ServerManager.chat.start();
                Console.println("Chat server started!");
            }

            if(server.contains("sockswork") || server.contains("all")) {
                Console.println("Starting SocksWork server...");
                ServerManager.sockswork.start();
                Console.println("SocksWork server started!");
            }

            if(server.contains("policy") || server.contains("all")) {
                Console.println("Starting policy server...");
                ServerManager.policy.start();
                Console.println("Policy server started!");
            }
        } catch(IOException e) {
            Console.println("Couldn't start "+ server +" server!");
            Console.println(e.getMessage());
        }
    }

    /**
     * Stops specified server.
     *
     * @param server Server name (game|chat|sockswork).
     */
    public static void stop(String server)
    {
        ServerManager.stop(server, 0);
    }

    /**
     * Stops specified server with given timeout.
     *
     * @param server  Server name (game|chat|sockswork).
     * @param timeout Amount in milliseconds to wait before closing the server.
     */
    public static void stop(String server, int timeout)
    {
        if(server.equalsIgnoreCase("game") || server.equalsIgnoreCase("all")) {
            Console.println("Stopping game server...");
            ServerManager.game.stop(timeout);
            Console.println("Game server stopped!");
        }

        if(server.equalsIgnoreCase("chat") || server.equalsIgnoreCase("all")) {
            Console.println("Stopping chat server...");
            ServerManager.chat.stop(timeout);
            Console.println("Game server stopped!");
        }

        if(server.equalsIgnoreCase("sockswork") || server.equalsIgnoreCase("all")) {
            Console.println("Stopping SocksWork server...");
            ServerManager.sockswork.stop(timeout);
            Console.println("SocksWork server stopped!");
        }

        if(server.equalsIgnoreCase("policy") || server.equalsIgnoreCase("all")) {
            Console.println("Stopping policy server...");
            ServerManager.policy.stop(timeout);
            Console.println("Policy server started!");
        }
    }
}
