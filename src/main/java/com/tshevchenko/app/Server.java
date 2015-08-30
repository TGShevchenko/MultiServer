package com.tshevchenko.app;

/**
 * This abstract class contains basic operations
 * for various kind of running server instances.
 */
public abstract class Server extends Thread {
    protected final IServerRunner serverRunner;

    // Used to accept and respond queries in blocking or non- blocking mode.
    // It needs to keep the last running server in a listening loop until a deactivate notification 
    // is received from a SignalNotifier
    protected boolean blockingMode = false;

    /**
     * A constructor
     * @param serverRunner
     */
    public Server(IServerRunner serverRunner){
        this.serverRunner = serverRunner;
        System.out.println("Server::Server. serverRunner=" + serverRunner.getClass());
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
        System.out.println("Server::startService. blockingMode=" + blockingMode);
        if(blockingMode)
            serverRunner.processRequests();
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
