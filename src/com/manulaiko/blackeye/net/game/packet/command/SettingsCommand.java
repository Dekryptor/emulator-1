package com.manulaiko.blackeye.net.game.packet.command;

/**
 * Settings command.
 *
 * Builds all Settings commands.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class SettingsCommand extends com.manulaiko.blackeye.net.utils.Command
{
    /**
     * Constructor.
     */
    public SettingsCommand()
    {
        super();

        this.add("7");
    }

    /**
     * Adds a string to the packet.
     *
     * @param str String to add.
     */
    public void add(String str)
    {
        super.add(str);
    }

    /**
     * Adds an integer to the packet.
     *
     * @param i Integer to add.
     */
    public void add(int i)
    {
        super.add(i);
    }

    /**
     * Adds a boolean to the packet.
     *
     * @param b Boolean to add.
     */
    public void add(boolean b)
    {
        super.add(b);
    }

    /**
     * Returns the packet as string.
     * 
     * @return Packet as string.
     */
    public String toString()
    {
        return super.toString();
    }
}
