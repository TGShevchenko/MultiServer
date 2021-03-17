package com.tshevchenko.app;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A factory that creates either a TCP or UDP Server runner
 */
public class ServerRunnerFactory {
    private final Logger logger = Logger.getLogger(ServerRunnerFactory.class.getName());

    private ServerType serverType;

    public ServerRunnerFactory(ServerType serverType){
        this.serverType = serverType;
    }

    public IServerRunner getServerRunner(int portNumber, IService service) {
        logger.log(Level.INFO, "portNumber=" + portNumber + ", serverType=" + serverType);

        IServerRunner serverRunner = null;
        if(serverType == ServerType.TCP) {
            serverRunner = new TCPRunner(portNumber, service);
        } else if (serverType == ServerType.UDP) {
            serverRunner = new UDPRunner(portNumber, service);
        } else {
            logger.log(Level.SEVERE, "Unknown server type. Exiting...");
            System.exit(0);
        }
        return serverRunner;
    }
}
