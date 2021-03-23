package com.tshevchenko.app;

import java.util.List;

/**
 * This interface is being implemented by a SignalController class
 * in order to register server runner objects and send notifications to them
 */
public interface ISignalNotifier {
    void registerServer(IServer server);
    void notifyServers(boolean state);
    List<IServer> getServers();
    void waitServerThreadsToJoin();
}
