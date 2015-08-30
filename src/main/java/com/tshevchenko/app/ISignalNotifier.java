package com.tshevchenko.app;

/**
 * This interface is being implemented by a SignalController class
 * in order to register server objects and send notifications to them
 */
public interface ISignalNotifier{
    public void registerServiceObserver(IService service);
    public void notifyServiceObservers();
}
