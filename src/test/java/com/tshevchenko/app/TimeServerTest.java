package com.tshevchenko.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for Time Server
 */
@DisplayName("TimeServerTest")
class TimeServerTest{

    @Test
    public void testStartAndStopServerWithRunnerAndService() {
        int portTime = 37;
        ServerRunnerFactory runnerUDPFactory = new ServerRunnerFactory(ServerType.UDP);

        // Creating a server runner for a given service
        IServerRunner timeUDPRunner = runnerUDPFactory.getServerRunner(portTime, new TimeService());
        IServer timeUDPServer = new Server();
        timeUDPServer.setServerRunner(timeUDPRunner);
        timeUDPServer.startService();
        assertTrue(timeUDPServer.getServerRunner().getRunningState());

        // Registering the created servers with a SignalController
        SignalController signalController = new SignalController();
        signalController.registerServer(timeUDPServer);
        signalController.notifyServers(false);
        assertFalse(timeUDPServer.getServerRunner().getRunningState());
    }
}