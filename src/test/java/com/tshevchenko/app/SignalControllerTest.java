package com.tshevchenko.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for SignalController
 */
@DisplayName("SignalControllerTest")
class SignalControllerTest{

    @Test
    public void testNotifyServersBySignalController() {
        int portEcho = 7;
        int portTime = 37;
        int portDayTime = 13;

        // Create 2 factories for TCP and UDP server types
        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);
        ServerRunnerFactory runnerUDPFactory = new ServerRunnerFactory(ServerType.UDP);

        // Create 3 server runners for each service
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

        // Create a SignalController instance
        SignalController signalController = new SignalController();

        // Registering the created servers with a SignalController
        signalController.registerServer(dayTimeTCPServer);
        signalController.registerServer(timeUDPServer);
        signalController.registerServer(echoTCPServer);

        // Start servers for listening
        dayTimeTCPServer.startService();
        timeUDPServer.startService();
        echoTCPServer.startService();

        //Check the number of servers
        Assertions.assertEquals(3, signalController.getServers().size());
        for(IServer server : signalController.getServers()) {
            //Check that the running state of each server is true
            assertTrue(server.getServerRunner().getRunningState());
        }
        signalController.notifyServerRunners(false);
        for(IServer server : signalController.getServers()) {
            //Check that the running state of each server is false
            assertFalse(server.getServerRunner().getRunningState());
        }
    }
}