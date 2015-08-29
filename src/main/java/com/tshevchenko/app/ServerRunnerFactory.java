package com.tshevchenko.app;

/**
 * A factory that creates either a TCP or UDP Server runner
 */
public class ServerRunnerFactory {
    private ServerType serverType;
    public ServerRunnerFactory(ServerType serverType){
        this.serverType = serverType;
    }
    public IServerRunner getServerRunner(int portNumber){
        System.out.println("ServerRunnerFactory::getServerRunner. portNumber="
                + portNumber + ", serverType=" + serverType);
        IServerRunner serverRunner = null;
        if(serverType == ServerType.TCP){
            serverRunner = new TCPRunner(portNumber);
        } else if (serverType == ServerType.UDP){
            serverRunner = new UDPRunner(portNumber);
        } else {
            System.out.println("Unknown server type. Exiting...");
            System.exit(0);
        }
        return serverRunner;
    }
}
