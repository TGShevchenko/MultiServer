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
        StringBuilder sbArgs = new StringBuilder();
        for(String arg : args){
            if(sbArgs.length() > 0){
                sbArgs.append(", ");
            }
            sbArgs.append(arg);
        } 
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
                System.err.println( "EchoUDPServer: One of port numbers is invalid: "
                        + sbArgs.toString() );
                System.exit(1);
            }
        }
        System.err.println("Used ports: " + sbArgs.toString());

        // At first, we create a SignalController instance in order to control
        // the running states of all the servers
        SignalController signalController = new SignalController();

        // Creating 2 server factories
        ServerRunnerFactory runnerTCPFactory = new ServerRunnerFactory(ServerType.TCP);
        ServerRunnerFactory runnerUDPFactory = new ServerRunnerFactory(ServerType.UDP);

        // Creating 3 server runners for each service
        IServerRunner dayTimeTCPRunner = runnerTCPFactory.getServerRunner(portDayTime);
        IServerRunner timeUDPRunner = runnerUDPFactory.getServerRunner(portTime);
        IServerRunner echoTCPRunner = runnerTCPFactory.getServerRunner(portEcho);

        // Registering the created server runners with a SignalController
        signalController.registerServerObserver(dayTimeTCPRunner);
        signalController.registerServerObserver(timeUDPRunner);
        signalController.registerServerObserver(echoTCPRunner);

        // Creating servers with given functionality 
        DayTimeServer dayTimeServerTCP =  new DayTimeServer(dayTimeTCPRunner);
        TimeServer timeServerUDP = new TimeServer(timeUDPRunner);
        EchoServer echoServerTCP = new EchoServer(echoTCPRunner);

        // Setting a blocking mode for a server, which will be running the latest
        // and it will block the whole application from exiting automatically immediately
        // after it starts
        echoServerTCP.setBlockingMode(true);

        // Start servers for listening
        dayTimeServerTCP.startService();
        timeServerUDP.startService();
        echoServerTCP.startService();
    }
}
