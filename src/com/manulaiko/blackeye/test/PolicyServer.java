package com.manulaiko.blackeye.test;

import com.manulaiko.blackeye.net.policy.Server;
import com.manulaiko.tabitha.Console;

/**
 * Policy Server test.
 *
 * Starts the policy server so it can be running even if the project needs to be
 * recompiled.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class PolicyServer
{
    /**
     * Main method.
     *
     * @param args Command line arguments.
     *
     * @throws Exception If something goes wrong.
     */
    public static void main(String[] args) throws Exception
    {
        Console.println("Starting policy server on port 843...");
        Server server = new Server((short)843);
        server.start();
        Console.println("Policy server started!");
        Console.print("Enter 'exit' to exit: ");

        String in = Console.readLine();
        while(!in.equalsIgnoreCase("exit")) {
            Console.print("Enter 'exit' to exit: ");
            in = Console.readLine();
        }

        Console.println("Stopping server...");
        server.stop();
        Console.println("Server stopped!");
        System.exit(0);
    }
}
