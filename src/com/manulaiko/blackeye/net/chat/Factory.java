package com.manulaiko.blackeye.net.chat;

/**
 * Packet factory
 *
 * Factory for building packets and retrieving handlers
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Factory extends com.manulaiko.blackeye.net.Factory
{
    /**
     * Constructor.
     *
     * Sets available packets and their handlers.
     */
    public Factory()
    {
        super("com.manulaiko.blackeye.net.chat.packets.handlers", "com.manulaiko.blackeye.net.chat.packets.commands");
    }
}
