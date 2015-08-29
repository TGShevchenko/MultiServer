package com.tshevchenko.app;

/**
 * An interface that is dedicated to be implemented in
 * specific server classes, which implement running loops
 * for UDP or TCP connections.
 * about their running state
 */
public interface IServerRunner {
    public void setService(IService service);
    public void processRequests();
    public void updateRunningState(boolean state);
}