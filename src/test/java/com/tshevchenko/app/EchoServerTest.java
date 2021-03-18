package com.tshevchenko.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for Echo Server
 */
@DisplayName("EchoServerTest")
class EchoServerTest{

    @Test
    public void testStartAndStopServerWithRunnerAndService() {
        int portEcho = 7;

        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);

        // Creating a server runner for a given service
        IServerRunner echoTCPRunner = runnerTCPFactory.getServerRunner(portEcho, new DayTimeService());
        IServer echoTCPServer = new Server();
        echoTCPServer.setServerRunner(echoTCPRunner);
        echoTCPServer.startService();
        assertTrue(echoTCPServer.getServerRunner().getRunningState());

        // Registering the created servers with a SignalController
        SignalController signalController = new SignalController();
        signalController.registerServer(echoTCPServer);
        signalController.notifyServerRunners(false);
        assertFalse(echoTCPServer.getServerRunner().getRunningState());
    }
}