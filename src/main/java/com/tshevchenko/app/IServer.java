package com.tshevchenko.app;

/**
 * A Server interface
 */
public interface IServer {
    void startService();
    void setBlockingMode(boolean blockingMode);
    void setServerRunner(IServerRunner serverRunner);
    IServerRunner getServerRunner();
}
