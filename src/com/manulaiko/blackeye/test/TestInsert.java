package com.manulaiko.blackeye.test;

import com.manulaiko.blackeye.launcher.Main;
import com.manulaiko.blackeye.simulator.map.portal.Portal;
import com.manulaiko.tabitha.Configuration;
import com.manulaiko.tabitha.Console;
import com.manulaiko.tabitha.database.Connection;
import com.manulaiko.tabitha.exceptions.NotFound;
import com.manulaiko.tabitha.exceptions.database.ConnectionFailed;
import com.manulaiko.tabitha.utils.Point;

/**
 * Insert test.
 *
 * Used to see if Simulator.create works or not :/
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class TestInsert
{
    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args)
    {
        Console.println("BlackEye "+ Main.version +" by Manulaiko");
        Console.println(Console.LINE_EQ);

        // 1st stage: Configuration file
        try {
            Console.println("Stage 1: Reading configuration file...");

            Main.configuration = Configuration.load(Main.configurationFileLocation);
        } catch(NotFound e) {
            Main.exit("Be sure that the configuration file is located in `"+ Main.configurationFileLocation +"`");
        } catch(Exception e) {
            Console.println("There was a problem reading configuration file.");
            Main.exit(e.getMessage());
        }

        // 2nd stage: Database
        try {
            Console.println("Stage 2: Connecting to the database...");

            Main.database = new Connection(
                    Main.configuration.getString("database.host"),
                    Main.configuration.getShort("database.port"),
                    Main.configuration.getString("database.username"),
                    Main.configuration.getString("database.password"),
                    Main.configuration.getString("database.name")
            );
        } catch(ConnectionFailed e) {
            Main.exit(e.getMessage());
        }

        Console.println("Creating portal");
        Portal p = Portal.create();

        p.factionScrap   = 1;
        p.isWorking      = true;
        p.gfx            = 1;
        p.isVisible      = true;
        p.level          = 1;
        p.mapsID         = 1;
        p.position       = new Point(5000, 5000);
        p.targetPosition = p.position;
        p.targetMapsID   = 1;

        p.save();

        Console.println("Portal ID: "+ p.id);
    }
}
