package com.tshevchenko.app;

/**
 * An interface for a server implementation
 */
public interface IServer {
    void startService();
    void setBlockingMode(boolean blockingMode);
    void setServerRunner(IServerRunner serverRunner);
    IServerRunner getServerRunner();
}
