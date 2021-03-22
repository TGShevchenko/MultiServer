package com.tshevchenko.app;

/**
 * A Server interface
 */
public interface IServer {
    void startService();
    void waitServerThreadsToJoin();
    void setServerRunner(IServerRunner serverRunner);
    IServerRunner getServerRunner();
}
