package com.manulaiko.blackeye.utils;

/**
 * Updatable interface.
 *
 * All updatable objects must extend this interface in order to use the UpdaterManager.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public interface Updatable
{
    /**
     * Performs the update.
     */
    void update();
}
