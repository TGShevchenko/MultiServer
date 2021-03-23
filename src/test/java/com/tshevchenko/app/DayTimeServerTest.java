package com.tshevchenko.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for DayTime Server
 */
@DisplayName("DayTimeServerTest")
class DayTimeServerTest{

    @Test
    public void testStartAndStopServerWithRunnerAndService() {
        int portDayTime = 13;
        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);

        // Creating a server runner for a given service
        IServerRunner dayTimeTCPRunner = runnerTCPFactory.getServerRunner(portDayTime, new DayTimeService());
        IServer dayTimeTCPServer = new Server();
        dayTimeTCPServer.setServerRunner(dayTimeTCPRunner);
        dayTimeTCPServer.startService();
        assertTrue(dayTimeTCPServer.getServerRunner().getRunningState());

        // Registering the created servers with a SignalController
        SignalController signalController = new SignalController();
        signalController.registerServer(dayTimeTCPServer);
        signalController.notifyServers(false);
        signalController.waitServerThreadsToJoin();
        assertFalse(dayTimeTCPServer.getServerRunner().getRunningState());
    }
}