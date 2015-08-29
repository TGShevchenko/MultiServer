package com.tshevchenko.app;

/**
 * An interface for notifying server objects
 * about their running state
 */
public interface IServerObserver{
    /**
     * Method updates a running state, which is used to interrupt
     * the main server processing loop
     * @param state
     */
    public void updateRunningState(boolean state);
}
