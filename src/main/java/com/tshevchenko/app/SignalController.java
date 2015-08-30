package com.tshevchenko.app;

import java.util.*;

/**
 * SignalController class keeps all the created servers
 * and notifies them as soon as a SIGINT signal comes
 * in order to ask servers gently stop their processing loops
 * and finish their work
 */
public class SignalController extends Thread implements ISignalNotifier {
    private List<IServerRunner> serverObservers;

    public SignalController(){
       serverObservers = new ArrayList<IServerRunner>();
       registerForIntSignal();
    }

    /**
     * Attaches a shutdown hook to JVM, which prevents the application
     * to stop working when a SIGINT signal comes
     */
    public void registerForIntSignal(){
        Runtime.getRuntime().addShutdownHook(this);
    }

    @Override
    public void run() {
        System.out.println("A SIGINT triggered. Notifying all the servers...");
        notifyServerObservers();
    }

    /**
     * Registers a server instance, which will be notified for a termination
     * @param server
     */
    @Override
    public void registerServerObserver(IServerRunner server){
        if(server == null){ 
            throw new NullPointerException("Null Server runner");
        } 
        if(!serverObservers.contains(server)){
            serverObservers.add(server);
        }
    }

    /**
     * Notifies all the subscribed servers updating their running states
     */
    @Override
    public void notifyServerObservers(){
        for(IServerRunner server : serverObservers){
            server.updateRunningState(false);
        }
        System.out.println("Services have been notified!");
    }

}



