package com.manulaiko.blackeye.launcher;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

import com.manulaiko.blackeye.utils.Updatable;
import com.manulaiko.tabitha.Console;

/**
 * Updater manager.
 *
 * Servers as manager for all updatable events.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class UpdaterManager extends Thread
{
    /**
     * Updatable events.
     *
     * @var Events.
     */
    private HashSet<Updatable> _events = new HashSet<>();

    /**
     * Constructor.
     */
    public UpdaterManager()
    {
        this.setName("Updater Manager");
    }

    /**
     * Adds an event to the array.
     *
     * @param event Event to add.
     */
    public void subscribe(Updatable event)
    {
        if(this._events.contains(event)) {
            return;
        }

        this._events.add(event);

        Console.println("Subscribed "+ event.toString());
    }

    /**
     * Unsubscribes an event.
     *
     * @param event Event to unsusbscribe.
     */
    public void unsubscribe(Updatable event)
    {
        if(!this._events.contains(event)) {
            return;
        }

        this._events.remove(event);

        Console.println("Unsubscribed "+ event.toString());
    }

    /**
     * Updates the game
     */
    public void run()
    {
        boolean isRunning = true;
        double  delta     = 0D;

        long lastTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long lastTickDuration = now - lastTime;

            delta += lastTickDuration / GameManager.OPTIMAL_TIME;
            lastTime = now;

            while (delta >= 1) {
                this.update();
                delta--;
            }
        }
    }

    /**
     * Performs the update
     */
    public void update()
    {
        try {
            this._events.forEach((e) -> {
                Thread t = new Thread()
                {
                    /**
                     * Execute the update on a separated thread.
                     */
                    public void run()
                    {
                        e.update();
                    }
                };

                t.setName("Update Event " + e.toString());
                t.start();
            });
        } catch(ConcurrentModificationException e) {
            Console.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
