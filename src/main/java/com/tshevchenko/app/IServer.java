package com.tshevchenko.app;

/**
 * A Server interface
 */
public interface IServer {
    void setServerRunner(IServerRunner serverRunner);
    IServerRunner getServerRunner();
    void startService();
    void waitThreadsToJoin();
}
