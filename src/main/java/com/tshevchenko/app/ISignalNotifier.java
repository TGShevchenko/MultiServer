package com.tshevchenko.app;

/**
 * This interface is being implemented by a SignalController class
 * in order to register server runner objects and send notifications to them
 */
public interface ISignalNotifier{
    void registerServerRunner(IServerRunner serverRunner);
    void notifyServerRunners();
}
