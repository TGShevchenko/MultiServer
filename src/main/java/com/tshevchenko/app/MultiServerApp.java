package com.tshevchenko.app;

/**
 * Main class to run a multiple server test task
 *
 */
public class MultiServerApp {
    public static void main( String[] args ) {
        int portEcho = 7;
        int portTime = 37;
        int portDayTime = 13;

        // Creating 2 server factories
        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);
        ServerRunnerFactory runnerUDPFactory = new ServerRunnerFactory(ServerType.UDP);

        // Creating 3 server runners for each service
        IServerRunner dayTimeTCPRunner = runnerTCPFactory.getServerRunner(portDayTime, new DayTimeService());
        IServerRunner timeUDPRunner = runnerUDPFactory.getServerRunner(portTime, new TimeService());
        IServerRunner echoTCPRunner = runnerTCPFactory.getServerRunner(portEcho, new EchoService());

        //Create 3 servers to start runners in separate threads and control their running states
        IServer dayTimeTCPServer = new Server();
        IServer timeUDPServer = new Server();
        IServer echoTCPServer = new Server();

        dayTimeTCPServer.setServerRunner(dayTimeTCPRunner);
        timeUDPServer.setServerRunner(timeUDPRunner);
        echoTCPServer.setServerRunner(echoTCPRunner);

        // We create a SignalController instance in order to control
        // the running states of all the servers
        SignalController signalController = new SignalController();

        // Registering the created servers with a SignalController
        signalController.registerServer(dayTimeTCPServer);
        signalController.registerServer(timeUDPServer);
        signalController.registerServer(echoTCPServer);

        // Start servers for listening
        dayTimeTCPServer.startService();
        timeUDPServer.startService();
        echoTCPServer.startService();

        signalController.waitServerThreadsToJoin();
    }
}
