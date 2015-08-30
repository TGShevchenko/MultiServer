package com.tshevchenko.app;

/**
 * Main class to run a multiple server test task
 *
 */
public class MultiServerApp
{
    public static void main( String[] args )
    {
        if (args.length > 3) {
            System.err.println("Usage: TestApp [PORT_ECHO] [PORT_TIME] [PORT_DAYTIME]");
            System.exit(1);
        }

        int portEcho = 7;
        int portTime = 37;
        int portDayTime = 13;
        if (args.length <= 3) {
            try {
                for(int arg = 0 ; arg < args.length ; ++arg){
                    switch(arg){
                        case 0:
                            portEcho = Integer.parseInt(args[0]);
                            break;
                        case 1:
                            portTime = Integer.parseInt(args[1]);
                            break;
                        case 2:
                            portDayTime = Integer.parseInt(args[2]);
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("EchoUDPServer: One of port numbers is invalid: "
                        + args[0] + ", " + args[1] + ", " + args[2]);
                System.exit(1);
            }
        }
        System.err.println("Used ports: " + args[0] + ", " + args[1] + ", " + args[2]);

        // At first, we create a SignalController instance in order to control
        // the running states of all the servers
        SignalController signalController = new SignalController();

        // Creating different kind of servers

        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);
        ServerRunnerFactory runnerUDPFactory = new ServerRunnerFactory(ServerType.UDP);

        DayTimeServer dayTimeServerTCP =  new DayTimeServer(runnerTCPFactory.getServerRunner(portDayTime));
        TimeServer timeServerUDP = new TimeServer(runnerUDPFactory.getServerRunner(portTime));
        EchoServer echoServerTCP = new EchoServer(runnerTCPFactory.getServerRunner(portEcho));

        // Registering the created servers with a SignalController
        signalController.registerServiceObserver(dayTimeServerTCP);
        signalController.registerServiceObserver(timeServerUDP);
        signalController.registerServiceObserver(echoServerTCP);

        // Setting a blocking mode for a server, which will be running the latest
        // and it will block the whole application from exiting automatically immediately
        // after it starts
        echoServerTCP.setBlockingMode(true);
        dayTimeServerTCP.startService();
        timeServerUDP.startService();
        echoServerTCP.startService();
    }
}
