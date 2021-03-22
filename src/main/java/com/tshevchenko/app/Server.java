package com.tshevchenko.app;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This server class contains basic operations
 * for running a process request loop of an injected runner instance.
 */
public class Server extends Thread implements IServer {
    private final Logger logger = Logger.getLogger(Server.class.getName());

    protected IServerRunner serverRunner;

    public void setServerRunner(IServerRunner serverRunner) {
        this.serverRunner = serverRunner;
    }

    public IServerRunner getServerRunner() {
        return serverRunner;
    }

    @Override
    public void run(){
        serverRunner.processRequests();
    }

    /**
     * Method waits the servers' threads to join
     */
    public void waitServerThreadsToJoin() {
        try {
            join();
        } catch(InterruptedException ie){
            logger.log(Level.SEVERE, ie.toString(), ie);
        }
    }

    /**
     * Method starts this thread, which runs processing by a server runner.
     */
    public void startService(){
        start();
    }
}
