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

    // Used to control termination of the main running server loop
    private volatile boolean isActive = true;

    // Used to accept and respond requests in blocking or non-blocking mode.
    // It needs to keep the last running server in a listening loop until a deactivate notification 
    // is received from a SignalNotifier
    private boolean blockingMode = false;

    /**
     * A constructor
     * @param serverRunner
     */
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
     * Method starts the main processing server loop either in
     * a separate thread (non-blocking mode) or in the same thread (blocking mode)
     * A blocking mode is being used in order to avoid the main application finish
     * its work after all the server instances have been started.
     */
    public void startService(){
        logger.log(Level.INFO, "blockingMode=" + blockingMode);
        if(blockingMode) {
            serverRunner.processRequests();
        }
        else{
            start();
        }
    }

    /**
     * Method sets a blocking mode for a server
     * @param blockingMode
     */
    public void setBlockingMode(boolean blockingMode){
        this.blockingMode = blockingMode;
    }
}
