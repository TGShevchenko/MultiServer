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
                System.err.println( "MultiServerApp: One of port numbers is invalid: "
                        + sbArgs.toString() );
                System.exit(1);
            }
        }
        System.err.println("Used ports: " + sbArgs.toString());

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

        // Setting a blocking mode for a server, which will start running the last,
        // and it will block the whole application from exiting
        echoTCPServer.setBlockingMode(true);

        // Start servers for listening
        dayTimeTCPServer.startService();
        timeUDPServer.startService();
        echoTCPServer.startService();
    }
}
