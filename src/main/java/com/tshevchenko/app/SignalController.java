package com.tshevchenko.app;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SignalController class keeps all the created servers
 * and notifies them as soon as a SIGINT signal comes
 * in order to ask servers gently stop their processing loops
 * and finish their work
 */
public class SignalController extends Thread implements ISignalNotifier {
    private final Logger logger = Logger.getLogger(SignalController.class.getName());

    private List<IServer> servers;

    public SignalController(){
       servers = new ArrayList<IServer>();
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
        logger.log(Level.INFO, "A SIGINT triggered. Notifying all the servers...");
        notifyServerRunners();
    }

    /**
     * Registers a server instance, which will be notified for a termination
     * @param server
     */
    public void registerServer(IServer server){
        if(server == null){ 
            throw new NullPointerException("Null Server");
        } 
        if(!servers.contains(server)){
            servers.add(server);
        }
    }

    /**
     * Updating running states for all server runners in the list
     */
    public void notifyServerRunners(){
        for(IServer server : servers){
            server.getServerRunner().updateRunningState(false);
        }
        logger.log(Level.INFO, "Server runners have been notified!");
    }

}



